//
//  Server.swift
//  Geocap
//
//  Created by Benjamin Angeria on 2019-04-15.
//  Copyright © 2019 Iyokan. All rights reserved.
//

import Foundation

struct jsonLocations: Codable {
    let locations: [Location]
    let type: String
}

struct Location: Codable {
    let owner: Owner?
    let identifier, name, description: String
    let position: Position
    let radius: Int
}

struct Owner: Codable {
    let id, name: String
}

struct Position: Codable {
    let lng, lat: Double
}

struct Quiz: Codable {
    let question: String?
    let success: Bool
    let reason: String?
    let alternatives: [String]?
    let type: String
}

struct QuizAnswer: Codable {
    let newAlternatives: [String]?
    let correct: Bool?
    let success: Bool
    let type: String
    let reason: String?
    let points: Int?
    let newQuestion: String?
    let correctAnswer: String?
}

struct Register: Codable {
    let type: String
    let success: Bool
    let user: ProfileInfo?
    let token: String?
    let reason: String?
}

struct HighscoreObject: Codable {
    let name: String
    let points: Int
}

struct Leaderboard: Codable {
    let highscore: [HighscoreObject]
}
    
struct LastQuizAnswer: Codable {
    let correct, success: Bool
    let type: String
    let points: Int
    let reason: String?
    let newQuestion: String?
    let correctAnswer: String?
    let successfulTakeover: Bool
}

struct MyProfile: Codable {
    let type: String
    let locations: [String]?
    let user: ProfileInfo?
}
struct ProfileInfo: Codable {
    let id: String?
    let name: String?
    let locations_taken: [String]?
    let level: Int?
    let exp: Int?
    let exp_rate: Int?
    let exp_to_level: Int?
}

class Server {
    
    var token: String!
    
    init() {
        setToken()
    }
    
    func setToken() {
        token = UserDefaults.standard.string(forKey: "token")
    }
    
    private func handleClientError(_ error: Error) {
        print(error.localizedDescription)
    }
    
    private func handleServerError(_ response: URLResponse?) {
        if let responseURL = response?.url {
            print("Server error: request to \(responseURL) failed")
        } else {
            print("Unknown server error")
        }
    }
    
    func fetchLocations(completionHandler: @escaping () -> ()) {
        let url = URL(string: "http://13.53.140.24/location/get-all")!
        
        URLSession.shared.dataTask(with: url) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        return
                }
                
                if let data = data {
                    let parsedJSON = try JSONDecoder().decode(jsonLocations.self, from: data)
                    geoCap.locations = parsedJSON.locations
                    
                    DispatchQueue.main.async {
                        completionHandler()
                    }
                }
                
            } catch {
                print("getLocations() failed")
                print(error)
            }
        }.resume()
    }
    
    func getQuiz(for location: String) -> Quiz? {
        let url = URL(string: "http://13.53.140.24/quiz/start")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let location = ["location": location]
        var quiz: Quiz?
        
        do {
            let json = try JSONSerialization.data(withJSONObject: location, options: [])
            request.httpBody = json
        } catch {
            print("Location couldn't be parsed to JSON in getQuiz()")
            return nil
        }
        
        // semaphore used for forcing dataTask to finish before returning
        let semaphore = DispatchSemaphore(value: 0)
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    semaphore.signal()
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        semaphore.signal()
                        return
                }
                
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                quiz = try decoder.decode(Quiz.self, from: data!)
            } catch {
                print("getQuiz() failed")
                print(error)
            }
            semaphore.signal()
            
        }.resume()
        semaphore.wait()
        
        if let quiz = quiz, !quiz.success {
            print("getQuiz() failed with error: \(quiz.reason!)")
            return nil
        }
        
        return quiz
    }
    
    func sendQuizAnswer(answer: String) -> QuizAnswer? {
        let url = URL(string: "http://13.53.140.24/quiz/answer")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let answer = ["answer": answer]
        var quizAnswer: QuizAnswer?
        
        do {
            let json = try JSONSerialization.data(withJSONObject: answer, options: [])
            request.httpBody = json
        } catch {
            print("Answer couldn't be parsed to JSON in sendQuizAnswer()")
            return nil
        }
        
        let semaphore = DispatchSemaphore(value: 0)
        
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    semaphore.signal()
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        semaphore.signal()
                        return
                }
                
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                quizAnswer = try decoder.decode(QuizAnswer.self, from: data!)
            } catch {
                print("Error in sendQuizAnswer()")
                print(error)
            }
            semaphore.signal()
        }.resume()
        
        semaphore.wait()
        
        if let quizAnswer = quizAnswer, !quizAnswer.success {
            print("getQuizAnswer() failed with error: \(quizAnswer.reason!)")
            return nil
        }
        
        return quizAnswer
    }
    
    func sendLastQuizAnswer(answer: String) -> LastQuizAnswer? {
        let url = URL(string: "http://13.53.140.24/quiz/answer")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let answer = ["answer": answer]
        var lastQuizAnswer: LastQuizAnswer?
        
        do {
            let json = try JSONSerialization.data(withJSONObject: answer, options: [])
            request.httpBody = json
        } catch {
            print("Answer couldn't be parsed to JSON in sendLastQuizAnswer()")
            return nil
        }
        
        let semaphore = DispatchSemaphore(value: 0)
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    semaphore.signal()
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        semaphore.signal()
                        return
                }
                
                let decoder = JSONDecoder()
                decoder.keyDecodingStrategy = .convertFromSnakeCase
                lastQuizAnswer = try decoder.decode(LastQuizAnswer.self, from: data!)
            } catch {
                print("Error in sendLastQuizAnswer()")
                print(error)
            }
            semaphore.signal()
        }.resume()
        semaphore.wait()
        
        if let lastQuizAnswer = lastQuizAnswer, !lastQuizAnswer.success {
            print("getLastQuizAnswer() failed with error: \(lastQuizAnswer.reason!)")
            return nil
        }
        
        return lastQuizAnswer
    }
    
    func register(userName: String) -> String? {
        let url = URL(string: "http://13.53.140.24/register")!
        var request = URLRequest(url: url)
        request.addValue(userName, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        var register: Register?
        let userName = ["username": userName]
        
        let json = try? JSONSerialization.data(withJSONObject: userName, options: [])
        request.httpBody = json
        do {
            let json = try JSONSerialization.data(withJSONObject: userName, options: [])
            request.httpBody = json
        } catch {
            print("Username couldn't be parsed to JSON in register()")
            return nil
        }
        
        let semaphore = DispatchSemaphore(value: 0)
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    semaphore.signal()
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        semaphore.signal()
                        return
                }
                
                register = try JSONDecoder().decode(Register.self, from: data!)
            } catch {
                print("Error in register()")
                print(error)
            }
            semaphore.signal()
        }.resume()
        
        semaphore.wait()
        guard let registerUnwrapped = register else { return nil }
        
        if !registerUnwrapped.success {
            return registerUnwrapped.reason!
        }
        
        UserDefaults.standard.set(registerUnwrapped.user!.name, forKey: "username")
        UserDefaults.standard.set(registerUnwrapped.user!.id, forKey: "guid")
        UserDefaults.standard.set(registerUnwrapped.token, forKey: "token")
        setToken()
        
        return "success"
    }

    func fetchProfileInfo(completionHandler: @escaping () -> ()) {
        let url = URL(string: "http://13.53.140.24/my-profile")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        var myProfile: MyProfile?
        
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        return
                }
                
                myProfile = try JSONDecoder().decode(MyProfile.self, from: data!)
                geoCap.profileInfo = myProfile?.user
                
                DispatchQueue.main.async {
                    completionHandler()
                }
            } catch {
                print("Error in getProfileInfo()")
                print(error)
            }
        }.resume()
        
        /*
        if let profileInfo = profileInfo, let _ = profileInfo.success, !profileInfo.success! {
            print("getProfileInfo() failed with error: \(profileInfo.reason!)")
        }
         */
    }
    
    func getLeaderboard() -> Leaderboard? {
        let url = URL(string: "http://13.53.140.24/highscore")!
        var leaderboard: Leaderboard?
    
        let semaphore = DispatchSemaphore(value: 0)
        URLSession.shared.dataTask(with: url) {(data, response, error) in
            do {
                if let error = error {
                    self.handleClientError(error)
                    semaphore.signal()
                    return
                }
                
                guard let httpResponse = response as? HTTPURLResponse,
                    (200...299).contains(httpResponse.statusCode) else {
                        self.handleServerError(response)
                        semaphore.signal()
                        return
                }
                
                leaderboard = try JSONDecoder().decode(Leaderboard.self, from: data!)
            } catch {
                print("Error in getLeaderboard()")
                print(error)
            }
            semaphore.signal()
        }.resume()
    
        semaphore.wait()
        return leaderboard
    }

}

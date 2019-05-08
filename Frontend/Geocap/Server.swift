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
    let question: String
    let success: Bool
    let alternatives: [String]
    let type: String
}

struct QuizAnswer: Codable {
    let newAlternatives: [String]
    let correct, success: Bool
    let type: String
    let points: Int
    let newQuestion: String
    
    enum CodingKeys: String, CodingKey {
        case newAlternatives = "new_alternatives"
        case correct, success, type, points
        case newQuestion = "new_question"
    }

}


struct UserInfo: Codable {
    let id: String
    let name: String
}
struct Register: Codable {
    let success: Bool
    let user: UserInfo?
    let token: String?
    let reason: String?
}
    
struct LastQuizAnswer: Codable {
    let correct, success: Bool
    let type: String
    let points: Int
    let newQuestion: String?
    let successfulTakeover: Bool
    
    enum CodingKeys: String, CodingKey {
        case correct, success, type, points
        case newQuestion = "new_question"
        case successfulTakeover = "successful_takeover"
    }
}

struct ProfileInfo: Codable {
    let score: Int
    let locations: [String]?
    let type: String
}

class Server {
    
    var token: String!
    
    init() {
        setToken()
    }
    
    func setToken() {
        token = UserDefaults.standard.string(forKey: "token")
    }
    
    func getLocations() -> [Location] {

        let url = "http://13.53.140.24/location/get-all"
        let urlObject = URL(string: url)!
        var locationsJSON: jsonLocations!
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: urlObject) {(data, response, error) in
            do {
                locationsJSON = try JSONDecoder().decode(jsonLocations.self, from: data!)
                semaphore.signal()
            } catch {
                print("error in retrieving JSON locations")
            }
        }.resume()
        
        //TODO: Future optimisation could be to not have to wait for the server to fetch
        //      and let the map load meanwhile
        semaphore.wait()
        return locationsJSON.locations
    }
    
    func getQuiz(for location: String) -> Quiz? {
        var quiz: Quiz!
        let url = URL(string: "http://13.53.140.24/quiz/start")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let location = ["location": location]
        
        let json = try? JSONSerialization.data(withJSONObject: location, options: [])
        request.httpBody = json
        
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                quiz = try JSONDecoder().decode(Quiz.self, from: data!)
                semaphore.signal()
            } catch {
                print("error in retrieving quiz")
                print(error)
            }
            }.resume()
        
        //TODO: Future optimisation could be to not have to wait for the server to fetch
        //      and let the map load meanwhile
        semaphore.wait()
        if(!quiz.success) {
            print("Invalid user")
            return nil
        }
        return quiz
    }
    
    func sendQuizAnswer(answer: String) -> QuizAnswer? {
        var quizAnswer: QuizAnswer!
        let url = URL(string: "http://13.53.140.24/quiz/answer")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let answer = ["answer": answer]
        
        let json = try? JSONSerialization.data(withJSONObject: answer, options: [])
        request.httpBody = json
        
        
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                quizAnswer = try JSONDecoder().decode(QuizAnswer.self, from: data!)
                semaphore.signal()
            } catch {
                print("error in retrieving quiz answer")
                print(error)
            }
            }.resume()
        
        //TODO: Future optimisation could be to not have to wait for the server to fetch
        //      and let the map load meanwhile
        semaphore.wait()
        if(!quizAnswer.success) {
            print("Invalid user")
            return nil
        }
        
        return quizAnswer
    }
    func sendLastQuizAnswer(answer: String) -> LastQuizAnswer? {
        var lastQuizAnswer: LastQuizAnswer!
        let url = URL(string: "http://13.53.140.24/quiz/answer")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let answer = ["answer": answer]
        
        let json = try? JSONSerialization.data(withJSONObject: answer, options: [])
        request.httpBody = json
        
        
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                lastQuizAnswer = try JSONDecoder().decode(LastQuizAnswer.self, from: data!)
                semaphore.signal()
            } catch {
                print("error in retrieving quiz answer")
                print(error)
            }
            }.resume()
        
        //TODO: Future optimisation could be to not have to wait for the server to fetch
        //      and let the map load meanwhile
        semaphore.wait()
        if(!lastQuizAnswer.success) {
            print("Invalid user")
            return nil
        }
        
        return lastQuizAnswer
    }
    

    func register(userName: String) -> String {
        var register: Register!
        let url = URL(string: "http://13.53.140.24/register")!
        var request = URLRequest(url: url)
        request.addValue(userName, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let userName = ["username": userName]
        
        let json = try? JSONSerialization.data(withJSONObject: userName, options: [])
        request.httpBody = json
        
        
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                register = try JSONDecoder().decode(Register.self, from: data!)
                semaphore.signal()
            } catch {
                print("error when registering")
                print(error)
            }
            }.resume()
        
        //TODO: Future optimisation could be to not have to wait for the server to fetch
        //      and let the map load meanwhile
        semaphore.wait()
        if(!register.success) {
            return register.reason!
        }
        UserDefaults.standard.set(register.user!.name, forKey: "username")
        UserDefaults.standard.set(register.user!.id, forKey: "guid")
        UserDefaults.standard.set(register.token, forKey: "token")
        setToken()
        
        return "success"
    }
    
    func getProfileInfo() -> ProfileInfo? {
        var profileInfo: ProfileInfo!
        let url = URL(string: "http://13.53.140.24/my-profile")!
        var request = URLRequest(url: url)
        request.addValue(token, forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        
        
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                profileInfo = try JSONDecoder().decode(ProfileInfo.self, from: data!)
                semaphore.signal()
            } catch {
                print("error in retrieving quiz")
                print(error)
            }
            }.resume()
        
        semaphore.wait()
        
        return profileInfo
    }

}

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
    let identifier, name, description: String
    let position: Position
    let type: String
    let radius: Int
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

class Server {
    
    
    
    init() {
        // host server on your computer and change to your public ip for testing on iPhone, or change to localhost for testing on Mac
       
    }
    
    func getLocations() -> [Location] {
        let url = "http://localhost/location/get-all"
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
    
    func getQuiz() -> Quiz? {
        var quiz: Quiz!
        let url = URL(string: "http://localhost/quiz/start")!
        var request = URLRequest(url: url)
        request.addValue("OsthyvelOsthyvelOsthyvelOsthyvel", forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let location = ["location": "domkyrkan"]
        
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
        print(quiz)
        return quiz
    }
}

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



class Server {
    
    private let url: String
    private let urlObject: URL
    
    init() {
        // host server on your computer and change to your public ip for testing on iPhone, or change to localhost for testing on Mac
        url = "http://130.243.233.148/location/get-all"
        urlObject = URL(string: url)!
    }
    
    func getLocations() -> [Location] {
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
    
    func getQuiz() ->
}

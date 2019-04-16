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


class Server {
    
    private let url: String
    private let urlObject: URL
    
    init() {
        // host server on your computer and change to your public ip for testing on iPhone, or change to localhost for testing on Mac
        url = "http://130.243.215.200/location/get-all"
        urlObject = URL(string: url)!
    }
    
    func getLocations() -> [Location] {
        var locations: jsonLocations!
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: urlObject) {(data, response, error) in
        
            do {
                locations = try JSONDecoder().decode(jsonLocations.self, from: data!)
                print(locations.type)
                for location in locations.locations {
                    print(location.identifier)
                    print(location.name)
                    print("Lat: " + String(location.position.lat))
                    print("Long: " + String(location.position.lng))
                    semaphore.signal()
                }
                
            } catch {
                print("error in retrieving JSON locations")
            }
            
            }.resume()
        
        //TODO: Future optimisation could be to not have to wait for the server to fetch
        //      and let the map load meanwhile
        semaphore.wait()
        return locations!.locations
    }
}

//
//  GeoCap.swift
//  Geocap
//
//  Created by Benjamin Angeria on 2019-05-06.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import Foundation
import MapKit

class GeoCap {
    let server: Server
    let quizModel: QuizModel
    var currentLocation: String?
    var locations = [Location]()
    var profileInfo: ProfileInfo?
    
    init() {
        self.server = Server()
        self.quizModel = QuizModel()
    }
    
    func userHasLocations(location: String) -> Bool {
        let locations = profileInfo!.locations_taken!
        for userLocations in locations {
            if(userLocations == location) {
                return true
            }
        }
        return false
    }
    
}
    



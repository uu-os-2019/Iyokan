//
//  GeoCap.swift
//  Geocap
//
//  Created by Benjamin Angeria on 2019-05-06.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import Foundation

class GeoCap {
    let server: Server
    let quizModel: QuizModel
    var currentLocation: String?
    var locations = [Location]()
    /*var username: String?
    var token: String?
    var profileInfo: ProfileInfo? */
    
    init() {
        self.server = Server()
        self.quizModel = QuizModel()
        /*
        self.username = UserDefaults.standard.string(forKey: "username")
        self.token = UserDefaults.standard.string(forKey: "token")
        self.profileInfo = geoCap.server.getProfileInfo()
 */
    }
    
    func userHasLocations(location: String) -> Bool {
        let profileInfo = self.server.getProfileInfo()
        let locations = profileInfo!.locations!
        for userLocations in locations {
            print(location)
            print(userLocations)
            if(userLocations == location) {
                return true
            }
        }
        return false
    }

}

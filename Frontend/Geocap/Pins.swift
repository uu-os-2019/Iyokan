//
//  Pins.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-09.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import Foundation
import MapKit

class Pin: NSObject, MKAnnotation {
    let title: String?
    let locationName: String
    let coordinate: CLLocationCoordinate2D
    let radius: CLLocationDistance
    let owner: Owner?
    
    init(title: String, locationName: String, coordinate: CLLocationCoordinate2D, radius: CLLocationDistance, owner: Owner?) {
        self.title = title
        self.locationName = locationName
        self.coordinate = coordinate
        self.radius = radius
        self.owner = owner
        
        super.init()
    }
    
    //TODO: change accordingly when place ownership implemented
    var markerTintColor: UIColor  {
        let userID = UserDefaults.standard.string(forKey: "guid")
        
        switch owner?.id {
        case userID:
            return .green
        // owned by no one
        case nil:
            return .white
        // owned by other user
        default:
            return .red
        }
    }

    var subtitle: String? {
        return locationName
    }
}

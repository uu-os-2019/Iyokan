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
    let discipline: String
    let coordinate: CLLocationCoordinate2D
    
    init(title: String, locationName: String, discipline: String, coordinate: CLLocationCoordinate2D) {
        self.title = title
        self.locationName = locationName
        self.discipline = discipline
        self.coordinate = coordinate
        
        super.init()
    }
    
    // markerTintColor for disciplines: Sculpture, Plaque, Mural, Monument, other
    var markerTintColor: UIColor  {
        switch discipline {
        case "area":
            return .red
        case "volleyballfield":
            return .cyan
        case "church":
            return .blue
        default:
            return .green
        }
    }

    
    var subtitle: String? {
        return locationName
    }
}

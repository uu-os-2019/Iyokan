//
//  PinsView.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-11.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import Foundation
import MapKit

class PinsMarkerView: MKMarkerAnnotationView {
    override var annotation: MKAnnotation? {
        willSet {
            // 1
            guard let pin = newValue as? Pin else { return }
            canShowCallout = true
            calloutOffset = CGPoint(x: -5, y: 5)
            rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
            // 2
            markerTintColor = pin.markerTintColor
            //glyphText = String(pin.discipline.first!)
            if let imageName = pin.imageName {
                glyphImage = UIImage(named: imageName)
            } else {
                glyphImage = nil
            }

        }
    }
}


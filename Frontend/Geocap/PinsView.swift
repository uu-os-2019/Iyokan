//
//  PinsView.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-11.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import Foundation
import MapKit

class ArtworkMarkerView: MKMarkerAnnotationView {
    override var annotation: MKAnnotation? {
        willSet {
            // 1
            guard let pin = newValue as? Pin else { return }
            canShowCallout = true
            calloutOffset = CGPoint(x: -5, y: 5)
            
            let buttonX = 150
            let buttonY = 150
            let buttonWidth = 100
            let buttonHeight = 50
            let button = UIButton(type: .system)
            button.setTitle("Ta över", for: .normal)
            button.tintColor = .white
            button.backgroundColor = .red
            button.frame = CGRect(x: buttonX, y: buttonY, width: buttonWidth, height: buttonHeight)
            
            rightCalloutAccessoryView = button
            markerTintColor = pin.markerTintColor
        }
    }
}


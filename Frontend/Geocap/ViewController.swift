//
//  ViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-09.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit
import MapKit


class ViewController: UIViewController {

    @IBOutlet weak var mapView: MKMapView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // set initial location in Honolulu
        let initialLocation = CLLocation(latitude: 59.8585 , longitude: 17.646)
        centerMapOnLocation(location: initialLocation)
        
        mapView.delegate = self
        
        mapView.register(ArtworkMarkerView.self,
                         forAnnotationViewWithReuseIdentifier: MKMapViewDefaultAnnotationViewReuseIdentifier)


        
        // show pins on map
        let pin1 = Pin(title: "Uppsala domkyrka",
                              locationName: "Uppsala domkyrka",
                              discipline: "Kyrka",
                              coordinate: CLLocationCoordinate2D(latitude: 59.8581, longitude: 17.634))
        mapView.addAnnotation(pin1)
        
        let pin2 = Pin(title: "Ekeby",
                       locationName: "Ekebyvägen",
                       discipline: "Volleybollplan",
                       coordinate: CLLocationCoordinate2D(latitude: 59.8491389, longitude: 17.6097114))
        mapView.addAnnotation(pin2)
        
        let pin3 = Pin(title: "Triangeln",
                       locationName: "Gräsmattan i triangeln",
                       discipline: "Område",
                       coordinate: CLLocationCoordinate2D(latitude: 59.855115 , longitude: 17.61795))
        mapView.addAnnotation(pin3)
        
        
        

        // Do any additional setup after loading the view, typically from a nib.
    }
    let regionRadius: CLLocationDistance = 5000
    func centerMapOnLocation(location: CLLocation) {
        let coordinateRegion = MKCoordinateRegionMakeWithDistance(location.coordinate, regionRadius, regionRadius)
        
        mapView.setRegion(coordinateRegion, animated: true)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    // User Location Auth
    let locationManager = CLLocationManager()
    func checkLocationAuthorizationStatus() {
        if CLLocationManager.authorizationStatus() == .authorizedWhenInUse {
            self.mapView.showsUserLocation = true
        } else {
            locationManager.requestWhenInUseAuthorization()
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        checkLocationAuthorizationStatus()
    }

}
extension ViewController: MKMapViewDelegate {
    // 1
    /*
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        // 2
        guard let annotation = annotation as? Pin   else { return nil }
        // 3
        let identifier = "marker"
        var view: MKMarkerAnnotationView
        // 4
        if let dequeuedView = mapView.dequeueReusableAnnotationView(withIdentifier: identifier)
            as? MKMarkerAnnotationView {
            dequeuedView.annotation = annotation
            view = dequeuedView
        } else {
            // 5
            view = MKMarkerAnnotationView(annotation: annotation, reuseIdentifier: identifier)
            view.canShowCallout = true
            view.calloutOffset = CGPoint(x: -5, y: 5)
            view.rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
        }
        return view
    }
 */
}



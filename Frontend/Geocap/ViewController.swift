//
//  ViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-09.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation

class ViewController: UIViewController {

    @IBOutlet weak var mapView: MKMapView!
    var mapRefreshTimer: Timer?
    @IBOutlet weak var profilbutton: UIButton!
    
    func startMapRefreshTimer() {
        mapRefreshTimer = Timer.scheduledTimer(withTimeInterval: 10, repeats: true) { Timer in
            Timer.tolerance = 3
            geoCap.server.fetchLocations(completionHandler: self.loadLocations)
            geoCap.server.fetchProfileInfo(completionHandler: self.updateProfileButtonScore)
        }
    }
    
    func stopMapRefreshTimer() {
        mapRefreshTimer?.invalidate()
        mapRefreshTimer = nil
    }
    
    func updateProfileButtonScore() {
        if let profileInfo = geoCap.profileInfo {
            profilbutton.setTitle("Poäng:" + String(profileInfo.score!), for: .normal)
        }
    }
    
    func clearMap() {
        mapView.removeAnnotations(mapView.annotations)
        mapView.removeOverlays(mapView.overlays)
    }
    
    func loadLocations() {
        clearMap()
        let locations = geoCap.locations
        var overlayCircles = [MKCircle]()
        for location in locations {
            let coordinate = CLLocationCoordinate2D(latitude: location.position.lat, longitude: location.position.lng)
            mapView.addAnnotation(Pin(title: location.identifier, locationName: location.description, coordinate: coordinate, radius: CLLocationDistance(location.radius), owner: location.owner))
            
            let circle = MKCircle(center: coordinate, radius: CLLocationDistance(location.radius))
            overlayCircles.append(circle)
            _ = mapView(mapView, rendererFor: circle)
        }
        mapView.addOverlays(overlayCircles)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
    
        geoCap.server.fetchProfileInfo(completionHandler: updateProfileButtonScore)
        let initialLocation = CLLocation(latitude: 59.8585 , longitude: 17.646)
        centerMapOnLocation(location: initialLocation)
        
        mapView.delegate = self
        
        mapView.register(ArtworkMarkerView.self,
                         forAnnotationViewWithReuseIdentifier: MKMapViewDefaultAnnotationViewReuseIdentifier)
        
        geoCap.server.fetchLocations(completionHandler: loadLocations)
        startMapRefreshTimer()
    }
    
    let regionRadius: CLLocationDistance = 5000
    func centerMapOnLocation(location: CLLocation) {
        let coordinateRegion = MKCoordinateRegionMakeWithDistance(location.coordinate, regionRadius, regionRadius)
        
        mapView.setRegion(coordinateRegion, animated: true)
    }
    
    // User Location Auth.
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
    @IBAction func LeaderboardButton(_ sender: Any) {
        performSegue(withIdentifier: "LeaderboardSegue", sender: self)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(true)
        stopMapRefreshTimer()
    }
    
}
extension ViewController: MKMapViewDelegate {
    
    // circle renderer
    func mapView(_ mapView: MKMapView, rendererFor overlay: MKOverlay) -> MKOverlayRenderer {
        let renderer = MKCircleRenderer(overlay: overlay)
        renderer.fillColor = UIColor.black.withAlphaComponent(0.1)
        renderer.strokeColor = #colorLiteral(red: 0.5843137503, green: 0.8235294223, blue: 0.4196078479, alpha: 1)
        renderer.lineWidth = 2
        return renderer
    }
 

    
    
    func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView,
                 calloutAccessoryControlTapped control: UIControl) {

        let annotationLocation = CLLocation(latitude: view.annotation!.coordinate.latitude, longitude: view.annotation!.coordinate.longitude)
        let userLocation = CLLocation(latitude: mapView.userLocation.coordinate.latitude, longitude: mapView.userLocation.coordinate.longitude)
        let distance = annotationLocation.distance(from: userLocation)
        
        let pin = view.annotation as! Pin
        
        // distance check commented out for testing purposes
        if !geoCap.quizModel.quizTimeoutIsActive//, distance <= pin.radius 
        {
            geoCap.currentLocation = pin.title
            performSegue(withIdentifier: "QuizSegue", sender: self)
        } else if geoCap.quizModel.quizTimeoutIsActive {
            let alert = UIAlertController(title: "Lugna ner dig!", message: "Du misslyckades nyligen med att ta över den här platsen, vänta 30 sekunder och försök igen", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Okej då...", style: .default, handler: nil))
            self.present(alert, animated: true)
        } else {
            let alert = UIAlertController(title: "You're not in this area", message: "Move within the area border to be able to capture it.", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Dismiss", style: .default, handler: nil))
            self.present(alert, animated: true)
        }
    }
}

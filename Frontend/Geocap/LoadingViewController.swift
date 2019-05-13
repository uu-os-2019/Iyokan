//
//  LoadingViewController.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-05-07.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

let geoCap = GeoCap()

class LoadingViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        
        //removes locally saved information
        //resetDefaults()
        
        //if there is a locally saved user, skips this view
        if (UserDefaults.standard.object(forKey: "token") != nil) {
            geoCap.server.setToken()
            performSegue(withIdentifier: "SkipLoginSegue", sender: self)
        }
        else {
            performSegue(withIdentifier: "LoginSegue", sender: self)
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    //removes the locally saved information
    func resetDefaults() {
        let defaults = UserDefaults.standard
        let dictionary = defaults.dictionaryRepresentation()
        dictionary.keys.forEach { key in
            defaults.removeObject(forKey: key)
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

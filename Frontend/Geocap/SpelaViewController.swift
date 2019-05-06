//
//  SpelaViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-09.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

let geoCap = GeoCap()

class SpelaViewController: UIViewController {

    @IBOutlet weak var SpelaButton: UIButton!
    @IBOutlet weak var NameField: UITextField!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureTextFields()
        
        SpelaButton.layer.cornerRadius = 10
        
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(_ animated: Bool) {
        
        //removes locally saved information
        //resetDefaults()
        
        //if there is a locally saved user, skips this view
        if (UserDefaults.standard.object(forKey: "token") != nil) {
            performSegue(withIdentifier: "SpelaSegue", sender: self)
            print("HÄR ÄR JAG")
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func spelaButtonTapped(_ sender: Any) {
        let NameText = NameField.text
        
        //You need to enter something in the username field
        if NameText!.isEmpty {
            let myAlert = UIAlertController(title:"Alert",message:"Du måste skriva in ett användarnamn!",preferredStyle:UIAlertControllerStyle.alert)
            
            let okAction = UIAlertAction(title:"Okej",style:UIAlertActionStyle.cancel,handler: nil)
            
            myAlert.addAction(okAction)
            self.present(myAlert, animated:true,completion:nil)
            
            return
        }
            
        if NameText!.count > 10 {
            let myAlert = UIAlertController(title:"Alert",message:"Max 10 bokstäver!",preferredStyle:UIAlertControllerStyle.alert)
            
            let okAction = UIAlertAction(title:"Okej",style:UIAlertActionStyle.cancel,handler: nil)
            
            myAlert.addAction(okAction)
            self.present(myAlert, animated:true,completion:nil)
            
            return
        }
            
        else {
            let success = geoCap.server.register(userName: NameText!)
            
            if (success == "success"){
                performSegue(withIdentifier: "SpelaSegue", sender: self)
            }
            
            //an alert with information about why your username didnt work
            else {
                let myAlert = UIAlertController(title:"Alert",message:success,preferredStyle:UIAlertControllerStyle.alert)
                
                let okAction = UIAlertAction(title:"Okej",style:UIAlertActionStyle.cancel,handler: nil)
                
                myAlert.addAction(okAction)
                
                self.present(myAlert, animated:true,completion:nil)
            }
        }
    }
    
    private func configureTextFields(){
        NameField.delegate = self
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


extension SpelaViewController: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
}

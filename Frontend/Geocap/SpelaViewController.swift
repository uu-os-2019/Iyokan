//
//  SpelaViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-09.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class SpelaViewController: UIViewController {

    @IBOutlet weak var SpelaButton: UIButton!
    @IBOutlet weak var NameField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureTextFields()
        
        SpelaButton.layer.cornerRadius = 10
        SpelaButton.center.y = 276

        // Do any additional setup after loading the view.
    }
    
    override func viewWillAppear(_ animated: Bool){
        super.viewWillAppear(animated)
        
        SpelaButton.alpha = 0.0
        NameField.alpha = 0.0
    }
    
    override func viewDidAppear(_ animated: Bool){
        super.viewDidAppear(animated)
        
        UIView.animate(withDuration: 1, delay: 0.0, options: [], animations: {
                self.NameField.alpha = 1.0
        }, completion:nil)
        
        UIView.animate(withDuration: 1, delay: 0.0, options: [], animations: {
            self.SpelaButton.alpha = 1.0
            
        }, completion:nil)
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func spelaButtonTapped(_ sender: Any) {
        let NameText = NameField.text
        
        if NameText!.isEmpty {
            let myAlert = UIAlertController(title:"Alert",message:"Du måste skriva in ett användarnamn!",preferredStyle:UIAlertControllerStyle.alert)
            
            let okAction = UIAlertAction(title:"Okej",style:UIAlertActionStyle.cancel,handler: nil)
            
            myAlert.addAction(okAction)
            
            self.present(myAlert, animated:true,completion:nil)
            
            return
        }
        else {
            
            //Skicka användarnamn//
            
            performSegue(withIdentifier: "SpelaSegue", sender: self)
        }
    }
    
    private func configureTextFields(){
        NameField.delegate = self
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

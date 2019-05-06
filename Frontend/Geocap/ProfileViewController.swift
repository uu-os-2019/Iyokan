//
//  Profile.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-04-25.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class Profile: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let string = UserDefaults.standard.object(forKey: "token")
        
        var quiz: Quiz!
        let url = URL(string: "http://3.14.65.225/quiz/start")!
        var request = URLRequest(url: url)
        request.addValue("OsthyvelOsthyvelOsthyvelOsthyvel", forHTTPHeaderField: "Authorization")
        request.httpMethod = "POST"
        let location = ["location": "domkyrkan"]
        
        let json = try? JSONSerialization.data(withJSONObject: location, options: [])
        request.httpBody = json
        
        
        let semaphore = DispatchSemaphore(value: 0) // Semaphore used for forcing dataTask to finish before returning
        
        // Asynchronous function
        URLSession.shared.dataTask(with: request) {(data, response, error) in
            do {
                quiz = try JSONDecoder().decode(Quiz.self, from: data!)
                semaphore.signal()
            } catch {
                print("error in retrieving quiz")
                print(error)
            }
            }.resume()
        // Do any additional setup after loading the view.
    }
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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

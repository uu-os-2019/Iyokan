//
//  QuizPageViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-16.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class QuizPageViewController: UIViewController, UITableViewDelegate/*, UITableViewDataSource*/ {
    /*
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        <#code#>
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        <#code#>
    }
     */
    

    @IBOutlet weak var tableview: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        
        // Do any additional setup after loading the view.
    }
    
    @IBAction func QuizToMap(_ sender: Any) {
        performSegue(withIdentifier: "QuizToMapSegue", sender: self)
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

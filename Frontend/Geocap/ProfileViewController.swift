//
//  Profile.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-04-25.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class Profile: UIViewController, UITableViewDataSource {
    

    @IBOutlet weak var namn: UILabel!
    @IBOutlet weak var points: UILabel!
    @IBOutlet weak var tableView: UITableView!
    
    struct ProfileInfo: Codable {
        let score: Int
        let locations: [String]?
        let type: String
    }
    
    var token: String!
    var locations: [String]?

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableView.dataSource = self
        
        let username = UserDefaults.standard.string(forKey: "username")
        self.namn.text = username
        token = UserDefaults.standard.string(forKey: "token")
        
        
        let profileInfo = geoCap.server.getProfileInfo()
        self.points.text = String(profileInfo!.score)
        
        locations = profileInfo!.locations!
        
        
        // Do any additional setup after loading the view.
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let rows = locations!.count
        return rows
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")!
        
        let text = locations![indexPath.row]
        
        cell.textLabel?.text = text //3.
        
        return cell
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

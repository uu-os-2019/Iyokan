//
//  Profile.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-04-25.
//  Copyright © 2019 Iyokan. All rights reserved.
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
        
        let username = UserDefaults.standard.string(forKey: "username")
        self.namn.text = username
        token = UserDefaults.standard.string(forKey: "token")

        geoCap.server.fetchProfileInfo(completionHandler: updateProfileView)
      
        tableView.dataSource = self
        tableView.tableFooterView = UIView(frame: .zero)
    }
    
    func updateProfileView() {
        points.text = String(geoCap.profileInfo!.score!)
        locations = geoCap.profileInfo!.locations!
        tableView.reloadData()
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let rows = locations?.count ?? 0
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

}

//
//  LeaderboardViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-16.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class LeaderboardViewController: UIViewController, UITableViewDataSource {
    
    @IBOutlet weak var tableLead: UITableView!
    
    let leaderboard = geoCap.server.getLeaderboard()
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return leaderboard.highscore.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableLead.dequeueReusableCell(withIdentifier: "cellHighscore") as! LeadTableCell
        
        cell.rank.text = "\(indexPath.row + 1)" + "."
        cell.name.text = leaderboard.highscore[indexPath.row].name
        cell.points.text = "Points: " + "\(leaderboard.highscore[indexPath.row].points)"
        
        return cell //4.
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        tableLead.dataSource = self
        tableLead.tableFooterView = UIView(frame: .zero)

        // Do any additional setup after loading the view.
    }
    
    @IBAction func LeaderboardToMap(_ sender: Any) {
        performSegue(withIdentifier: "LeaderboardToMapSegue", sender: self)
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

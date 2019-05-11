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
    
    var leaderboard = geoCap.server.getLeaderboard()
    
    private var data: [String] = []
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return leaderboard?.highscore.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableLead.dequeueReusableCell(withIdentifier: "cellHighscore") as! LeadTableCell
        
        cell.rank.text = data[indexPath.row] + "."
        cell.name.text = leaderboard?.highscore[indexPath.row].name
        cell.points.text = "Points: " + "\(leaderboard?.highscore[indexPath.row].points ?? 0)"
        
        return cell
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if leaderboard != nil {
            for i in 1...leaderboard!.highscore.count {
                data.append("\(i)")
            }
        }
        
        tableLead.dataSource = self
        tableLead.tableFooterView = UIView(frame: .zero)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        if leaderboard == nil {
            let alertController = UIAlertController(title: "Något gick fel", message: "Det gick inte att hämta leaderboarden, försök igen senare", preferredStyle: .alert)
            let OKAction = UIAlertAction(title: "OK", style: .default) { (action:UIAlertAction!) in
                self.performSegue(withIdentifier: "LeaderboardToMapSegue", sender: self)
            }
            alertController.addAction(OKAction)
            self.present(alertController, animated: true, completion:nil)
        }
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

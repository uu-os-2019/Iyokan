//
//  LeadTotalViewController.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-05-15.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class LeadTotalViewController: UIViewController, UITableViewDataSource {

    @IBOutlet weak var tableLead: UITableView!
    
    var leaderboard = geoCap.server.getLeaderboardTotal()
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return leaderboard?.users.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableLead.dequeueReusableCell(withIdentifier: "cellHighscore") as! LeadTableCell
        
        cell.rank.text = "\(indexPath.row + 1)" + "."
        cell.name.text = leaderboard?.users[indexPath.row].name
        cell.points.text = "Level: " + "\(leaderboard?.users[indexPath.row].level ?? 0)"
        
        return cell
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        
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
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}

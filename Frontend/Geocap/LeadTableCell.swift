//
//  LeadTableCell.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-05-08.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class LeadTableCell: UITableViewCell {

    
    @IBOutlet weak var rank: UILabel!
    @IBOutlet weak var name: UILabel!
    @IBOutlet weak var points: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}

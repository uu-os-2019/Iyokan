//
//  SegueFromTop.swift
//  Geocap
//
//  Created by Erik Hellström on 2019-05-09.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class SegueFromTop: UIStoryboardSegue {
        override func perform() {
            let src = self.source as UIViewController
            let dst = self.destination as UIViewController
            
            src.view.superview?.insertSubview(dst.view, aboveSubview: src.view)
            dst.view.transform = CGAffineTransform(translationX: 0, y: -src.view.frame.size.height)
            
            UIView.animate(withDuration: 0.75, animations: {
                dst.view.transform = CGAffineTransform(translationX: 0, y: 0)
                
            }) { (Finished) in
                src.present(dst, animated: false, completion: nil)
            }
        }
}

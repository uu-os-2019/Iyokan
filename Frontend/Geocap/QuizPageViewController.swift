//
//  QuizPageViewController.swift
//  Geocap
//
//  Created by Oscar Englöf on 2019-04-16.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import UIKit

class QuizPageViewController: UIViewController {
   
    


    @IBOutlet weak var question: UILabel!
    
    @IBOutlet weak var alternative1: UIButton!
    
    @IBOutlet weak var alternative2: UIButton!
    
    @IBOutlet weak var alternative3: UIButton!
    
    @IBOutlet weak var alternative4: UIButton!
    
    
    let quiz = server.getQuiz()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.question.text = quiz?.question
        
        self.alternative1.setTitle(quiz?.alternatives[0], for: .normal)
        
        self.alternative2.setTitle(quiz?.alternatives[1], for: .normal)
        
        self.alternative3.setTitle(quiz?.alternatives[2], for: .normal)
        
        self.alternative4.setTitle(quiz?.alternatives[3], for: .normal)
        
        
        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func alternative1(_ sender: Any) {
        let answer = quiz?.alternatives[0]
        let correct = server.sendQuizAnswer(answer: answer!)
        if(correct) {
            self.alternative1.backgroundColor = UIColor.green
        }
    }
    
    @IBAction func alternative2(_ sender: Any) {
        let answer = quiz?.alternatives[0]
        //let correct = server.sendQuizAnswer(answer: answer!)
        if(correct) {
            self.alternative1.backgroundColor = UIColor.green
        }
        
    }
    @IBAction func alternative3(_ sender: Any) {
        let answer = quiz?.alternatives[0]
        let correct = server.sendQuizAnswer(answer: answer!)
        if(correct) {
            self.alternative1.backgroundColor = UIColor.green
        }
    }
    
    @IBAction func alternative4(_ sender: Any) {
        let answer = quiz?.alternatives[0]
        let correct = server.sendQuizAnswer(answer: answer!)
        if(correct) {
            self.alternative1.backgroundColor = UIColor.green
        }
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

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
    
    @IBOutlet weak var nextQuestion: UIButton!
    
    
    
    let quiz = server.getQuiz()
    
    var quizAnswer: QuizAnswer!
    
    var lastQuizAnswer: LastQuizAnswer!
    
    var counter = 0
    
    var quizTimeoutIsActive = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        

        self.question.text = quiz?.question
        self.nextQuestion.isHidden = true
        
        
        self.alternative1.setTitle(quiz?.alternatives[0], for: .normal)
        
        self.alternative2.setTitle(quiz?.alternatives[1], for: .normal)
        
        self.alternative3.setTitle(quiz?.alternatives[2], for: .normal)
        
        self.alternative4.setTitle(quiz?.alternatives[3], for: .normal)
        
        self.alternative1.backgroundColor = UIColor.blue
        self.alternative2.backgroundColor = UIColor.blue
        self.alternative3.backgroundColor = UIColor.blue
        self.alternative4.backgroundColor = UIColor.blue
        
        
        
        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func alternative1(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[0]
            quizAnswer = server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[0])!)
                if(lastQuizAnswer.successfulTakeover) {
                    let alert = UIAlertController(title: "Grattis du tog över området", message: "", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                else {
                    let alert = UIAlertController(title: "Tyvärr, du fick inte området", message: "Kom tillbaka om 30 sek och försök igen", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                
            }
            else {
                quizAnswer = server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[0])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative1.backgroundColor = UIColor.green
        }
        else {
            self.alternative1.backgroundColor = UIColor.red
        }
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    
    @IBAction func alternative2(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[1]
            quizAnswer = server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[1])!)
                if(lastQuizAnswer.successfulTakeover) {
                    let alert = UIAlertController(title: "Grattis du tog över området", message: "", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                else {
                    let alert = UIAlertController(title: "Tyvärr, du fick inte området", message: "Kom tillbaka om 30 sek och försök igen", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                
            }
            else {
                quizAnswer = server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[1])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative2.backgroundColor = UIColor.green
        }
        else {
            self.alternative2.backgroundColor = UIColor.red
        }
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    @IBAction func alternative3(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[2]
            quizAnswer = server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[2])!)
                if(lastQuizAnswer.successfulTakeover) {
                    let alert = UIAlertController(title: "Grattis du tog över området", message: "", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                else {
                    let alert = UIAlertController(title: "Tyvärr, du fick inte området", message: "Kom tillbaka om 30 sek och försök igen", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                
            }
            else {
                quizAnswer = server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[2])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative3.backgroundColor = UIColor.green
        }
        else {
            self.alternative3.backgroundColor = UIColor.red
        }
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    
    @IBAction func alternative4(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[3]
            quizAnswer = server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[3])!)
                if(lastQuizAnswer.successfulTakeover) {
                    let alert = UIAlertController(title: "Grattis du tog över området", message: "", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                else {
                    let alert = UIAlertController(title: "Tyvärr, du fick inte området", message: "Kom tillbaka om 30 sek och försök igen", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "Stäng", style: .default, handler: nil))
                    self.present(alert, animated: true)
                }
                
            }
            else {
                quizAnswer = server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[3])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative4.backgroundColor = UIColor.green
        }
        else {
            self.alternative4.backgroundColor = UIColor.red
        }
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    
    func getNewQuestions(quizAnswer: QuizAnswer?) {
        self.alternative1.backgroundColor = UIColor.blue
        self.alternative2.backgroundColor = UIColor.blue
        self.alternative3.backgroundColor = UIColor.blue
        self.alternative4.backgroundColor = UIColor.blue
        self.alternative1.setTitle(quizAnswer?.newAlternatives[0], for: .normal)
        self.alternative2.setTitle(quizAnswer?.newAlternatives[1], for: .normal)
        self.alternative3.setTitle(quizAnswer?.newAlternatives[2], for: .normal)
        self.alternative4.setTitle(quizAnswer?.newAlternatives[3], for: .normal)
        self.question.text = quizAnswer?.newQuestion
    }
    
    
    @IBAction func getNextQuestion(_ sender: Any) {
        getNewQuestions(quizAnswer: quizAnswer)
        
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

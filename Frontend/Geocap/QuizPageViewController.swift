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
    

    let quiz = geoCap.server.getQuiz(for: geoCap.currentLocation!)

    
    var quizAnswer: QuizAnswer!
    
    var lastQuizAnswer: LastQuizAnswer!
    
    var counter = 0
    
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
        
        self.alternative1.showsTouchWhenHighlighted = true
        self.alternative2.showsTouchWhenHighlighted = true
        self.alternative3.showsTouchWhenHighlighted = true
        self.alternative4.showsTouchWhenHighlighted = true

        
        
        
        // Do any additional setup after loading the view.
    }
    
    
    @IBAction func alternative1(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[0]
            quizAnswer = geoCap.server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = geoCap.server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[0])!)
                if(lastQuizAnswer.successfulTakeover) {
             
                    doneWithQuizWin()

                }
                else {
                    showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                    doneWithQuizLoss()
                }
            }
            else {
                quizAnswer = geoCap.server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[0])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative1.backgroundColor = UIColor.green
        }
        else {
            showCorrectAnswer(answer: quizAnswer.correctAnswer!)
            self.alternative1.backgroundColor = UIColor.red
        }
        disableButtons()
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    
    @IBAction func alternative2(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[1]
            quizAnswer = geoCap.server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = geoCap.server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[1])!)
                if(lastQuizAnswer.successfulTakeover) {
                   
                    doneWithQuizWin()

                }
                else {
                    showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                    doneWithQuizLoss()
                }
                
            }
            else {
                quizAnswer = geoCap.server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[1])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative2.backgroundColor = UIColor.green
        }
        else {
            showCorrectAnswer(answer: quizAnswer.correctAnswer!)
            self.alternative2.backgroundColor = UIColor.red
        }
        disableButtons()
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    @IBAction func alternative3(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[2]
            quizAnswer = geoCap.server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = geoCap.server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[2])!)
                if(lastQuizAnswer.successfulTakeover) {
                
                    doneWithQuizWin()
                }
                else {
                    
                    showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                    doneWithQuizLoss()

                    
                    geoCap.quizModel.quizTimeoutIsActive = true
                    geoCap.quizModel.startQuizTimer()
                }
                
            }
            else {
                quizAnswer = geoCap.server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[2])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative3.backgroundColor = UIColor.green
        }
        else {
            showCorrectAnswer(answer: quizAnswer.correctAnswer!)
            self.alternative3.backgroundColor = UIColor.red
        }
        disableButtons()
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    
    @IBAction func alternative4(_ sender: Any) {
        if(counter == 0) {
            let answer = quiz?.alternatives[3]
            quizAnswer = geoCap.server.sendQuizAnswer(answer: answer!)
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = geoCap.server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives[3])!)
                if(lastQuizAnswer.successfulTakeover) {
                   
                    doneWithQuizWin()

                }
                else {
                    showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                    doneWithQuizLoss()
                }
                
            }
            else {
                quizAnswer = geoCap.server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives[3])!)
            }
        }
        
        
        if((quizAnswer?.correct)!) {
            self.alternative4.backgroundColor = UIColor.green
        }
        else {
            showCorrectAnswer(answer: quizAnswer.correctAnswer!)
            self.alternative4.backgroundColor = UIColor.red
        }
        disableButtons()
        counter += 1
        self.nextQuestion.isHidden = false
        
    }
    
    func showCorrectAnswer(answer: String) {
        let buttons = [alternative1, alternative2, alternative3, alternative4]
        if(counter == 0) {
            for alternative in (buttons) {
                if(alternative!.titleLabel!.text == answer) {
                    alternative!.backgroundColor = UIColor.green
                }
            }
        }
        else {
            for alternative in (buttons) {
                if(alternative!.titleLabel!.text == answer) {
                    alternative!.backgroundColor = UIColor.green
                }
            }
        }
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
    
    func disableButtons() {
        self.alternative1.isEnabled = false
        self.alternative2.isEnabled = false
        self.alternative3.isEnabled = false
        self.alternative4.isEnabled = false
    }
    func enableButtons() {
        self.alternative1.isEnabled = true
        self.alternative2.isEnabled = true
        self.alternative3.isEnabled = true
        self.alternative4.isEnabled = true
    }
    
    func doneWithQuizWin() {
        let alertController = UIAlertController(title: "Grattis du tog över området!", message: "", preferredStyle: .alert)
        
        // Create OK button
        let OKAction = UIAlertAction(title: "Tillbaka till kartan", style: .default) { (action:UIAlertAction!) in
            
            // Code in this block will trigger when OK button tapped.
            self.performSegue(withIdentifier: "QuizToMapSegue", sender: self)

            print("Ok button tapped");
            
        }
        alertController.addAction(OKAction)
        
        // Present Dialog message
        self.present(alertController, animated: true, completion:nil)
    }
    func doneWithQuizLoss() {
        let alertController = UIAlertController(title: "Du misslyckades med att ta över området", message: "Vänta 30 sekunder och försök igen!", preferredStyle: .alert)
        
        // Create OK button
        let OKAction = UIAlertAction(title: "Tillbaka till kartan", style: .default) { (action:UIAlertAction!) in
            
            // Code in this block will trigger when OK button tapped.
            self.performSegue(withIdentifier: "QuizToMapSegue", sender: self)
            
            print("Ok button tapped");
            
        }
        alertController.addAction(OKAction)
        
        // Present Dialog message
        self.present(alertController, animated: true, completion:nil)
    }
    
    
    @IBAction func getNextQuestion(_ sender: Any) {
        self.nextQuestion.isHighlighted = true
        enableButtons()
        getNewQuestions(quizAnswer: quizAnswer)
        
    }
    
    
    @IBAction func QuizToMap(_ sender: Any) {
        performSegue(withIdentifier: "QuizToMapSegue", sender: self)
    }
    

}

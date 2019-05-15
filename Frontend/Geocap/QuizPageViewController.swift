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
    @IBOutlet var answerButtons: [UIButton]!
    @IBOutlet weak var progressView: UIProgressView!
    
    let quiz = geoCap.server.getQuiz(for: geoCap.currentLocation!)
    var quizAnswer: QuizAnswer!
    var lastQuizAnswer: LastQuizAnswer!
    var counter = 0
    var alternatives = [alternative1, alternative2, alternative3, alternative4]
    var timer: Timer?
    var totalTime = 1000
    var timeRemaining = 1000
    var timerIsOn: Bool?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.question.text = quiz?.question
        self.nextQuestion.isHidden = true

        self.alternative1.setTitle(quiz?.alternatives![0], for: .normal)
        self.alternative2.setTitle(quiz?.alternatives![1], for: .normal)
        self.alternative3.setTitle(quiz?.alternatives![2], for: .normal)
        self.alternative4.setTitle(quiz?.alternatives![3], for: .normal)

        self.alternative1.backgroundColor = UIColor.blue
        self.alternative2.backgroundColor = UIColor.blue
        self.alternative3.backgroundColor = UIColor.blue
        self.alternative4.backgroundColor = UIColor.blue
        progressView.progressTintColor = UIColor.green
        quizTimer()
        
        
    }

    override func viewDidAppear(_ animated: Bool) {
        if quiz == nil {
            let alertController = UIAlertController(title: "Något gick fel", message: "Det gick inte att hämta quizen, försök igen senare", preferredStyle: .alert)
            let OKAction = UIAlertAction(title: "OK", style: .default) { (action:UIAlertAction!) in
                self.performSegue(withIdentifier: "QuizToMapSegue", sender: self)
            }
            alertController.addAction(OKAction)
            self.present(alertController, animated: true, completion:nil)
        }
    }
    
    func chooseAnswer(alternative: Int) {
        stopTimer()
        if(counter == 0) {
            let answer = quiz?.alternatives![alternative]
            quizAnswer = geoCap.server.sendQuizAnswer(answer: answer!)
            
            if((quizAnswer?.correct)!) {
                answerButtons[alternative].backgroundColor = UIColor.green
            }
            else {
                showCorrectAnswer(answer: quizAnswer.correctAnswer!)
                answerButtons[alternative].backgroundColor = UIColor.red
            }
            disableButtons()
            counter += 1
            self.nextQuestion.isHidden = false
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = geoCap.server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives![alternative])!)
                if(lastQuizAnswer.successfulTakeover) {
                    if((lastQuizAnswer?.correct)!) {
                        answerButtons[alternative].backgroundColor = UIColor.green
                    }
                    else {
                        showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                        answerButtons[alternative].backgroundColor = UIColor.red
                    }
                    doneWithQuizWin()
                    
                }
                else {
                    if((lastQuizAnswer?.correct)!) {
                        answerButtons[alternative].backgroundColor = UIColor.green
                    }
                    else {
                        showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                        answerButtons[alternative].backgroundColor = UIColor.red
                    }
                    geoCap.quizModel.startQuizTimer()
                    doneWithQuizLoss()
                    
                }
            }
            else {
                quizAnswer = geoCap.server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives![alternative])!)
                if((quizAnswer?.correct)!) {
                    answerButtons[alternative].backgroundColor = UIColor.green
                }
                else {
                    showCorrectAnswer(answer: quizAnswer.correctAnswer!)
                    answerButtons[alternative].backgroundColor = UIColor.red
                }
                disableButtons()
                counter += 1
                self.nextQuestion.isHidden = false
            }
        }
        
        
    }
    
    @IBAction func alternative1(_ sender: UIButton) {
        
        UIButton.animate(withDuration: 0.25,
                         animations: {
                            sender.transform = CGAffineTransform(scaleX: 0.94, y: 0.94)
        },
                         completion: { finish in
                            UIButton.animate(withDuration: 0.25, animations: {
                                sender.transform = CGAffineTransform.identity
                            })
        })
    
        chooseAnswer(alternative: 0)

    }
    
    @IBAction func alternative2(_ sender: UIButton) {
        
        UIButton.animate(withDuration: 0.25,
                         animations: {
                            sender.transform = CGAffineTransform(scaleX: 0.94, y: 0.94)
        },
                         completion: { finish in
                            UIButton.animate(withDuration: 0.25, animations: {
                                sender.transform = CGAffineTransform.identity
                            })
        })
        
       chooseAnswer(alternative: 1)
        
    }
    @IBAction func alternative3(_ sender: UIButton) {
        
        UIButton.animate(withDuration: 0.25,
                         animations: {
                            sender.transform = CGAffineTransform(scaleX: 0.94, y: 0.94)
        },
                         completion: { finish in
                            UIButton.animate(withDuration: 0.25, animations: {
                                sender.transform = CGAffineTransform.identity
                            })
        })
        
        chooseAnswer(alternative: 2)
        
    }
    
    @IBAction func alternative4(_ sender: UIButton) {
        
        UIButton.animate(withDuration: 0.25,
                         animations: {
                            sender.transform = CGAffineTransform(scaleX: 0.94, y: 0.94)
        },
                         completion: { finish in
                            UIButton.animate(withDuration: 0.25, animations: {
                                sender.transform = CGAffineTransform.identity
                            })
        })
        
        chooseAnswer(alternative: 3)
        
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
        self.alternative1.setTitle(quizAnswer?.newAlternatives![0], for: .normal)
        self.alternative2.setTitle(quizAnswer?.newAlternatives![1], for: .normal)
        self.alternative3.setTitle(quizAnswer?.newAlternatives![2], for: .normal)
        self.alternative4.setTitle(quizAnswer?.newAlternatives![3], for: .normal)
        self.question.text = quizAnswer?.newQuestion
        quizTimer()
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
        }
        alertController.addAction(OKAction)
        
        // Present Dialog message
        self.present(alertController, animated: true, completion:nil)
    }
    
    func toSlowAnswer() {
        let alertController = UIAlertController(title: "Du svarade för långsamt", message: "", preferredStyle: .alert)
        
        // Create OK button
        let OKAction = UIAlertAction(title: "Okej", style: .default) { (action:UIAlertAction!) in
            
            // Code in this block will trigger when OK button tapped.
            
        }
        alertController.addAction(OKAction)
        
        // Present Dialog message
        self.present(alertController, animated: true, completion:nil)
    }
    
    
    @IBAction func getNextQuestion(_ sender: UIButton) {
        
        UIButton.animate(withDuration: 0.25,
                         animations: {
                            sender.transform = CGAffineTransform(scaleX: 0.90, y: 0.90)
        },
                         completion: { finish in
                            UIButton.animate(withDuration: 0.25, animations: {
                                sender.transform = CGAffineTransform.identity
                            })
        })
        stopTimer()
        self.nextQuestion.isHighlighted = true
        enableButtons()
        getNewQuestions(quizAnswer: quizAnswer)
        
    }
    
    func quizTimer() {
        timerIsOn = true
        timer = Timer.scheduledTimer(withTimeInterval: 0.01, repeats: true) { Timer in
            if(!self.timerIsOn!) {
                self.stopTimer()
            }
            Timer.tolerance = 2
            self.timerRunning()
        }
    }
    
    func stopTimer() {
        progressView.progressTintColor = UIColor.green
        timeRemaining = 1000
        timer?.invalidate()
        self.timer = nil
    }
    
    func timerRunning() {
        timeRemaining -= 1
        progressView.setProgress(Float(timeRemaining)/Float(totalTime), animated: true)
        if(timeRemaining == 750) {
            progressView.progressTintColor = UIColor.yellow
        }
        else if(timeRemaining == 500) {
            progressView.progressTintColor = UIColor.orange
        }
        else if(timeRemaining == 250) {
            progressView.progressTintColor = UIColor.red
        }
        
        else if(timeRemaining == 0) {
            defaultChooseAnswer()
            toSlowAnswer()
            timerIsOn = false
        }
    
    }
    func defaultChooseAnswer() {
        
        if(counter == 0) {
            let answer = quiz?.alternatives![0]
            quizAnswer = geoCap.server.sendQuizAnswer(answer: answer!)
            
            if((quizAnswer?.correct)!) {
                answerButtons[0].backgroundColor = UIColor.green
            }
            else {
                showCorrectAnswer(answer: quizAnswer.correctAnswer!)
            }
            disableButtons()
            counter += 1
            self.nextQuestion.isHidden = false
        }
        else {
            if(counter == 2) {
                lastQuizAnswer = geoCap.server.sendLastQuizAnswer(answer: (quizAnswer?.newAlternatives![0])!)
                if(lastQuizAnswer.successfulTakeover) {
                    if((lastQuizAnswer?.correct)!) {
                        answerButtons[0].backgroundColor = UIColor.green
                    }
                    else {
                        showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                    }
                    doneWithQuizWin()
                    
                }
                else {
                    if((lastQuizAnswer?.correct)!) {
                        answerButtons[0].backgroundColor = UIColor.green
                    }
                    else {
                        showCorrectAnswer(answer: lastQuizAnswer.correctAnswer!)
                    }
                    geoCap.quizModel.startQuizTimer()
                    doneWithQuizLoss()
                    
                }
            }
            else {
                quizAnswer = geoCap.server.sendQuizAnswer(answer: (quizAnswer?.newAlternatives![0])!)
                if((quizAnswer?.correct)!) {
                    answerButtons[0].backgroundColor = UIColor.green
                }
                else {
                    showCorrectAnswer(answer: quizAnswer.correctAnswer!)
                }
                disableButtons()
                counter += 1
                self.nextQuestion.isHidden = false
            }
        }
        
        
    }
    @IBAction func QuizToMap(_ sender: Any) {
        performSegue(withIdentifier: "QuizToMapSegue", sender: self)
    }
    

}

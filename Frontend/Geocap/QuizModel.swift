//
//  QuizModel.swift
//  Geocap
//
//  Created by Benjamin Angeria on 2019-05-06.
//

import Foundation

class QuizModel {
    
    var quizTimeoutIsActive = false
    var quizTimer: Timer?
    
    init() {}

    func startQuizTimer() {
        quizTimer = Timer.scheduledTimer(withTimeInterval: 30, repeats: false) { Timer in
            Timer.tolerance = 2
            self.quizTimeoutIsActive = false
            Timer.invalidate()
            self.quizTimer = nil
        }
    }
    
}

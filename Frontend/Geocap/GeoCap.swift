//
//  GeoCap.swift
//  Geocap
//
//  Created by Benjamin Angeria on 2019-05-06.
//  Copyright © 2019 Oscar Englöf. All rights reserved.
//

import Foundation

class GeoCap {
    let server: Server
    let quizModel: QuizModel
    
    init() {
        self.server = Server()
        self.quizModel = QuizModel()
    }
}

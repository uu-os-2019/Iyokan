package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.QuizRound;
import com.iyokan.geocapserver.QuizRoundCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RouteQuizAnswer extends Route {
    QuizRoundCollection quizRoundCollection;

    public RouteQuizAnswer(QuizRoundCollection quizRoundCollection){this.quizRoundCollection = quizRoundCollection;}


    public JSONObject handle(RequestData data) {
        JSONObject json = data.getJSON();

        JSONObject response = new JSONObject();

        response.put("type", "quiz_answer");
        String request = data.getRequest();

        String question = json.getString("question");
        String answer = json.getString("answer");

        //Hittar rätt quizRound i collections och kontrollerar spelar svaret och sätter bool i JSON
        QuizRound[] quizRounds = quizRoundCollection.getAllQuizRounds();
        QuizRound QR = null;
        for(QuizRound quizRound: quizRounds){
            if(quizRound.getQuestion().equals(question)){
                QR = quizRound;
                break;
            }
        }
        if (QR != null){
            boolean correct = QR.checkAnswer(answer);
            response.put("correct", correct);
        } else {
            System.out.println("Error: question does not exist");
            response.put("correct", false);
        }

        return response;
    }

    @Override
    public String getUrl() { return "/quiz/get-answer";}
}

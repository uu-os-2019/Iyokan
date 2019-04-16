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

    //Tar http GET strängen från URLen och mappar ut alla variabler
    //T.ex. queryToMap("?question=när?&answer=100") -> Map(question, när)..etc
    //TODO flytta funktionen till rätt ställe, lär behövas på andra ställen
    public Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();

        response.put("type", "quiz_answer");
        String request = data.getRequest();
        Map<String, String> params = queryToMap(request); //TODO: nullpointer exception handling if query is null
        String question = params.get("question");
        String answer = params.get("answer");

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

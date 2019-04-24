package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class RouteQuizStart extends Route {
    QuizRoundCollection quizRoundCollection;
    LocationCollection locationCollection;

    public RouteQuizStart(QuizRoundCollection quizRoundCollection, LocationCollection locationCollection){
        this.quizRoundCollection = quizRoundCollection;
        this.locationCollection = locationCollection;
    }

    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();
        JSONObject json = data.getJSON();
        boolean success = false;

        response.put("type", "quiz_start");
        JSONArray array = new JSONArray();

        if(json != null) {
            String location = json.getString("location");
            if(locationCollection.getLocation(location) != null) {
                success = true;
                QuizSession quizSession = new QuizSession(quizRoundCollection);
                data.getUser().setQuizSession(quizSession);

                QuizRound quizRound = quizSession.getQuestion();

                response.put("question", quizRound.getQuestion());
                response.put("alternatives", quizRound.getAlternatives());
            } else {
                success = false;
                response.put("reason", "Couldn't find location");
            }
        }

        response.put("success", success);

        return response;
    }

    @Override
    public String getUrl(){ return "/quiz/get-round";}
}

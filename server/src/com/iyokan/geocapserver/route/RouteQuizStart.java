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

        response.put("type", "quiz_start");

        if (data.getUser() == null) {
            response.put("success", false);
            response.put("reason", "no user for token");
            return response;
        }

        if (json == null) {
            response.put("success", false);
            response.put("reason", "Couldn't find location");
            return response;
        }

        String locationID = json.getString("location");
        Location location = locationCollection.getLocation(locationID);

        if (location == null) {
            response.put("reason", "Couldn't find location");
        }



        QuizSession quizSession = new QuizSession(quizRoundCollection, location);
        data.getUser().setQuizSession(quizSession);

        QuizRound quizRound = quizSession.getQuestion();

        response.put("success", true);
        response.put("question", quizRound.getQuestion());
        response.put("alternatives", quizRound.getAlternatives());


        return response;
    }

    @Override
    public String getUrl(){ return "/quiz/start";}
}

package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RouteQuizAnswer extends Route {
    private LocationCollection locationCollection;
    QuizRoundCollection quizRoundCollection;
    Highscore hs;
    UserCollection users;

    public RouteQuizAnswer(QuizRoundCollection quizRoundCollection, LocationCollection collection, Highscore hs, UserCollection users){
        this.quizRoundCollection = quizRoundCollection;
        this.hs = hs;
        this.locationCollection = collection;
        this.users = users;
    }


    public JSONObject handle(RequestData data) {
        JSONObject json = data.getJSON();

        JSONObject response = new JSONObject();

        response.put("type", "quiz_answer");
        User me = data.getUser();

        if (me == null) {
            response.put("success", false);
            response.put("reason", "no user");

            return response;
        }

        String answer = Utils.getJsonDefault(json, "answer", "");
        QuizSession quizSession = me.getQuizSession();

        if (quizSession == null) {
            response.put("success", false);
            response.put("reason", "no active quiz");

            return response;
        }

        response.put("success", true);
        QuizRound round = quizSession.getQuestion();
        boolean correctAnswer = quizSession.answer(answer);

        response.put("correct", correctAnswer);
        if (correctAnswer == false) {
            response.put("correct_answer", round.getCorrectAnswer());
        }

        int score = quizSession.getScore();
        response.put("points", score);

        if(quizSession.isDone() == false) {
            response.put("new_question", quizSession.getQuestion().getQuestion());
            response.put("new_alternatives", quizSession.getQuestion().getAlternativesRandom());
        } else {
            response.put("new_question", JSONObject.NULL);
            response.put("new_alternatives", JSONObject.NULL);
            Location location = quizSession.getLocation();
            me.setQuizSession(null);


            if(location.hasOwner() == false || location.getScore() <= score) {
                response.put("successful_takeover", true);

                if(location.hasOwner()) {
                    User oldOwner = users.getUser(location.getOwner());
                    oldOwner.removeLocation(location.getId());
                    oldOwner.updatePointRate(-1); //add logic for weighted score
                    users.updateUser(oldOwner);
                }

                hs.updateHighscore(me.getID(), score);
                location.setOwner(me.getID(), score);
                locationCollection.updateLocation(location);

                me.addLocation(location.getId());
                me.updatePointRate(1); //add logic for weighted score
                users.updateUser(me);

            } else {
                response.put("successful_takeover", false);
            }
        }

        return response;
    }

    @Override
    public String getUrl() { return "/quiz/answer";}
}

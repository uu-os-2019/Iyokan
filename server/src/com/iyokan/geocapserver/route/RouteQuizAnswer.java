package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.QuizRound;
import com.iyokan.geocapserver.QuizRoundCollection;
import com.iyokan.geocapserver.QuizSession;
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
        boolean success = true;

        response.put("type", "quiz_answer");
        String request = data.getRequest();

        String answer = json.getString("answer");

        QuizSession quizSession = data.getUser().getQuizSession();
        response.put("type", "quiz_answer");
        response.put("success", success);
        response.put("correct", quizSession.answer(answer));
        response.put("points", quizSession.getScore());

        if(quizSession.isDone() == false) {
            response.put("new_question", quizSession.getQuestion().getQuestion());
            response.put("new_alternatives", quizSession.getQuestion().getAlternatives());
        } else {
            response.put("new_question", JSONObject.NULL);
            if(!quizSession.getLocation().hasOwner()) {
                response.put("successful_takeover", "true");
                quizSession.getLocation().setScore(quizSession.getScore());
                quizSession.getLocation().setOwner(quizSession.getUser());
            } else if(quizSession.getLocation().getScore() < quizSession.getScore()) {
                response.put("successful_takeover", "true");
                quizSession.getLocation().setScore(quizSession.getScore());
                quizSession.getLocation().setOwner(quizSession.getUser());
            } else {
                response.put("successful_takeover", "false");
            }
        }

        return response;
    }

    @Override
    public String getUrl() { return "/quiz/answer";}
}

package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.Location;
import com.iyokan.geocapserver.QuizRound;
import com.iyokan.geocapserver.QuizRoundCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class RouteQuizStart extends Route {
    QuizRoundCollection quizRoundCollection;

    public RouteQuizStart(QuizRoundCollection quizRoundCollection){ this.quizRoundCollection = quizRoundCollection;}

    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();

        response.put("type", "quiz_start");
        JSONArray array = new JSONArray();
        QuizRound[] quizRounds = quizRoundCollection.getAllQuizRounds();

        //randomize vilken quizRound
        //TODO se till att man inte får samma fråga igen
        int random = (int)(Math.random() * quizRounds.length);
        QuizRound quizRound = quizRounds[random];

        //randomize ordning av svar
        ArrayList<String> alternatives = quizRound.getAlternatives();
        Collections.shuffle(alternatives);

        String q = quizRound.getQuestion();
        response.put("question", q);
        response.put("alternatives", alternatives);

        return response;
    }

    @Override
    public String getUrl(){ return "/quiz/get-round";}
}

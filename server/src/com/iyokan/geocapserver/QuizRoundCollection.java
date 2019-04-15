package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class QuizRoundCollection {
    private ArrayList<QuizRound> quizRounds;

    public QuizRoundCollection(){
        quizRounds = new ArrayList<QuizRound>();
    }

    public void loadQuizRounds(JSONArray array) {
        for (int i=0; i < array.length(); i++) {
            QuizRound quizRound = new QuizRound(array.getJSONObject(i));
            quizRounds.add(quizRound);
        }
    }

}

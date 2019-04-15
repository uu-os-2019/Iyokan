package com.iyokan.geocapserver;

import org.json.JSONObject;

import java.util.ArrayList;

public class QuizRound {
    private String question;
    private ArrayList<String> alternatives;


    public QuizRound(String question, ArrayList<String> alternatives){
        this.question = question;
        this.alternatives = alternatives;
    }

    public String getQuestion(){
        return question;
    }

    public ArrayList<String> getAlternative(){
        return alternatives;
    }

    public QuizRound(JSONObject object){
        this.question  = object.getString("question");
        JSONObject object2 = object.getJSONObject("alternatives");
        this.alternatives.add(object2.getString("alt1"));
        this.alternatives.add(object2.getString("alt2"));
        this.alternatives.add(object2.getString("alt3"));
        this.alternatives.add(object2.getString("alt4"));

    }


}

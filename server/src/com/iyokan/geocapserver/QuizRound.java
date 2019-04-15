package com.iyokan.geocapserver;

import org.json.JSONObject;

import java.util.ArrayList;

public class QuizRound {
    private String question;
    private ArrayList<String> alternatives = new ArrayList<String>();


    public QuizRound(String question, ArrayList<String> alternatives){
        this.question = question;
        this.alternatives = alternatives;
    }

    public String getQuestion(){
        return question;
    }

    public ArrayList<String> getAlternatives(){
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

    public boolean CheckAnswer(String playerAnswer){
        if (playerAnswer.equalsIgnoreCase(alternatives.get(0))){
            return true;
        }
        else {
            return false;
        }
    }


}

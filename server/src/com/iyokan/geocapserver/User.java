package com.iyokan.geocapserver;

import org.json.JSONObject;

public class User {
    private final UserGuid id;
    private String name;
    private Position position;
    private QuizSession quizSession;

    public User(UserGuid id, String name) {
        this.id = id;
        this.name = name;
    }


    public UserGuid getID() {
        return id;
    }

    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id.toString());
        obj.put("name", name);

        return obj;
    }

    public int getScore(LocationCollection allLocations) {
        int playerScore = 0;
        Location[] allLocationArray = allLocations.getAllLocations();

        for (Location i : allLocationArray) {
            if (i.hasOwner() && this.id.equals(i.getOwner())) {
                playerScore += i.getScore();
            }
        }
        return playerScore;
    }

    public String getName() {
        return name;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }
}

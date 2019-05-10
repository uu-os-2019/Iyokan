package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private final UserGuid id;
    private String name;
    private Position position;
    private QuizSession quizSession;
    private ArrayList<String> locationsTaken = new ArrayList<>();
    private int pointRate;
    private int lastCalculatedScore;
    private long timeLastCalculated;


    public User(UserGuid id, String name) {
        this.id = id;
        this.name = name;
        this.locationsTaken = new ArrayList<>();
        this.pointRate = 0;
        this.lastCalculatedScore = 0;

    }

    public User(UserGuid id, String name, ArrayList<String> locations) {
        this.id = id;
        this.name = name;
        this.locationsTaken = locations;
        this.pointRate = 0;
        this.lastCalculatedScore = 0;
    }

    public User(UserGuid id, String name, ArrayList<String> locations, int pointRate, int lastCalculatedScore, long timeLastCalculated){
        this.id = id;
        this.name = name;
        this.locationsTaken = locations;
        this.pointRate = pointRate;
        this.lastCalculatedScore = lastCalculatedScore;
        this.timeLastCalculated = timeLastCalculated;
    }

    public User(JSONObject json) {
        this.id = new UserGuid(json.getString("id"));
        this.name = json.getString("name");

        if (json.has("pointRate")){
            this.pointRate = json.getInt("pointRate");
        }
        if (json.has("lastCalculatedScore")) {
            this.lastCalculatedScore = json.getInt("lastCalculatedScore");
        }
        if (json.has("timeLastCalculated")) {
            this.timeLastCalculated = json.getLong("timeLastCalculated");
        }

        if (json.has("locationsTaken")) {
            JSONArray array = json.getJSONArray("locationsTaken");
            for (int i = 0; i < array.length(); i++) {
                this.locationsTaken.add(array.get(i).toString());
            }
        }
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

    public JSONObject getPrivateJson() {
        JSONObject obj = getJson();
        obj.put("locationsTaken", locationsTaken);
        obj.put("pointRate", pointRate);
        obj.put("lastCalculatedScore", lastCalculatedScore);
        obj.put("timeLastCalculated", timeLastCalculated);
        return obj;
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

    public ArrayList<String> getLocationsTaken() {
        return locationsTaken;
    }

    public void addLocation(String locationId) {
        locationsTaken.add(locationId);
    }

    public void removeLocation(String locationId) {
        locationsTaken.remove(locationId);
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public int getLastCalculatedScore(){ return lastCalculatedScore; }

    public long getTimeLastCalculated(){ return timeLastCalculated; }

    public int getPointRate() { return pointRate; }

    public void updatePointRate(int newRate) {
        if(lastCalculatedScore == 0){
            timeLastCalculated = System.nanoTime();
        } else {
            this.setTotalScore();
        }
        this.pointRate += newRate;
    }

    public void setTotalScore() {
        long currentTime = System.nanoTime();
        int timePassed = (int)((currentTime-timeLastCalculated)/1000000000);
        timeLastCalculated = currentTime;
        int totalScore = (timePassed * pointRate) + lastCalculatedScore;
        lastCalculatedScore = totalScore;
    }

    public int getTotalScore(){
        this.setTotalScore();
        return lastCalculatedScore;
    }
}

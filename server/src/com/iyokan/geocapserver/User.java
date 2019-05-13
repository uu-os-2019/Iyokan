package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class User {
    private final UserGuid id;
    private String name;
    private Position position;
    private QuizSession quizSession;
    private ArrayList<String> locationsTaken;
    private int expRate;
    private long lastCalculatedExp;
    private long timeLastCalculated;


    public User(UserGuid id, String name) {
        this.id = id;
        this.name = name;
        this.locationsTaken = new ArrayList<>();
        this.expRate = 0;
        this.lastCalculatedExp = 0;

    }

    public User(UserGuid id, String name, ArrayList<String> locations) {
        this.id = id;
        this.name = name;
        this.locationsTaken = locations;
        this.expRate = 0;
        this.lastCalculatedExp = 0;
    }

    public User(UserGuid id, String name, ArrayList<String> locations, int pointRate, long lastCalculatedScore, long timeLastCalculated){
        this.id = id;
        this.name = name;
        this.locationsTaken = locations;
        this.expRate = pointRate;
        this.lastCalculatedExp = lastCalculatedScore;
        this.timeLastCalculated = timeLastCalculated;
    }

    public UserGuid getID() {
        return id;
    }

    /**
     * Serializes the user, does not contain private user data
     * @return
     */
    public JSONObject getJson() {
        JSONObject obj = new JSONObject();
        obj.put("id", id.toString());
        obj.put("name", name);

        return obj;
    }

    /**
     * Includes private user data that should only be sent to the user who it is
     * @return
     */
    public JSONObject getPrivateJson() {
        JSONObject obj = getJson();
        obj.put("locations_taken", locationsTaken);

        LevelInformation information = getLevelInformation();
        obj.put("level", information.level);
        obj.put("exp", information.exp);
        obj.put("exp_rate", expRate);
        obj.put("exp_to_level", information.expToLevel);
        return obj;
    }

    public static class LevelInformation {
        public final long exp;
        public final long expToLevel;
        public final int level;

        public LevelInformation(long exp, long expToLevel, int level) {
            this.exp = exp;
            this.expToLevel = expToLevel;
            this.level = level;
        }
    }

    public LevelInformation getLevelInformation () {
        long levelCap = 100;
        long exp = getTotalExp();
        int level = 1;

        while (exp >= levelCap) {
            exp -= levelCap;
            levelCap += 100*level;
            level++;
        }

        return new LevelInformation(exp, levelCap, level);
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

    public long getLastCalculatedExp(){ return lastCalculatedExp; }

    public long getTimeLastCalculated(){ return timeLastCalculated; }

    public int getExpRate() { return expRate; }

    public void setExpRate(int pointRate) {
        this.expRate = pointRate;
    }

    public void updatePointRate(int newRate) {
        if(lastCalculatedExp == 0){
            timeLastCalculated = System.nanoTime();
        } else {
            this.updateTotalExp();
        }
        this.expRate += newRate;
    }

    public void updateTotalExp() {
        long currentTime = System.nanoTime();
        int timePassed = (int)((currentTime-timeLastCalculated)/1000000000);
        timeLastCalculated = currentTime;
        long totalScore = (timePassed * expRate) + lastCalculatedExp;
        lastCalculatedExp = totalScore;
    }

    public long getTotalExp(){
        this.updateTotalExp();
        return lastCalculatedExp;
    }
}

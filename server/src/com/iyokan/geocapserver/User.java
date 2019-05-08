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

    public User(UserGuid id, String name) {
        this.id = id;
        this.name = name;
        this.locationsTaken = new ArrayList<>();
    }

    public User(UserGuid id, String name, ArrayList<String> locations) {
        this.id = id;
        this.name = name;
        this.locationsTaken = locations;
    }

    public User(JSONObject json) {
        this.id = new UserGuid(json.getString("id"));
        this.name = json.getString("name");

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
        obj.put("locationsTaken", locationsTaken);

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
}

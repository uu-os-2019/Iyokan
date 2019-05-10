package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseUserData {
    public final UserGuid id;
    public String name;
    public ArrayList<String> locationsTaken = new ArrayList<String>();
    public int pointRate;
    public int lastCalculatedScore;
    public long timeLastCalculated;

    public DatabaseUserData(UserGuid guid, String name, ArrayList<String> locationsTaken, int pointRate, int lastCalculatedScore, long timeLastCalculated) {
        this.id = guid;
        this.name = name;
        this.locationsTaken = locationsTaken;
        this.pointRate = pointRate;
        this.lastCalculatedScore = lastCalculatedScore;
        this.timeLastCalculated = timeLastCalculated;
    }

    public DatabaseUserData(JSONObject json) {
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

    public JSONObject getJson() {
        return new JSONObject()
                .put("id", id.toString())
                .put("name", name)
                .put("locationsTaken", locationsTaken)
                .put("pointRate",pointRate)
                .put("lastCalculatedScore", lastCalculatedScore)
                .put("timeLastCalculated", timeLastCalculated);
    }
}

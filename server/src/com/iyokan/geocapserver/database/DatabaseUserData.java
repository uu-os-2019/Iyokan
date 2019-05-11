package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import com.iyokan.geocapserver.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseUserData {
    public final UserGuid id;
    public String name;
    public ArrayList<String> locationsTaken;
    public int pointRate;
    public long lastCalculatedScore;
    public long timeLastCalculated;


    public DatabaseUserData(UserGuid guid, String name, ArrayList<String> locationsTaken, int pointRate, long lastCalculatedScore, long timeLastCalculated) {
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
        this.pointRate = Utils.getJsonDefault(json, "pointRate", 0);
        this.lastCalculatedScore = Utils.getJsonDefault(json, "lastCalculatedScore", 0L);
        this.timeLastCalculated = Utils.getJsonDefault(json, "timeLastCalculated", 0L);

        this.locationsTaken = new ArrayList<>();
    }

    /**
     * Returns thet json of the object, does not contain locations taken
     * @return
     */
    public JSONObject getJson() {
        // Purposefully does not include locations taken because it creates an aliasing issue
        return new JSONObject()
                .put("id", id.toString())
                .put("name", name)
                .put("pointRate",pointRate)
                .put("lastCalculatedScore", lastCalculatedScore)
                .put("timeLastCalculated", timeLastCalculated);

    }
}

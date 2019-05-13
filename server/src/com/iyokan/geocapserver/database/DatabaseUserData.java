package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import com.iyokan.geocapserver.Utils;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseUserData {
    public final UserGuid id;
    public String name;
    public ArrayList<String> locationsTaken;
    public int expRate;
    public long lastCalculatedExp;
    public long timeLastCalculated;


    public DatabaseUserData(UserGuid guid, String name, ArrayList<String> locationsTaken, int pointRate, long lastCalculatedExp, long timeLastCalculated) {
        this.id = guid;
        this.name = name;
        this.locationsTaken = locationsTaken;
        this.expRate = pointRate;
        this.lastCalculatedExp = lastCalculatedExp;
        this.timeLastCalculated = timeLastCalculated;
    }

    public DatabaseUserData(JSONObject json) {
        this.id = new UserGuid(json.getString("id"));
        this.name = json.getString("name");
        this.expRate = Utils.getJsonDefault(json, "exp_rate", 0);
        this.lastCalculatedExp = Utils.getJsonDefault(json, "last_calculated_exp", 0L);
        this.timeLastCalculated = Utils.getJsonDefault(json, "time_last_calculated", 0L);

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
                .put("exp_rate", expRate)
                .put("last_calculated_exp", lastCalculatedExp)
                .put("time_last_calculated", timeLastCalculated);

    }
}

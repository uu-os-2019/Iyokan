package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseUserData {
    public final UserGuid id;
    public String name;
    public ArrayList<String> locationsTaken = new ArrayList<String>();

    public DatabaseUserData(UserGuid guid, String name, ArrayList<String> locationsTaken) {
        this.id = guid;
        this.name = name;
        this.locationsTaken = locationsTaken;
    }

    public DatabaseUserData(JSONObject json) {
        this.id = new UserGuid(json.getString("id"));
        this.name = json.getString("name");

        JSONArray array = json.getJSONArray("locationsTaken");
        for(int i = 0; i < array.length(); i++) {
            this.locationsTaken.add(array.get(i).toString());
        }
    }

    public JSONObject getJson() {
        return new JSONObject()
                .put("id", id.toString())
                .put("name", name)
                .put("locationsTaken", locationsTaken);
    }
}

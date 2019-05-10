package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseUserData {
    public final UserGuid id;
    public String name;
    public ArrayList<String> locationsTaken;

    public DatabaseUserData(UserGuid guid, String name, ArrayList<String> locationsTaken) {
        this.id = guid;
        this.name = name;
        this.locationsTaken = locationsTaken;
    }

    public DatabaseUserData(JSONObject json) {
        this.id = new UserGuid(json.getString("id"));
        this.name = json.getString("name");
        this.locationsTaken = new ArrayList<String>();
    }

    /**
     * Returns thet json of the object, does not contain locations taken
     * @return
     */
    public JSONObject getJson() {
        // Purposefully does not include locations taken because it creates an aliasing issue
        return new JSONObject()
                .put("id", id.toString())
                .put("name", name);
    }
}

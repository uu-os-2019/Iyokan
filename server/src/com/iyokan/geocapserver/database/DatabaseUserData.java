package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import org.json.JSONObject;

public class DatabaseUserData {
    public final UserGuid id;
    public String name;

    public DatabaseUserData(UserGuid guid, String name) {
        this.id = guid;
        this.name = name;
    }

    public DatabaseUserData(JSONObject json) {
        this.id = new UserGuid(json.getString("id"));
        this.name = json.getString("name");
    }

    public JSONObject getJson() {
        return new JSONObject()
                .put("id", id.toString())
                .put("name", name);
    }
}

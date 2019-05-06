package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import org.json.JSONObject;

public class DatabaseSessionData {
    public final UserGuid guid;
    public final String token;

    public DatabaseSessionData(UserGuid guid, String token) {
        this.guid = guid;
        this.token = token;
    }

    public DatabaseSessionData(JSONObject json) {
        this.guid = new UserGuid(json.getString("user"));
        this.token = json.getString("token");
    }

    public JSONObject getJson() {
        return new JSONObject()
                .put("user", guid.toString())
                .put("token", token);
    }
}

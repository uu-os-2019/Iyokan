package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.UserGuid;
import org.json.JSONObject;

/**
 * Contains all the necessary information we need to store in the database for a location
 */
public class DatabaseLocationData {
    public final String identifier;
    public UserGuid owner;
    public int score;

    public DatabaseLocationData(String identifier, UserGuid owner, int score) {
        this.identifier = identifier;
        this.owner = owner;
        this.score = score;
    }

    public DatabaseLocationData(JSONObject json) {
        this.identifier = json.getString("identifier");
        this.owner = json.isNull("owner") ? null : new UserGuid(json.getString("owner"));
        this.score = json.getInt("score");
    }

    public JSONObject getJson() {
        return new JSONObject()
            .put("identifier", identifier)
            .put("owner", owner == null ? JSONObject.NULL : owner.toString())
            .put("score", score);
    }
}

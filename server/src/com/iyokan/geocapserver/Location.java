package com.iyokan.geocapserver;

import com.iyokan.geocapserver.database.JsonDatabase;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Location {
    private Position position;
    public final String identifier;
    private String name;
    private String description;
    private double radius;
    private UserGuid owner;
    private int score;
    private int expValue;
    private LocationTag[] tags;

    public Location(Position position, String identifier, String name, String description, UserGuid owner, double radius, int expValue) {
        this.position = position;
        this.name = name;
        this.owner = owner;
        this.identifier = identifier;
        this.radius = radius;
        this.description = description;
        this.score = 0;
        this.expValue = expValue;
        tags = new LocationTag[0];
    }

    private LocationTag[] readTags(JSONObject obj) {
        ArrayList<LocationTag> tags = new ArrayList<>();
        Iterator<String> keys = obj.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            LocationTag tag = new LocationTag(key, obj.getInt(key));
            tags.add(tag);
        }
        return tags.toArray(new LocationTag[0]);
    }

    public Location(JSONObject object) {
        this.identifier = object.getString("identifier");
        this.name = object.getString("name");
        this.radius = object.getDouble("radius");
        this.position = new Position(object.getJSONObject("position"));
        this.description = object.getString("description");
        this.expValue = object.getInt("exp");
        this.tags = readTags(object.getJSONObject("tags"));
    }


    /**
     * Serializes the location. Does NOT include the owner
     * @return
     */
    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("description", description);
        json.put("position", position.getJson());
        json.put("radius", radius);
        json.put("exp", expValue);
        return json;
    }

    /**
     * Serializes the location and includes the owners name and guid
     * @param users
     * @return
     */
    public JSONObject getJson(UserCollection users) {
        JSONObject json = getJson();

        Object jsonOwner = JSONObject.NULL;

        if (owner != null) {
            User user = users.getUser(owner);
            if (user != null) {
                jsonOwner = user.getJson();
            }
        }

        json.put("owner", jsonOwner);

        return json;
    }

    /**
     * Updates the owner of the location and sets a new score required to take it over
     * @param owner
     * @param newScore
     */
    public void setOwner(UserGuid owner, int newScore) {
        this.owner = owner;
        this.score = newScore;
    }

    public int getScore() {
        return score;
    }

    public int getExpValue() {
        return expValue;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return identifier;
    }

    public boolean hasOwner() {
        return owner != null;
    }

    public UserGuid getOwner() {
        return owner;
    }

    public LocationTag[] getTags() {
        return tags;
    }
}

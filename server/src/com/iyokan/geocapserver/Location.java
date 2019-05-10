package com.iyokan.geocapserver;

import org.json.JSONObject;

public class Location {
    private Position position;
    public final String identifier;
    private String name;
    private String description;
    private double radius;
    private UserGuid owner;
    private int score;

    public Location(Position position, String identifier, String name, String description, UserGuid owner, double radius) {
        this.position = position;
        this.name = name;
        this.owner = owner;
        this.identifier = identifier;
        this.radius = radius;
        this.description = description;
        this.score = 0;
    }

    public Location(JSONObject object) {
        this.identifier = object.getString("identifier");
        this.name = object.getString("name");
        this.radius = object.getDouble("radius");
        this.position = new Position(object.getJSONObject("position"));
        this.description = object.getString("description");
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
}

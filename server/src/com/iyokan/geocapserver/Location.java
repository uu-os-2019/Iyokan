package com.iyokan.geocapserver;

import org.json.JSONObject;

public class Location {
    private Position position;
    public final String identifier;
    private String name;
    private String description;
    private UserGuid owner;
    private double radius;
    private LocationType type;
    private int score;

    public Location(
                    Position position,
                    String identifier,
                    String name,
                    String description,
                    UserGuid owner,
                    double radius,
                    LocationType type,
                    int score) {
        this.position = position;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.radius = radius;
        this.type = type;
        this.score = score;
    }

    public Location(JSONObject object) {
        this.position = new Position(object.getJSONObject("position"));
        this.identifier = object.getString("identifier");
        this.name = object.getString("identifier");
        this.description = object.getString("description");
        if (object.isNull("owner")) {
            this.owner = null;
        } else {
            this.owner = new UserGuid(object.getString("owner"));
        }
        this.radius = object.getDouble("radius");
        this.type = LocationType.fromString( object.getString("type") );
        this.score = object.getInt("score");
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("position", position.getJson());
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("description", description);
        json.put("owner", owner == null ? JSONObject.NULL : owner.toString());
        json.put("radius", radius);
        json.put("type", type.getName());
        json.put("score", score);
        return json;
    }

    public void setScore(int score) {
        this.score = score;
    }

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

    public boolean hasOwner() {
        return owner != null;
    }

    public UserGuid getOwner() {
        return owner;
    }
}

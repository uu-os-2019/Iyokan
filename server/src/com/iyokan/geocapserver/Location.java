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
        this.name = object.getString("identifier");
        this.radius = object.getDouble("radius");
        this.position = new Position(object.getJSONObject("position"));
        this.description = object.getString("description");
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("description", description);
        json.put("owner", owner == null ? JSONObject.NULL : owner.toString());
        json.put("position", position.getJson());
        json.put("radius", radius);
        return json;
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

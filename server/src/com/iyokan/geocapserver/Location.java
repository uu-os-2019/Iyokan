package com.iyokan.geocapserver;

import org.json.JSONObject;

public class Location {
    private Position position;
    public final String identifier;
    private String name;
    private UserGuid owner;

    public Location(Position position, String identifier, String name, UserGuid owner) {
        this.position = position;
        this.name = name;
        this.owner = owner;
        this.identifier = identifier;
    }

    public Location(JSONObject object) {
        this.identifier = object.getString("identifier");
        this.name = object.getString("identifier");
        if (object.isNull("owner")) {
            this.owner = null;
        } else {
            this.owner = new UserGuid(object.getString("owner"));
        }
        this.position = new Position(object.getJSONObject("position"));
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("owner", owner == null ? null : owner.toString());
        json.put("position", position.getJson());
        return json;
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

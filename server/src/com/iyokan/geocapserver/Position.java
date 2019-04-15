package com.iyokan.geocapserver;

import org.json.JSONObject;

public class Position {
    public double lng;
    public double lat;

    public Position(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public Position(JSONObject obj) {
        lng = obj.getDouble("lng");
        lat = obj.getDouble("lat");
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        json.put("lng", lng);
        json.put("lat", lat);

        return json;
    }
}

package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LocationCollection {
    private HashMap<String, Location> locations;

    public LocationCollection() {
        locations = new HashMap<>();
    }

    public void loadLocations(JSONArray array) {
        for (int i=0; i < array.length(); i++) {
            Location location = new Location(array.getJSONObject(i));
            locations.put(location.identifier, location);
        }
    }

    public Location[] getAllLocations() {
        return locations.values().toArray(new Location[0]);
    }

    public Location getLocation(String identifier) {
        return locations.get(identifier);
    }

    public void addLocation(Location location) {
        locations.put(location.identifier, location);
    }
}

package com.iyokan.geocapserver;

import java.util.Dictionary;
import java.util.HashMap;

public class LocationType {
    private static HashMap<String, LocationType> values = new HashMap<>();

    private String name;

    public static final LocationType Church = new LocationType("church");
    public static final LocationType VolleyballField = new LocationType("volleyballfield");
    public static final LocationType Area = new LocationType("area");

    private LocationType(String name) {
        this.name = name;

        values.put(name.toLowerCase(), this);
    }

    public static LocationType fromString(String string) {
        string = string.toLowerCase();
        if (values.containsKey(string)) {
            return values.get(string);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
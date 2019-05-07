package com.iyokan.geocapserver;

import com.iyokan.geocapserver.database.Database;
import com.iyokan.geocapserver.database.DatabaseLocationData;
import org.json.JSONArray;

import java.util.HashMap;

public class LocationCollection {
    private Database database;
    private HashMap<String, Location> locations;

    public LocationCollection(Database database) {
        this.database = database;
        locations = new HashMap<>();
    }

    public void loadLocations(JSONArray array) {
        for (int i=0; i < array.length(); i++) {
            Location location = new Location(array.getJSONObject(i));
            locations.put(location.identifier, location);
        }

        // Get the necessary data from the database
        DatabaseLocationData[] locationsData = database.getLocations();

        for (DatabaseLocationData locationData : locationsData) {
            // Get the saved location
            Location location = locations.get(locationData.identifier);
            if (location != null) {
                location.setOwner(locationData.owner, locationData.score);
            }
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

    public void updateLocation(Location location) {
        database.updateLocation(location);
    }
}

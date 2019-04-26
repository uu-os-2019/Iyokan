package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Fetches data from a database, or so far, just from JSON files
 */
public class Database {
    private final String path = "database.json";

    ArrayList<User> users;
    ArrayList<Location> locations;

    public Database() {
        // Loads all data
        if (FileReader.fileExists(path) == false) {
            users = new ArrayList<>();
            locations = new ArrayList<>();
            save();
        } else {
            JSONObject root = FileReader.readJsonObjectFromFile(path);

            JSONArray jsonLocations = root.getJSONArray("locations");
            JSONArray jsonUsers = root.getJSONArray("users");
        }
    }

    public User[] getUsers() {
        return users.toArray(new User[0]);
    }

    public Location[] getLocations() {
        return locations.toArray(new Location[0]);
    }

    public void putUser(User user) {
        users.add(user);
    }

    /**
     * Update the existing user inside the database
     * @param user
     */
    public void updateUser(User user) {

    }

    /**
     * Inserts the given location into the database
     * @param location
     */
    public void insertLocation(Location location) {
        locations.add(location);
    }

    private void save() {
        JSONObject root = new JSONObject();

        JSONArray jsonUsers = new JSONArray();
        users.forEach(x -> jsonUsers.put(x.getJson()));

        JSONArray jsonLocations = new JSONArray();
        locations.forEach(x -> jsonLocations.put(x.getJson()));



        root.put("users", jsonUsers);
        root.put("locations", jsonLocations);

        FileReader.writeJsonToFile(path, root);
    }

}

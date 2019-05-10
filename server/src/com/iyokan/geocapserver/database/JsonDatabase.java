package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Fetches data from a database, or so far, just from JSON files
 */
public class JsonDatabase implements Database {
    private final String path;

    HashMap<UserGuid, DatabaseUserData> users;
    HashMap<String, DatabaseLocationData> locations;
    HashMap<String, DatabaseSessionData> sessions;

    public JsonDatabase(String path) {
        users = new HashMap<>();
        locations = new HashMap<>();
        sessions = new HashMap<>();

        this.path = path;

        // Loads all data
        if (FileReader.fileExists(path) == false) {
            save();
        } else {
            JSONObject root = FileReader.readJsonObjectFromFile(path);

            JSONArray jsonLocations = root.getJSONArray("locations");

            for (int i=0; i < jsonLocations.length(); i++) {
                JSONObject jsonLocation = jsonLocations.getJSONObject(i);
                DatabaseLocationData location = new DatabaseLocationData(jsonLocation);
                locations.put(location.identifier, location);
            }

            JSONArray jsonUsers = root.getJSONArray("users");

            for (int i=0; i < jsonUsers.length(); i++) {
                JSONObject jsonUser = jsonUsers.getJSONObject(i);
                DatabaseUserData user = new DatabaseUserData(jsonUser);
                users.put(user.id, user);
            }

            // Loop through all locations and put the owners in the users
            for (DatabaseLocationData location : locations.values()) {
                if (users.containsKey(location.owner)) {
                    users.get(location.owner).locationsTaken.add(location.identifier);
                }
            }

            JSONArray jsonSessions = root.getJSONArray("sessions");

            for (int i=0; i < jsonSessions.length(); i++) {
                JSONObject jsonSession = jsonSessions.getJSONObject(i);
                DatabaseSessionData session = new DatabaseSessionData(jsonSession);
                sessions.put(session.token, session);
            }
        }
    }

    /**
     * Removes all locations from the database that are not in the provided LocationCollection
     * @param collection
     */
    public void filterLocations(LocationCollection collection) {
        // Removes stored locations that are not in the database

        // A list of what to remove, because we can not modify a collection while we iterate over it
        LinkedList<String> toRemove = new LinkedList<>();

        for (DatabaseLocationData location : locations.values()) {
            // Remove it for the user
            if (collection.isLocation(location.identifier) == false) {
                if (users.containsKey(location.owner)) {
                    users.get(location.owner).locationsTaken.remove(location.identifier);
                }
            }

            // Put it in the list for removal later
            toRemove.add(location.identifier);
        }

        // Remove all the locations
        toRemove.forEach(x -> locations.remove(x));
        save();
    }
    public DatabaseUserData getUser(UserGuid guid) { return users.get(guid);}

    public DatabaseUserData[] getUsers() {
        return users.values().toArray(new DatabaseUserData[0]);
    }

    public DatabaseSessionData[] getSessions() { return sessions.values().toArray(new DatabaseSessionData[0]);}

    public DatabaseLocationData getLocation(String identifier) {return locations.get(identifier);}

    public DatabaseLocationData[] getLocations() {
        return locations.values().toArray(new DatabaseLocationData[0]);
    }



    public void insertUser(User user) {
        users.put(user.getID(), new DatabaseUserData(user.getID(), user.getName(), user.getLocationsTaken()));
        save();
    }

    /**
     * Update the existing user inside the database
     * @param user
     */
    public void updateUser(User user) {
        // Finds the old user and updates its data
        DatabaseUserData savedUser = users.get(user.getID());
        savedUser.locationsTaken = user.getLocationsTaken();
        save();
    }

    /**
     * Updates the given location into the database
     * @param location
     */
    public void updateLocation(Location location) {
        DatabaseLocationData savedLocation = locations.get(location.identifier);

        // If the location did not have a save record before, insert it
        if (savedLocation == null) {
            savedLocation = new DatabaseLocationData(location.identifier, location.getOwner(), location.getScore());
            locations.put(location.identifier, savedLocation);
        }

        savedLocation.owner = location.getOwner();
        savedLocation.score = location.getScore();

        save();
    }

    public void insertSession(String token, UserGuid user) {
        sessions.put(token, new DatabaseSessionData(user, token));
        save();
    }

    private void save() {
        JSONObject root = new JSONObject();

        JSONArray jsonUsers = new JSONArray();
        users.values().forEach(x -> jsonUsers.put(x.getJson()));

        JSONArray jsonLocations = new JSONArray();
        locations.values().forEach(x -> jsonLocations.put(x.getJson()));

        JSONArray jsonSessions = new JSONArray();
        sessions.values().forEach(x -> jsonSessions.put(x.getJson()));

        root.put("users", jsonUsers);
        root.put("locations", jsonLocations);
        root.put("sessions", jsonSessions);

        FileReader.writeJsonToFile(path, root);
    }

}

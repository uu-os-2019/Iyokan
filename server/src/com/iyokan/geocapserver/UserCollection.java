package com.iyokan.geocapserver;

import com.iyokan.geocapserver.database.Database;
import com.iyokan.geocapserver.database.JsonDatabase;
import com.iyokan.geocapserver.database.DatabaseUserData;

import java.util.HashMap;

public class UserCollection {
    private Database database;
    private HashMap<UserGuid, User> users;


    public UserCollection(Database database) {
        users = new HashMap<>();
        this.database = database;

        // Add initial users from database
        for (DatabaseUserData user : database.getUsers()) {
            users.put(user.id, new User(user.id, user.name, user.locationsTaken));
        }
    }

    public void addUser(User user) {
        users.put(user.getID(), user);

        if (database != null) {
            database.insertUser(user);
        }
    }

    public User getUser(UserGuid id) {
        return users.get(id);
    }

    public boolean hasUser(UserGuid id) {
        return users.containsKey(id);
    }

    public void updateUser(User user) {
        // Doesn't actually do anything to our internal user, simply signals the database it's time to update
        if (database != null) {
            database.updateUser(user);
        }
    }

    /**
     * Sees if the collection contains a user with the given name. This is slow
     * @param username
     * @return true if user exists
     */
    public boolean hasUser(String username) {
        username = username.toLowerCase();
        for (User user : users.values()) {
            if (user.getName().toLowerCase().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public void removeUser(UserGuid id) {
        users.remove(id);
    }

}

package com.iyokan.geocapserver;

import com.iyokan.geocapserver.database.Database;
import com.iyokan.geocapserver.database.DatabaseSessionData;

import java.util.HashMap;

public class SessionVault {
    private HashMap<String, User> users;
    private Database database;

    public SessionVault(Database database, UserCollection collection) {
        users = new HashMap<>();
        this.database = database;

        if (database != null) {
            // Add initial users from database
            for (DatabaseSessionData session : database.getSessions()) {
                users.put(session.token, collection.getUser(session.guid));
            }
        }
    }

    public void insert(String token, User user) {
        users.put(token, user);

        // Update the database
        if (database != null) {
            database.insertSession(token, user.getID());
        }
    }

    public User getUser(String token) {
        if (users.containsKey(token)) {
            return users.get(token);
        }
        return null;
    }
}

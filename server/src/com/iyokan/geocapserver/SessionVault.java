package com.iyokan.geocapserver;

import java.util.HashMap;

public class SessionVault {
    HashMap<String, User> users;

    public SessionVault() {
        users = new HashMap<>();
    }

    public void insert(String token, User user) {
        users.put(token, user);
    }

    public User getUser(String token) {
        if (users.containsKey(token)) {
            return users.get(token);
        }
        return null;
    }
}

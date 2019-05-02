package com.iyokan.geocapserver;

import java.util.HashMap;

public class UserCollection {
    private HashMap<UserGuid, User> users;


    public UserCollection() {
        users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getID(), user);
    }

    public User getUser(UserGuid id) {
        return users.get(id);
    }

    public boolean hasUser(UserGuid id) {
        return users.containsKey(id);
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

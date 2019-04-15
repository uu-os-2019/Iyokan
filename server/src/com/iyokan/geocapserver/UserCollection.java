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

    public void removeUser(UserGuid id) {
        users.remove(id);
    }

}

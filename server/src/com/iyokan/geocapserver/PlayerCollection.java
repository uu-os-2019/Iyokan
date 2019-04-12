package com.iyokan.geocapserver;

import java.util.HashMap;

public class PlayerCollection {
    private HashMap<UserGuid, User> users;


    public PlayerCollection() {
        users = new HashMap<>();
    }

    public void addPlayer(User user) {
        users.put(user.getID(), user);
    }

    public User getPlayer(UserGuid id) {
        return users.get(id);
    }

    public boolean hasPlayer(UserGuid id) {
        return users.containsKey(id);
    }

}

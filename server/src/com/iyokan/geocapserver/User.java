package com.iyokan.geocapserver;

public class User {
    private final UserGuid id;
    private String name;

    public User(UserGuid id, String name) {
        this.id = id;
        this.name = name;
    }


    public UserGuid getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}

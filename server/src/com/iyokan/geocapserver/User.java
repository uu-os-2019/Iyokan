package com.iyokan.geocapserver;

public class User {
    private final UserGuid id;
    private String name;
    private Position position;

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

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}

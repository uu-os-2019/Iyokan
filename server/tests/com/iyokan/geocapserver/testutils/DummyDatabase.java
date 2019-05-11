package com.iyokan.geocapserver.testutils;

import com.iyokan.geocapserver.Location;
import com.iyokan.geocapserver.User;
import com.iyokan.geocapserver.UserGuid;
import com.iyokan.geocapserver.database.Database;
import com.iyokan.geocapserver.database.DatabaseLocationData;
import com.iyokan.geocapserver.database.DatabaseSessionData;
import com.iyokan.geocapserver.database.DatabaseUserData;

public class DummyDatabase implements Database {

    @Override
    public void insertSession(String token, UserGuid user) {

    }

    @Override
    public DatabaseUserData getUser(UserGuid guid) {
        return null;
    }

    @Override
    public DatabaseUserData[] getUsers() {
        return new DatabaseUserData[0];
    }

    @Override
    public DatabaseSessionData[] getSessions() {
        return new DatabaseSessionData[0];
    }

    @Override
    public DatabaseLocationData getLocation(String identifier) {
        return null;
    }

    @Override
    public DatabaseLocationData[] getLocations() {
        return new DatabaseLocationData[0];
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void insertUser(User user) {

    }

    @Override
    public void updateLocation(Location location) {

    }
}

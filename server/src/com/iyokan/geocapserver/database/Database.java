package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.Location;
import com.iyokan.geocapserver.User;
import com.iyokan.geocapserver.UserGuid;

public interface Database {

    /**
     * Stores a new session in the database
     * @param token
     * @param user
     */
    void insertSession(String token, UserGuid user);

    /**
     * Gets an array of all users currently in the database
     * @return
     */
    DatabaseUserData[] getUsers();

    /**
     * Gets an array of all sessions currently in the database
     * @return
     */
    DatabaseSessionData[] getSessions();

    /**
     * Gets an array of all locations currently stored in the database
     * @return
     */
    DatabaseLocationData[] getLocations();

    /**
     * Updates an already existing user in the database
     * @param user
     */
    void updateUser(User user);

    /**
     * Inserts a new user into the database
     * @param user
     */
    void insertUser(User user);

    /**
     * Updates a location in the database. If the location does not already exist, it is created
     * @param location
     */
    void updateLocation(Location location);

}

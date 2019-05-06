package com.iyokan.geocapserver;

import com.iyokan.geocapserver.testutils.DummyDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCollectionTest {

    @Test
    void addUser() {
        UserCollection collection = new UserCollection(new DummyDatabase());

        UserGuid rndGuid = Utils.generateUserGuid();
        collection.addUser(new User(rndGuid, "Kniv-karlsson"));

        assertTrue(collection.hasUser(rndGuid));
        assertTrue(collection.hasUser("Kniv-karlsson"));
    }

    @Test
    void getUser() {
        UserCollection collection = new UserCollection(new DummyDatabase());
        UserGuid rndGuid = Utils.generateUserGuid();
        User user1 = new User(rndGuid, "Kniv-karlsson");

        collection.addUser(user1);
        User user2 = collection.getUser(rndGuid);

        assertTrue(user1.getName().equals(user2.getName()));
    }

    @Test
    void removeUser() {
        UserCollection collection = new UserCollection(new DummyDatabase());

        UserGuid rndGuid1 = Utils.generateUserGuid();
        UserGuid rndGuid2 = Utils.generateUserGuid();
        collection.addUser(new User(rndGuid1, "Pistol-pelle"));
        collection.addUser(new User(rndGuid2, "Galne-gustaf"));

        assertTrue(collection.hasUser(rndGuid1));
        assertTrue(collection.hasUser("Pistol-pelle"));
        assertTrue(collection.hasUser(rndGuid2));
        assertTrue(collection.hasUser("Galne-gustaf"));

        collection.removeUser(rndGuid1);

        assertFalse(collection.hasUser(rndGuid1));
        assertFalse(collection.hasUser("Pistol-pelle"));
        assertTrue(collection.hasUser(rndGuid2));
        assertTrue(collection.hasUser("Galne-gustaf"));
     
        // Make sure deleting again doesn't crashes
        collection.removeUser(rndGuid1);
    }
}
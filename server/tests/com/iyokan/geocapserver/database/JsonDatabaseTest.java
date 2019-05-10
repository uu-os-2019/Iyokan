package com.iyokan.geocapserver.database;

import com.iyokan.geocapserver.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonDatabaseTest {
    @Test
    void testUser() {
        // Clear the old file if it exists
        File file = new File("testdatabase.json");
        file.delete();

        JsonDatabase db = new JsonDatabase("testdatabase.json");

        UserGuid uid = Utils.generateUserGuid();
        User user = new User(uid, "revolver-rickard");

        db.insertUser(user);

        // See that it was added
        DatabaseUserData dbud = db.getUser(uid);

        assertEquals(dbud.id, user.getID());

        // Create a new database on the old file
        JsonDatabase db2 = new JsonDatabase("testdatabase.json");

        dbud = db2.getUser(uid);

        // And see that it imported correctly
        assertEquals(dbud.id, user.getID());
        assertEquals(dbud.name, user.getName());
    }

    @Test
    void testLocation() {
        // Clear the old file if it exists
        File file = new File("testdatabase.json");
        file.delete();

        JsonDatabase db = new JsonDatabase("testdatabase.json");

        UserGuid uid = Utils.generateUserGuid();
        User user = new User(uid, "kent med kniven");

        Location location = new Location(new Position(10, 10), "bråkgatan", "bråkmakargatan",
                "testy", null, 50);

        location.setOwner(user.getID(), 100);

        db.insertUser(user);
        db.updateLocation(location);

        DatabaseLocationData dbld = db.getLocation("bråkgatan");

        assertEquals(dbld.owner, location.getOwner());
        assertEquals(dbld.score, location.getScore());


        // Create a new database on the old file
        JsonDatabase db2 = new JsonDatabase("testdatabase.json");

        dbld = db2.getLocation("bråkgatan");

        // And see that it imported correctly
        assertEquals(dbld.owner, location.getOwner());
        assertEquals(dbld.score, location.getScore());
    }


}
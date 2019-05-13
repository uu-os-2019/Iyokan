package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.SessionVault;
import com.iyokan.geocapserver.User;
import com.iyokan.geocapserver.UserCollection;
import com.iyokan.geocapserver.UserGuid;
import com.iyokan.geocapserver.database.Database;
import com.iyokan.geocapserver.testutils.DummyDatabase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RouteRegisterTest {

    @Test
    void handle() {
        Database db = new DummyDatabase();
        UserCollection collection = new UserCollection(db);
        SessionVault vault = new SessionVault(db, collection);

        Route route = new RouteRegister(collection, vault);

        JSONObject body = new JSONObject();

        body.put("username", "blod-ingvar");

        RequestData data = new RequestData(body, null);

        JSONObject response = route.handle(data);

        String token = response.getString("token");
        assertTrue(response.getBoolean("success"));
        assertTrue(token != null);
        assertTrue(token.length() == 32);

        JSONObject userJson = response.getJSONObject("user");

        ArrayList<String> locationsTaken = new ArrayList<>();
        JSONArray array = userJson.getJSONArray("locations_taken");
        for(int i = 0; i < array.length(); i++) {
            locationsTaken.add(array.get(i).toString());
        }

        User user = new User(new UserGuid(userJson.getString("id")), userJson.getString("name"), locationsTaken);

        // See that the user exists in the vault
        assertNotNull(vault.getUser(token));
        assertTrue(collection.hasUser(user.getID()));

        // Try again, should fail because user already exists
        response = route.handle(data);
        assertFalse(response.getBoolean("success"));

    }
}
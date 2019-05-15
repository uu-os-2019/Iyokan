package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.*;
import com.iyokan.geocapserver.database.Database;
import com.iyokan.geocapserver.testutils.DummyDatabase;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteMyProfileTest {

    @Test
    void handle() {


        Database db = new DummyDatabase();

        UserCollection users = new UserCollection(db);
        SessionVault sessions = new SessionVault(db, users);

        LocationCollection locations = new LocationCollection(db);
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        QuizRoundCollection quizRounds = new QuizRoundCollection();
        quizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));

        var testa = new User(Utils.generateUserGuid(), "Testa fiesta");
        sessions.insert("OsthyvelOsthyvelOsthyvelOsthyvel", testa);
        users.addUser(testa);

        Route route_myprofile = new RouteMyProfile(locations, users);
        JSONObject postJson = new JSONObject();
        RequestData data = new RequestData(postJson,  testa);
        JSONObject response = route_myprofile.handle(data);
        JSONObject user = response.getJSONObject("user");

        // my profile without locations taken
        assertEquals(user.getInt("exp_rate"), 0);
        assertTrue(user.getJSONArray("locations_taken").isEmpty());


        //route quiz start & answer to takeover
        Route route_quizstart = new RouteQuizStart(quizRounds, locations);
        Route route_quizanswer = new RouteQuizAnswer(quizRounds, locations, users);
        postJson.put("location", "domkyrkan");
        data = new RequestData(postJson,  testa);
        response = route_quizstart.handle(data);

        for(int i = 0; i < 3; i++) {
            JSONObject answer = new JSONObject();
            answer.put("answer", "anything");
            data = new RequestData(answer, testa);
            response = route_quizanswer.handle(data);
        }


        // currentScore 1 and taken over uppsala domkyrka
        response = route_myprofile.handle(data);
        user = response.getJSONObject("user");
        assertEquals(user.getInt("exp_rate"), 4);
        assertEquals(response.getJSONArray("locations").get(0), "Uppsala Domkyrka");

    }
}
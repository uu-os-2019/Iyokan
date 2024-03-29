package com.iyokan.geocapserver;

import com.iyokan.geocapserver.database.JsonDatabase;
import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;
        JsonDatabase database = new JsonDatabase("database.json");

        LocationCollection locations = new LocationCollection(database);
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        database.filterLocations(locations);

        UserCollection users = new UserCollection(database);
        users.updateExps(locations);

        SessionVault sessions = new SessionVault(database, users);
        QuizRoundCollection quizRounds = new QuizRoundCollection();
        quizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));


        final Route[] routes = new Route[]{
                new RouteTest(),
                new RouteLocationGetAll(locations, users),
                new RouteRegister(users, sessions),
                new RouteQuizStart(quizRounds, locations),
                new RouteQuizAnswer(quizRounds, locations, users),
                new RouteMyProfile(locations, users),
                new RouteLeaderboardCurrent(users),
                new RouteLeaderboardTotal(users)
        };

        Server server = new Server(port, routes, sessions);

        server.start();

    }
}

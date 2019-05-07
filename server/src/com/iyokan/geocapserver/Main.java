package com.iyokan.geocapserver;

import com.iyokan.geocapserver.database.JsonDatabase;
import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;
        Highscore hs = new Highscore();

        JsonDatabase database = new JsonDatabase("database.json");

        LocationCollection locations = new LocationCollection(database);
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        UserCollection users = new UserCollection(database);

        SessionVault sessions = new SessionVault(database, users);
        QuizRoundCollection quizRounds = new QuizRoundCollection();
        quizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));


        final Route[] routes = new Route[]{
                new RouteTest(),
                new RouteHighscore(hs, users),
                new RouteLocationGetAll(locations, users),
                new RouteRegister(users, sessions),
                new RouteQuizStart(quizRounds, locations),
                new RouteQuizAnswer(quizRounds, locations, hs, users),
                new RouteMyProfile(hs, locations)
        };

        Server server = new Server(port, routes, sessions);

        server.start();

    }
}

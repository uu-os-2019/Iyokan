package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;
        Highscore hs = new Highscore();

        LocationCollection locations = new LocationCollection();
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        UserCollection users = new UserCollection();

        SessionVault sessions = new SessionVault();
        QuizRoundCollection quizRounds = new QuizRoundCollection();
        quizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));

        // Put a temporary token in the vault
        sessions.insert("OsthyvelOsthyvelOsthyvelOsthyvel", new User(Utils.generateUserGuid(), "Testa fiesta"));


        final Route[] routes = new Route[]{
                new RouteTest(),
                new RouteHighscore(hs, users),
                new RouteLocationGetAll(locations),
                new RouteRegister(users, sessions),
                new RouteQuizStart(quizRounds, locations),
                new RouteQuizAnswer(quizRounds, hs)
        };

        Server server = new Server(port, routes, sessions);

        server.start();

    }
}

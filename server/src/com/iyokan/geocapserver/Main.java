package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;
        Highscore hs = new Highscore();

        LocationCollection locations = new LocationCollection();
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        QuizRoundCollection QuizRounds = new QuizRoundCollection();
        QuizRounds.loadQuizRounds(FileReader.readJsonArrayFromFile("resources/quizRounds.json"));


        /*
        QuizRound QR = QuizRounds.getQuizRound(0);
        int svarInd = 0;

        System.out.println(QR.getQuestion());
        System.out.println(QR.getAlternatives());
        System.out.println(QR.CheckAnswer(QR.getAlternatives().get(svarInd)));
         */

        final Route[] routes = new Route[] {
                new RouteTest(),
                new RouteHighscore(hs),
                new RouteLocationGetAll(locations)
        };


        Server server = new Server(port, routes);

        server.start();

    }
}

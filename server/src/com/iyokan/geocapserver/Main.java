package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;
        Highscore hs = new Highscore();

        final Route[] routes = new Route[] {
                new RouteTest(),
                new RouteHighscore(hs)
        };


        Server server = new Server(port, routes);

        server.start();
        System.out.println(hs.getHighscore(3));

    }
}

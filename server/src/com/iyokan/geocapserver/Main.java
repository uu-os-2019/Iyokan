package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;

        final Route[] routes = new Route[] {
                new RouteTest()
        };

        Server server = new Server(port, routes);

        server.start();
    }
}

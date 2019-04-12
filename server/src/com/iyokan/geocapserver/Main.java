package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.*;

public class Main {

    public static void main(String[] args) {
        final int port = 80;

        LocationCollection locations = new LocationCollection();
        locations.loadLocations(FileReader.readJsonArrayFromFile("resources/locations.json"));

        final Route[] routes = new Route[] {
                new RouteTest(),
                new RouteLocationGetAll(locations)
        };

        Server server = new Server(port, routes);

        server.start();
    }
}

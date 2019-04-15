package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.RequestData;
import com.iyokan.geocapserver.route.Route;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ContextHandler implements HttpHandler {
    Route route;

    public ContextHandler(Route route) {
        this.route = route;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = route.handle(new RequestData(t)).toString();
        t.getResponseHeaders().add("Content-Type", "application/json");
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

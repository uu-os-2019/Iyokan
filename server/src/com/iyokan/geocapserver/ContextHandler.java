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
        String response = "";
        int code = 200;
        try {
            response = route.handle(new RequestData(t)).toString();
        } catch(Exception ex) {
            System.out.println("Error inside route: " + route);
            System.out.println(ex);
            response = "Internal server error";
            code = 500;
        }
        byte[] b = response.getBytes();
        t.getResponseHeaders().add("Content-Type", "application/json");
        t.sendResponseHeaders(code, b.length);
        OutputStream os = t.getResponseBody();
        os.write(b);
        os.close();
    }
}

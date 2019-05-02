package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.RequestData;
import com.iyokan.geocapserver.route.Route;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ContextHandler implements HttpHandler {
    private Route route;
    private SessionVault vault;

    public ContextHandler(Route route, SessionVault vault) {
        this.route = route;
        this.vault = vault;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        String response = "";
        User user = null;

        Headers headers = t.getRequestHeaders();
        if (headers.containsKey("Authorization")) {
            List<String> list = t.getRequestHeaders().get("Authorization");
            String token = list.get(0);
            user = vault.getUser(token);
        }

        RequestData data = new RequestData(t, user);

        int code = 200;
        try {
            response = route.handle(data).toString();
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

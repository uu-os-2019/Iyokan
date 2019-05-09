package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.RequestData;
import com.iyokan.geocapserver.route.Route;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * The ContextHandler moves a http context/endpoint to a specific route
 */
public class ContextHandler implements HttpHandler {
    private Route route;
    private SessionVault vault;

    /**
     * Constructor for ContextHandler
     * @param route The route where the context is moved
     * @param vault SessionVault where users are verified
     */
    public ContextHandler(Route route, SessionVault vault) {
        this.route = route;
        this.vault = vault;
    }

    /**
     * Handles a HttpExchange
     * @param t exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange t) throws IOException {
        String response, responseType;
        User user = null;

        Headers headers = t.getRequestHeaders();
        if (headers.containsKey("Authorization")) {
            List<String> list = t.getRequestHeaders().get("Authorization");
            String token = list.get(0);
            user = vault.getUser(token);
        }

        RequestData data = new RequestData(t, user);

        int code;

        try {
            response = route.handle(data).toString();
            responseType = "application/json";
            code = 200;
        } catch(Exception ex) {
            System.out.println("Error inside route: " + route + "\n" + ex);
            response = "Internal server error";
            responseType = "text";
            code = 500;
        }

        byte[] b = response.getBytes();
        t.getResponseHeaders().add("Content-Type", responseType);
        t.sendResponseHeaders(code, b.length);
        OutputStream os = t.getResponseBody();
        os.write(b);
        os.close();
    }
}

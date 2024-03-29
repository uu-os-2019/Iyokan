package com.iyokan.geocapserver;

import com.iyokan.geocapserver.route.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;

    public Server(int port, Route[] routes, SessionVault vault) {

        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException ex) {
            throw new RuntimeException(ex.toString());
        }

        // Create a ContextHandler that will call upon the route when processed
        for (Route r : routes) {
            HttpContext context = server.createContext(r.getUrl());
            context.setHandler(new ContextHandler(r, vault));
        }
    }

    public void start() {
        System.out.println("Started server.");
        server.start();
    }
}

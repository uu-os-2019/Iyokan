package com.iyokan.geocapclient;

public class Main {
    public static void main(String[] args) {

        String url = "localhost";

        Client client = new Client(url);
        Tui tui = new Tui(client);

        tui.start();
    }
}


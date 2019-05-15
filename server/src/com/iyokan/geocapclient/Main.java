package com.iyokan.geocapclient;

public class Main {
    public static void main(String[] args) {

        String url = "13.53.140.24";

        Client client = new Client(url);
        Tui tui = new Tui(client);

        tui.start();
    }
}


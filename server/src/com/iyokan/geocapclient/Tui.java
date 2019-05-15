package com.iyokan.geocapclient;

import com.iyokan.geocapserver.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class Tui {

    BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    Client client;

    public Tui(Client client) {
        this.client = client;
    }

    public void start() {
        mainLoop();
    }

    private void mainLoop() {
        boolean exit = false;
        while (exit == false) {
            printLine("Enter command:");
            switch (readLine()) {
                case "set token":
                    printLine("Enter token to use token:");
                    client.token = readLine();
                    printLine("Token was updated");
                    break;

                case "register":
                    register();
                    break;

                case "quiz":
                    quiz();
                    break;

                case "exit":
                    exit = true;
                    break;
            }
        }
    }

    private void quiz() {
        printLine("Enter a location:");
        String location = readLine();

        JSONObject response = client.makeRequest("quiz/start", new JSONObject().put("location", location));

        printLine("Response was" + response.toString());

        if (response.getBoolean("success")) {
            for (int i=0;i<3;i++) {
                String question;
                JSONArray jAlternatives;

                if (i == 0) {
                    question = response.getString("question");
                    jAlternatives = response.getJSONArray("alternatives");

                } else {
                    question = response.getString("new_question");
                    jAlternatives = response.getJSONArray("new_alternatives");
                }

                printLine("\n\nQuestion: "+ question);
                String[] alternatives = new String[4];
                for (int j = 0; j < 4; j++) {
                    alternatives[j] = jAlternatives.getString(j);
                    printLine(j + ")  " +  alternatives[j]);
                }


                String answer = alternatives[Integer.parseInt(readLine())];
                response = client.makeRequest("quiz/answer", new JSONObject().put("answer", answer));

                printLine(response.getBoolean("correct") ? "Correct answer" : "Wrong answer");

                printLine("Response was: " + response.toString());
            }
        }

    }

    private void register() {
        printLine("Enter your username");
        String username = readLine();
        JSONObject response = client.makeRequest("register", new JSONObject().put("username", username));

        printLine("Response was " + response.toString());

        if (response.has("token")) {
            client.token = response.getString("token");
            printLine("Token was set to " + client.token);
        }
    }

    private void printLine(String line) {
        System.out.println(line);
    }

    private String readLine() {
        String line = "";
        try {
            line=buffer.readLine();
        } catch (Exception ex) {}

        return line;
    }

}

package com.iyokan.geocapclient;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Client {
    String serverUrl = "";
    String token = "";

    public Client(String url) {
        this.serverUrl = url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JSONObject makeRequest(String endpoint, JSONObject data) {
        try {
            URL url = new URL("http://" + serverUrl + "/" + endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", token);

            // Send data
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(data.toString());
            wr.flush();
            wr.close();

            // Get data

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            return new JSONObject(tokener);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void makeRequest(String endpoint) {
        makeRequest(endpoint, new JSONObject());
    }



}

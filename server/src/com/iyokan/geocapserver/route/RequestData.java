package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.User;
import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

/**
 * This class contains data relevant to a specific route, such as parameters, the attached user etc
 */
public class RequestData {
    private JSONObject json;

    private User user;

    private String getRequest;

    public RequestData(HttpExchange exchangeData, User user) {

        try {
            JSONTokener tokener = new JSONTokener(exchangeData.getRequestBody());
            this.getRequest = exchangeData.getRequestURI().getQuery();
            json = new JSONObject(tokener);
        } catch (Exception ex) {
            json = null;
        }

        this.user = user;
    }

    public RequestData(JSONObject body, User user) {
        this.json = body;
        this.user = user;
    }

    public JSONObject getJSON() {
        return json;
    }

    public boolean hasUser() {
        return user != null;
    }

    /**
     * Gets the user attached to this request
     */
    public User getUser() {
        return user;
    }

    public String getRequest(){
        return getRequest;
    }
}

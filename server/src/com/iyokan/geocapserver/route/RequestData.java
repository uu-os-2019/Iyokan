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

    public RequestData(HttpExchange exchangeData, User user) {
        try {
            JSONTokener tokener = new JSONTokener(exchangeData.getRequestBody());
            json = new JSONObject(tokener);
        } catch (Exception ex) {
            json = null;
        }

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
}

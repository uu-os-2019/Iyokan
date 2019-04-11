package com.iyokan.geocapserver.route;

import com.sun.net.httpserver.HttpExchange;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

/**
 * This class contains data relevant to a specific route, such as parameters, the attached user etc
 */
public class RequestData {
    private JSONObject json;

    public RequestData(HttpExchange exchangeData) {
        try {
            JSONTokener tokener = new JSONTokener(exchangeData.getRequestBody());
            json = new JSONObject(tokener);
        } catch (Exception ex) {
            json = null;
        }

    }

    public JSONObject getJSON() {
        return json;
    }


    /**
     * Gets the user attached to this request
     */
    public void getUser() {
        /// TODO returns a user
    }
}

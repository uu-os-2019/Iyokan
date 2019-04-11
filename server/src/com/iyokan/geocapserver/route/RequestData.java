package com.iyokan.geocapserver.route;

import com.sun.net.httpserver.HttpExchange;

import java.util.HashMap;

/**
 * This class contains data relevant to a specific route, such as parameters, the attached user etc
 */
public class RequestData {
    HashMap<String, String> parameters;

    public RequestData(HttpExchange exchangeData) {
        parameters = new HashMap<>();

        ///TODO parse parameters
    }

    private void extractParameters(String parameters) {
        parameters = parameters.substring(parameters.indexOf('?'));
        String[] keys = parameters.split("=");
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }

    public boolean hasParameter(String parameter) {
        return parameters.containsKey(parameter);
    }

    /**
     * Gets the user attached to this request
     */
    public void getUser() {
        /// TODO returns a user
    }
}

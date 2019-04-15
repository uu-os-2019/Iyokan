package com.iyokan.geocapserver.route;

import org.json.JSONObject;

public class RouteTest extends Route {

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();
        response.put("testy", "testy!");
        response.put("watusent", data.getJSON());

        if (data.hasUser()) {
            response.put("you are", data.getUser().getJson());
        }

        return response;
    }

    @Override
    public String getUrl() {
        return "/test";
    }
}

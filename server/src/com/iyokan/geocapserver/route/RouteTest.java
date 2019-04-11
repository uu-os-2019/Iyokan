package com.iyokan.geocapserver.route;

import org.json.JSONObject;

public class RouteTest extends Route {

    @Override
    public JSONObject handle() {
        JSONObject response = new JSONObject();
        response.put("testy", "testy!");

        return response;
    }

    @Override
    public String getUrl() {
        return "/test";
    }
}

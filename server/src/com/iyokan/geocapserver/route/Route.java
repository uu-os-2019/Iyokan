package com.iyokan.geocapserver.route;

import org.json.*;

public abstract class Route {

    public JSONObject handle(RequestData data) {
        throw new RuntimeException("Needs to be implemented in sub class");
    }

    public String getUrl() {
        throw new RuntimeException("Needs to be implemented in sub class");
    }

}

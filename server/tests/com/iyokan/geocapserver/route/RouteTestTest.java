package com.iyokan.geocapserver.route;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RouteTestTest {

    @Test
    void handle() {
        Route route = new RouteTest();
        RequestData data = new RequestData(new JSONObject(), null);

        JSONObject response = route.handle(data);
        assert(response.has("testy"));
    }
}
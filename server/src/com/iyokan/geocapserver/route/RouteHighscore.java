package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.Highscore;
import com.iyokan.geocapserver.UserCollection;
import com.iyokan.geocapserver.UserGuid;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RouteHighscore extends Route {
    Highscore highscore;
    UserCollection users;

    public RouteHighscore(Highscore highscore, UserCollection users) {
        this.users = users;
        this.highscore = highscore;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();
        Map<UserGuid, Integer> hashHighscore = this.highscore.getHighscore(7);
        JSONArray array = new JSONArray();

        for (Map.Entry<UserGuid, Integer> entry : hashHighscore.entrySet()) {
            JSONObject response2 = new JSONObject();
            String name = users.getUser(entry.getKey()).getName();
            response2.put("name", name);
            response2.put("points", entry.getValue());
            array.put(response2);
        }

        response.put("highscore", array);
        
        return response;
    }



    @Override
    public String getUrl() {
        return "/highscore";
    }
}

package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.Highscore;
import com.iyokan.geocapserver.UserGuid;
import org.json.JSONObject;

import java.util.HashMap;

public class RouteHighscore extends Route {
    Highscore highscore;

    public RouteHighscore(Highscore highscore) {
        this.highscore = highscore;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();


        /*HashMap<UserGuid, Integer> hashHighscore = this.highscore.getHighscore(10);

        for (UserGuid i : hashHighscore.keySet()) {
            response.put(i.toString(), hashHighscore.get(i));
        }*/

        return response;
    }

    @Override
    public String getUrl() {
        return "/highscore";
    }
}

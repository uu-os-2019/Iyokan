package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RouteLeaderboardCurrent extends Route {
    UserCollection users;

    public RouteLeaderboardCurrent(UserCollection users) {
        this.users = users;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();

        int startValue = Utils.getJsonDefault(data.getJSON(), "index", 0);
        int count = Utils.getJsonDefault(data.getJSON(), "count", 50);

        ArrayList<User> userClump = new ArrayList<>( Arrays.asList(users.getUsers()));
        userClump.sort( (a, b) -> (int)(b.getExpRate() - a.getExpRate()));
        JSONArray jUsers = new JSONArray();


        for (int i=startValue; i < userClump.size(); i++) {
            if (--count < 0) {
                break;
            }

            User user = userClump.get(i);
            JSONObject obj = new JSONObject();
            obj.put("index", i);
            obj.put("id", user.getID());
            obj.put("name", user.getName());
            obj.put("exp_rate", user.getExpRate());

            jUsers.put(obj);

        }

        response.put("success", true);
        response.put("type", "leaderboard-current");
        response.put("users", jUsers);

        return response;
    }



    @Override
    public String getUrl() {
        return "/leaderboard/current";
    }
}

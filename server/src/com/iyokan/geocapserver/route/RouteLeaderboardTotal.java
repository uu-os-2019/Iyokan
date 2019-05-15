package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.User;
import com.iyokan.geocapserver.UserCollection;
import com.iyokan.geocapserver.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class RouteLeaderboardTotal extends Route {
    private UserCollection users;


    public RouteLeaderboardTotal(UserCollection users) {
        this.users = users;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();

        int startValue = Utils.getJsonDefault(data.getJSON(), "index", 0);
        int count = Utils.getJsonDefault(data.getJSON(), "count", 50);

        ArrayList<User> userClump = new ArrayList<>( Arrays.asList(users.getUsers()));

        // Sort where the larger the further up you are
        userClump.sort( (a, b) -> (int)(b.getTotalExp() - a.getTotalExp()));

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

            User.LevelInformation info = user.getLevelInformation();
            obj.put("exp_rate", user.getExpRate());
            obj.put("exp", info.exp);
            obj.put("exp_to_level", info.expToLevel);
            obj.put("level", info.level);

            jUsers.put(obj);
        }

        response.put("success", true);
        response.put("type", "leaderboard-total");
        response.put("users", jUsers);

        return response;
    }

    @Override
    public String getUrl() {
        return "/leaderboard/total";
    }

}

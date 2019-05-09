package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.Highscore;
import com.iyokan.geocapserver.LocationCollection;
import org.json.JSONObject;

import java.util.ArrayList;

public class RouteMyProfile extends Route {
    Highscore hs;
    LocationCollection locations;

    public RouteMyProfile(Highscore hs, LocationCollection locations) {
        this.hs = hs;
        this.locations = locations;
    }

    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();
        response.put("type", "my_profile");

        if (data.getUser() == null) {
            response.put("success", false);
            response.put("reason", "no user for token");
            return response;
        }

        ArrayList<String> locationNames = new ArrayList<>();
        for(String s : data.getUser().getLocationsTaken()) {
            locationNames.add(locations.getLocation(s).getName());
        }

        response.put("locations", locationNames);
        response.put("currentScore", data.getUser().getPointRate());
        response.put("totalScore", data.getUser().getTotalScore());

        return response;
    }

    @Override
    public String getUrl() { return "/my-profile";}
}

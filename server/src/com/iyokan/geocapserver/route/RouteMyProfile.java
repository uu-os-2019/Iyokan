package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.LocationCollection;
import com.iyokan.geocapserver.UserCollection;
import org.json.JSONObject;

import java.util.ArrayList;

public class RouteMyProfile extends Route {
    LocationCollection locations;
    UserCollection users;

    public RouteMyProfile(LocationCollection locations, UserCollection users) {
        this.locations = locations;
        this.users = users;
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
        response.put("user", data.getUser().getPrivateJson());

        return response;
    }

    @Override
    public String getUrl() { return "/my-profile";}
}

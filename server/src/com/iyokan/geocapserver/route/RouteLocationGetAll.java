package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.Location;
import com.iyokan.geocapserver.LocationCollection;
import com.iyokan.geocapserver.User;
import com.iyokan.geocapserver.UserCollection;
import org.json.JSONArray;
import org.json.JSONObject;

public class RouteLocationGetAll extends Route {
    LocationCollection locationCollection;
    UserCollection users;

    public RouteLocationGetAll(LocationCollection locationCollection, UserCollection users) {
        this.locationCollection = locationCollection;
        this.users = users;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();

        response.put("type", "locations_get_all");
        JSONArray array = new JSONArray();
        Location[] allLocations = locationCollection.getAllLocations();

        for (Location location : allLocations) {
            array.put(location.getJson(users));
        }

        response.put("locations", array);
        return response;
    }

    @Override
    public String getUrl() {
        return "/location/get-all";
    }
}

package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.Location;
import com.iyokan.geocapserver.LocationCollection;
import org.json.JSONArray;
import org.json.JSONObject;

public class RouteLocationGetAll extends Route {
    LocationCollection locationCollection;

    public RouteLocationGetAll(LocationCollection locationCollection) {
        this.locationCollection = locationCollection;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject response = new JSONObject();

        response.put("type", "locations_get_all");
        JSONArray array = new JSONArray();
        Location[] allLocations = locationCollection.getAllLocations();

        for (Location location : allLocations) {
            array.put(location.getJson());
        }

        response.put("locations", array);

        return response;
    }

    @Override
    public String getUrl() {
        return "/location/get-all";
    }
}

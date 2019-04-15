package com.iyokan.geocapserver.route;

import com.iyokan.geocapserver.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Base64;
import java.util.Map;

public class RouteRegister extends Route {

    UserCollection users;
    SessionVault sessionVault;

    public RouteRegister(UserCollection users, SessionVault sessionVault) {
        this.users = users;
        this.sessionVault = sessionVault;
    }

    @Override
    public JSONObject handle(RequestData data) {
        JSONObject json = data.getJSON();

        String username = json.getString("username");

        JSONObject obj = new JSONObject();
        obj.put("type", "register");

        if (username.isEmpty()) {
            obj.put("success", false);
            obj.put("reason", "invalid username");
            return obj;
        }

        if (users.hasUser(username)) {
            obj.put("success", false);
            obj.put("reason", "username taken");
            return obj;
        }

        UserGuid guid = Utils.generateUserGuid();
        User user = new User(guid, username);

        users.addUser(user);

        byte[] tokenBytes = Utils.generateBytes(24);
        String token = Base64.getUrlEncoder().encodeToString(tokenBytes);

        obj.put("success", true);
        obj.put("user", user.getJson());
        obj.put("token", token);

        sessionVault.insert(token, user);

        return obj;
    }



    @Override
    public String getUrl() {
        return "/register";
    }

}

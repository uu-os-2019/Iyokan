package com.iyokan.geocapserver.route;

import jdk.jshell.spi.ExecutionControl;
import org.json.*;

public abstract class Route {

    public JSONObject handle() {
        throw new RuntimeException("Needs to be implemented in sub class");
    }

    public String getUrl() {
        throw new RuntimeException("Needs to be implemented in sub class");
    }

}

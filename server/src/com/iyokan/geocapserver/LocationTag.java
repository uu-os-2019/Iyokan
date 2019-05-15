package com.iyokan.geocapserver;

public class LocationTag {
    public final int weight;
    public final String tag;

    public LocationTag(String tag, int weight) {
        this.tag = tag;
        this.weight = weight;
    }
}

package com.iyokan.geocapserver;

/**
 * Guid used to represent a guid in use by a user
 */
public class UserGuid extends Guid {
    public UserGuid(byte[] b) {
        super(b);
    }

    public UserGuid(String s) {
        super(s);
    }
}

package com.iyokan.geocapserver;

import java.util.Arrays;

public abstract class Guid {
    byte[] bytes;

    /**
     * Creates an empty guid
     */
    public Guid() {
        this.bytes = new byte[16];
    }

    /***
     * Creates a guid with the specified bytes
     * @param bytes
     */
    public Guid(byte[] bytes) {
        if (bytes.length != 16) {
            throw new RuntimeException();
        }
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Guid) {
            return equals((Guid)obj);
        }
        return false;
    }

    public boolean equals(Guid obj) {
        return Arrays.equals(obj.getBytes(), bytes);
    }
}

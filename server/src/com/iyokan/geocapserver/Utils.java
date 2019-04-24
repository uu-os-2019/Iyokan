package com.iyokan.geocapserver;

import java.util.Random;

public class Utils {

    private static Random rnd = new Random();

    public static byte[] generateBytes(int length) {
        // TODO make this thread safe
        byte[] b = new byte[length];
        rnd.nextBytes(b);
        return b;
    }

    public static UserGuid generateUserGuid() {
        return new UserGuid(generateBytes(16));
    }
}

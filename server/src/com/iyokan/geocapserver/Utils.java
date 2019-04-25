package com.iyokan.geocapserver;

import java.util.Random;

public class Utils {

    private static Random rnd = new Random();

    /**
     * Clamps value between min and max
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static long clamp(long value, long min, long max) {
        return Math.min(Math.max(value, min), max);
    }

    /**
     * Clamps value between min and max
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static double clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static byte[] generateBytes(int length) {
        // TODO make this thread safe
        byte[] b = new byte[length];
        rnd.nextBytes(b);
        return b;
    }

    public static UserGuid generateUserGuid() {
        return new UserGuid(generateBytes(16));
    }

    public static <T> boolean arrayContains(T[] array, T value) {
        for (T val : array) {
            if (value.equals(val)) {
                return true;
            }
        }
        return false;
    }
}

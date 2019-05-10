package com.iyokan.geocapserver;

import org.json.JSONObject;

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

    /**
     * Tries to read a value from a JsonObject, if it does not exists returns the defaultValue
     * @param obj json object to read from
     * @param field field to read from
     * @param defaultValue the default value
     * @return if the field exists, returns it, otherwise returns the default
     */
    public static int getJsonDefault(JSONObject obj, String field, int defaultValue) {
        if (obj.has(field)) {
            return obj.getInt(field);
        }
        return defaultValue;
    }

    /**
     * Tries to read a value from a JsonObject, if it does not exists returns the defaultValue
     * @param obj json object to read from
     * @param field field to read from
     * @param defaultValue the default value
     * @return if the field exists, returns it, otherwise returns the default
     */
    public static String getJsonDefault(JSONObject obj, String field, String defaultValue) {
        if (obj.has(field)) {
            return obj.getString(field);
        }
        return defaultValue;
    }

    /**
     * Tries to read a value from a JsonObject, if it does not exists returns the defaultValue
     * @param obj json object to read from
     * @param field field to read from
     * @param defaultValue the default value
     * @return if the field exists, returns it, otherwise returns the default
     */
    public static boolean getJsonDefault(JSONObject obj, String field, boolean defaultValue) {
        if (obj.has(field)) {
            return obj.getBoolean(field);
        }
        return defaultValue;
    }

    /**
     * Tries to read a value from a JsonObject, if it does not exists returns the defaultValue
     * @param obj json object to read from
     * @param field field to read from
     * @param defaultValue the default value
     * @return if the field exists, returns it, otherwise returns the default
     */
    public static double getJsonDefault(JSONObject obj, String field, double defaultValue) {
        if (obj.has(field)) {
            return obj.getDouble(field);
        }
        return defaultValue;
    }
}

package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    static public Boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * Reads a string from the specified file
     * @param path
     * @return
     */
    static public String readStringFromFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, Charset.defaultCharset());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Writes a string to a file
     * @param path
     * @param string
     */
    static public void writeStringToFile(String path, String string) {
        try {
            byte[] encoded = string.getBytes();
            Files.write(Paths.get(path), encoded);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Reads a JsonArray from a file
     * @param path
     * @return
     */
    static public JSONArray readJsonArrayFromFile(String path) {
        JSONTokener tokener = new JSONTokener(readStringFromFile(path));
        return new JSONArray(tokener);
    }

    /**
     * Reads a JsonObject from a file
     * @param path
     * @return
     */
    static public JSONObject readJsonObjectFromFile(String path) {
        JSONTokener tokener = new JSONTokener(readStringFromFile(path));
        return new JSONObject(tokener);
    }

    /**
     * Writes a JsonObject to a file
     * @param path
     * @param object
     */
    static public void writeJsonToFile(String path, JSONObject object) {
        writeStringToFile(path, object.toString());
    }
}

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

    static public String readStringFromFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, Charset.defaultCharset());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    static public void writeStringToFile(String path, String string) {
        try {
            byte[] encoded = string.getBytes();
            Files.write(Paths.get(path), encoded);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    static public JSONArray readJsonArrayFromFile(String path) {
        JSONTokener tokener = new JSONTokener(readStringFromFile(path));
        return new JSONArray(tokener);
    }

    static public JSONObject readJsonObjectFromFile(String path) {
        JSONTokener tokener = new JSONTokener(readStringFromFile(path));
        return new JSONObject(tokener);
    }

    static public void writeJsonToFile(String path, JSONObject object) {
        writeStringToFile(path, object.toString());
    }
}

package com.iyokan.geocapserver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    static String readStringFromFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, Charset.defaultCharset());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    static JSONArray readJsonArrayFromFile(String path) {
        JSONTokener tokener = new JSONTokener(readStringFromFile(path));
        return new JSONArray(tokener);
    }
}

package com.expenses.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtil {
    private static final JSONParser parser = new JSONParser();

    private JsonUtil() {}

    public static Object parse(String jsonString, String key) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
        return jsonObject.get(key);
    }
}

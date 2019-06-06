package com.atelier.yahoo.WeatherReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.json.JsonParseException;

public class Utility {

    public static Map<String, Object> jsonToMap(Object json) throws JsonParseException {

        if(json instanceof JSONObject)
            return _jsonToMap_((JSONObject)json) ;

        return null ;
    }


   private static Map<String, Object> _jsonToMap_(JSONObject json) throws JsonParseException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(json != null) {
            retMap = toMap(json);
        }
        return retMap;
    }


    private static Map<String, Object> toMap(JSONObject object) throws JsonParseException {
        Map<String, Object> map = new HashMap<String, Object>();

        
        Set<String> keys = object.keySet();
        
        Iterator<String> keysItr = (Iterator<String>) keys;
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }


    public static List<Object> toList(JSONArray array) throws JsonParseException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}


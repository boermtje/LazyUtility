package net.botwithus.api.util;

import com.google.gson.JsonObject;

import java.util.Map;

public class Json {
    public static JsonObject serialize(Map<String, Object> map) {
        var obj = new JsonObject();
        for (var key : map.keySet()) {
            if (map.get(key) instanceof Number) {
                obj.addProperty(key, (Number) map.get(key));
            } else {
                obj.addProperty(key, (String) map.get(key));
            }
        }
        return obj;
    }
}

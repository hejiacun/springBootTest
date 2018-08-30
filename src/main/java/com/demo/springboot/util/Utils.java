package com.demo.springboot.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    public static String object2Json(Object o) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(o);
    }
    
    public static Map<String, Object> json2Map(String json) throws IOException {
        return OBJECT_MAPPER.readValue(json, new TypeReference<Map<String, Object>>(){});
    }
    
    public static <T> T map2Object(Map<String, Object> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    public static <T> T json2Object(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }
    
    public static <T> T object2Object(Object o, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(o, clazz);
    }
}

package org.zero.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
  * JSON工具类
  * @author : cgl
  * @version : 1.0
  * @since : 2020/7/10 16:09
  **/
public class JsonUtil {

    /**
     * 转为java对象
     *
     * @param jsonStr:
     * @param clazz:
     * @return java.lang.Object:
     * @author : cgl
     * @version : 1.0
     * @since 2020/7/10 16:11
     **/
    public static <T> T toJavaObject(String jsonStr, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, clazz);
    }

    /**
     * 转为json字符串
     *
     * @param obj:
     * @return java.lang.String:
     * @author : cgl
     * @version : 1.0
     * @since 2020/7/10 16:11
     **/
    public static String toJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}

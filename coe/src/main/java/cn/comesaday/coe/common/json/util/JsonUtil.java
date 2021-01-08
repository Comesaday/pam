package cn.comesaday.coe.common.json.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <描述> json处理工具类
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-08 14:07
 */
public class JsonUtil {

    public static String toJson(Object object) {
        if (null == object) return null;
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJsonIgnoreNull(Object object) {
        if (null == object) return null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 过滤对象的null属性.
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            // 过滤map的null属性.
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) return null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 过滤对象的null属性.
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            // 过滤map的null属性.
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            CollectionLikeType type = mapper.getTypeFactory()
                    .constructCollectionLikeType(ArrayList.class, clazz);
            return mapper.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

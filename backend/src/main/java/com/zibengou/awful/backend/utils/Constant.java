package com.zibengou.awful.backend.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Constant {

    public static final Gson GSON = new Gson();

    public static <T> T merge(T joda, Map<String, Object> valueMap) {
        if (joda == null) {
            return null;
        }
        Class cls = joda.getClass();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : cls.getMethods()) {
            methodMap.put(method.getName(), method);
        }
        for (Field field : cls.getDeclaredFields()) {
            String name = field.getName();
            if (valueMap.containsKey(name)) {
                String methodName = "set" + StringUtils.capitalize(name);
                Object v = valueMap.get(name);
                if (methodMap.containsKey(methodName)) {
                    Method method = methodMap.get(methodName);
                    field.setAccessible(true);
                    try {
                        method.invoke(joda, v);
                    } catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return joda;
    }

    public static String[] filterSensitive(String str) {
        return new String[]{};
    }
}

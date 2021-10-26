package com.mda.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mda.annotation.ConfigIsRequired;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class ConfigUtil {
    public static final String COMMON = "COMMON";
    public static final String USERNAME = "USERNAME";
    public static final String DATA = "DATA";
    public static final String LIST = "LIST";

    public static final String OK = "OK";
    public static final String NG = "NG";

    public static String doParamCheck(Object jsonString, Class<?> mainClz, Class<?> itemClz) {
        String msg = null;
        if (OK.equalsIgnoreCase(msg)) {
            msg = preCheck(jsonString, mainClz, itemClz, DATA, LIST);
        }
        return msg;
    }

    public static Object doParseMain(Object jsonString, Class<?> mainClz) {
        return parseMain(jsonString, mainClz, DATA);
    }

    public static String checkErpIsRequired(Object pojo) {
        try {
            String msg = "OK";
            Class cls = pojo.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                f.setAccessible(true);
                ConfigIsRequired annotation = f.getAnnotation(ConfigIsRequired.class);
                if (annotation != null) {
                    boolean flag = (f.get(pojo) != null && !StringUtils.isBlank(String.valueOf(f.get(pojo))));
                    if (!flag) {
                        msg = annotation.msg();
                        return msg;
                    }
                }
            }
            return "OK";
        } catch (Exception e) {
            return "NG";
        }
    }

    /**
     * 校验参数
     *
     * @param jsonString
     * @param mainClz
     * @param itemClz
     * @param mainKey
     * @param itemKey
     * @return
     */
    public static String preCheck(Object jsonString, Class<?> mainClz, Class<?> itemClz, String mainKey, String itemKey) {
        String paramsCheckMsg = null;
        StringBuilder checkMsg = new StringBuilder();

        if (mainClz != null) {
            Object erpInfoPojo = parseMain(jsonString, mainClz, mainKey);
            if (erpInfoPojo != null) {
                paramsCheckMsg = checkErpIsRequired(erpInfoPojo);
            } else {
                paramsCheckMsg = "main解析异常";
            }
        } else {
            paramsCheckMsg = "main解析类不能为空";
        }
        checkMsg.append(paramsCheckMsg);
        return checkMsg.toString();
    }

    /**
     * 解析item
     *
     * @param jsonString
     * @param mainClz
     * @param mainKey
     * @return
     */
    public static Object parseMain(Object jsonString, Class<?> mainClz, String mainKey) {
        JSONObject jsonObject = JSONObject.parseObject((String) jsonString);
//        String dataString = null;
//        try {
//            dataString = JSON.toJSONString(jsonObject.get(mainKey.toUpperCase()));
//        } catch (Exception e) {
//            dataString = JSON.toJSONString(jsonObject.get(mainKey.toLowerCase()));
//        }

        try {
//            JSONObject dataJsonObject = JSONObject.parseObject(dataString);
            Object mainObj = JSON.toJavaObject(jsonObject, mainClz);
            return mainObj;
        } catch (Exception e) {
            return null;
        }
    }
}

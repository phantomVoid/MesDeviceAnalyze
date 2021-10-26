package com.mda.demo;

import com.alibaba.fastjson.JSON;
import com.mda.pojo.ConfigPojo;
import com.mda.utils.ConfigUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class demo {

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();

        InputStream inputStream = null;

        try {
//            prop.load(new FileInputStream("res/mda.properties"));
            prop.load(new FileInputStream("mda-common/src/main/resources/mda.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<Map.Entry<Object, Object>> props = prop.entrySet();
        for (Map.Entry<Object, Object> entry : props) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            System.setProperty(entry.getKey().toString(), entry.getValue().toString());
        }
        String jsonString = JSON.toJSONString(prop);
        System.out.println(jsonString);
        ConfigPojo configPojo = (ConfigPojo) ConfigUtil.doParseMain(jsonString, ConfigPojo.class);
        System.out.println(JSON.toJSONString(configPojo));

        System.out.println(System.getProperty("CONFIG_TYPE"));

    }
}

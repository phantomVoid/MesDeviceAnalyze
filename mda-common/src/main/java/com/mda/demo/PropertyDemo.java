package com.mda.demo;

import com.alibaba.fastjson.JSON;
import com.mda.pojo.ConfigPojo;
import com.mda.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertyDemo {

    public static final Logger log = LoggerFactory.getLogger(PropertyDemo.class);

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();

        InputStream inputStream = null;

        try {
            prop.load(new FileInputStream("res/mda.properties"));
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
        log.info(JSON.toJSONString(configPojo));

        log.info(System.getProperty("CONFIG_TYPE"));

    }
}

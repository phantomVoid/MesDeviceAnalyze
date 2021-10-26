package com.mda;

import com.alibaba.fastjson.JSON;
import com.mda.pojo.ConfigPojo;
import com.mda.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 启动程序
 *
 * @author: phantomsaber
 * @version: 2021/10/21 5:48
 * @email: phantomsaber@foxmail.com
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MdaApplication {
    private static final Logger log = LoggerFactory.getLogger(MdaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MdaApplication.class, args);
        StringBuilder info = new StringBuilder();
        info.append("\n(♥◠‿◠)ﾉﾞ  MES设备文件解析程序(MDA)启动成功   ლ(´ڡ`ლ)ﾞ  \n");
        info.append("\t███╗   ███╗██████╗  █████╗     ██╗███████╗    ██████╗ ██╗   ██╗███╗   ██╗███╗   ██╗██╗███╗   ██╗ ██████╗ \n");
        info.append("\t████╗ ████║██╔══██╗██╔══██╗    ██║██╔════╝    ██╔══██╗██║   ██║████╗  ██║████╗  ██║██║████╗  ██║██╔════╝ \n");
        info.append("\t██╔████╔██║██║  ██║███████║    ██║███████╗    ██████╔╝██║   ██║██╔██╗ ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗\n");
        info.append("\t██║╚██╔╝██║██║  ██║██╔══██║    ██║╚════██║    ██╔══██╗██║   ██║██║╚██╗██║██║╚██╗██║██║██║╚██╗██║██║   ██║\n");
        info.append("\t██║ ╚═╝ ██║██████╔╝██║  ██║    ██║███████║    ██║  ██║╚██████╔╝██║ ╚████║██║ ╚████║██║██║ ╚████║╚██████╔╝\n");
        info.append("\t╚═╝     ╚═╝╚═════╝ ╚═╝  ╚═╝    ╚═╝╚══════╝    ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ \n");
//        System.out.println(info.toString());

        log.info(info.toString());
//        ConfigPojo configPojo = readProperties();
//        log.info(JSON.toJSONString(configPojo));
    }

    public static ConfigPojo readProperties(){
        Properties prop =new Properties();

        InputStream inputStream =null;

        try {
            prop.load(new FileInputStream("res/mda.properties"));

            /* 注释：也可以直接在src/main/resources目录下新建配置文件，但是new FileInputStream("res/myCanal.properties")需要改为new FileInputStream("src/main/resources/myCanal.properties") */

        }catch (FileNotFoundException e) {
            e.printStackTrace();

        }catch (IOException e) {
            e.printStackTrace();
        }

        Set<Map.Entry<Object, Object>> props = prop.entrySet();
        for (Map.Entry<Object, Object> entry : props) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
            System.setProperty(entry.getKey().toString(),entry.getValue().toString());
        }

        System.out.println(System.getProperty("CONFIG_TYPE"));
        String jsonString = JSON.toJSONString(prop);

        ConfigPojo configPojo = (ConfigPojo) ConfigUtil.doParseMain(jsonString, ConfigPojo.class);
        return configPojo;
    }
}

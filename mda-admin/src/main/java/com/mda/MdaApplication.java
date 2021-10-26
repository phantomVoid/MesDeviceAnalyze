package com.mda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author: phantomsaber
 * @version: 2021/10/21 5:48
 * @email: phantomsaber@foxmail.com
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MdaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdaApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  MES设备文件解析程序启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " __      ______ _____ _____  \n" +
                " \\ \\    / / __ \\_   _|  __ \\ \n" +
                "  \\ \\  / / |  | || | | |  | |\n" +
                "   \\ \\/ /| |  | || | | |  | |\n" +
                "    \\  / | |__| || |_| |__| |\n" +
                "     \\/   \\____/_____|_____/ \n" +
                "                             \n" +
                "                             ");
    }
}

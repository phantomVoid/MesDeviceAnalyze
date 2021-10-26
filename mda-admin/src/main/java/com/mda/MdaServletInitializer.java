package com.mda;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * web容器中进行部署
 *
 * @author: phantomsaber
 * @version: 2021/10/21 6:34
 * @email: phantomsaber@foxmail.com
 **/
public class MdaServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MdaApplication.class);
    }
}

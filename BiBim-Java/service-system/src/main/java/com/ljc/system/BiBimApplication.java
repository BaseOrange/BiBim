package com.ljc.system;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author dachengzi
 * @date 2022-12-27
 */
@Slf4j
@SpringBootApplication
public class BiBimApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(BiBimApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        port = port == null ? "8800" : port;
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        log.info("\n----------------------------------------------------------\n" +
                "|\tApplication is running! Access URLs:\n" +
                "|\t本地访问地址: http://localhost:" + port + path + "\n" +
                "|\t外部访问地址: http://" + ip + ":" + port + path + "\n" +
                "|\tSwagger文档: http://"+ip+":"+port+path+"/swagger-ui.html" + "\n" +
                "----------------------------------------------------------");
    }
}
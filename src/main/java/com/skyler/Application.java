package com.skyler;

import com.alibaba.fastjson.JSON;
import com.skyler.config.StockAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-22 at 11:15
 */
@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class Application {

    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}

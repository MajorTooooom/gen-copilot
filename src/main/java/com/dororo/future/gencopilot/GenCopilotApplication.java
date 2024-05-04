package com.dororo.future.gencopilot;

import com.dororo.future.gencopilot.util.SqliteHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@MapperScan(basePackages = {"com.dororo.future.gencopilot.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GenCopilotApplication {

    public static void main(String[] args) {
        SqliteHelper.init();
        SpringApplication.run(GenCopilotApplication.class, args);
    }

}

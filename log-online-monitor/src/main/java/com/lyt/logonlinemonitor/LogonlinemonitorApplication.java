package com.lyt.logonlinemonitor;

import cn.hutool.core.date.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Date;

@SpringBootApplication
@Log4j2
@EnableAsync
@ComponentScan({"com.lyt.logonlinemonitor.server","com.lyt"})
public class LogonlinemonitorApplication {
    private static ConfigurableApplicationContext context;
    private static String[] args;

    public static void main(String[] args) {

        SpringApplication.run(LogonlinemonitorApplication.class, args);
        //产生测试日志数据代码
//        new Thread(()->{
//            while(true){
//                try {
//                    Thread.sleep(3000);
//                    log.info(DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        },"aaaaa").start();

    }

}

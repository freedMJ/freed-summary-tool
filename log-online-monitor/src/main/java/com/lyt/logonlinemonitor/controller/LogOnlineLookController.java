package com.lyt.logonlinemonitor.controller;


import cn.hutool.core.util.StrUtil;
import com.lyt.logonlinemonitor.server.CacheServer;
import com.lyt.logonlinemonitor.server.WebSocketServer;
import com.lyt.logonlinemonitor.sys.model.ResultObj;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class LogOnlineLookController {

    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private CacheServer cacheServer;
    @Value("${spring.application.name}")
    private String name;
    @Value("${logInfo.fileName}")
    private String logInfoFileName;
    @GetMapping("/getFilteLogInfo")
    public ResultObj getFilteLogInfo(@RequestParam("fileName")String fileName){
        log.info("输出日志开始，，，，，");
        if(StrUtil.hasEmpty(fileName)){
            return ResultObj.error(500,"文件名为空");
        }
        log.info("spring application name"+name);
        log.info("logInfoFileName"+logInfoFileName);

        log.info("开始创建消息");
        cacheServer.createMessage();
        log.info("开始群发消息");
        System.out.println("asdddddddddddddddddddddddddddddddddddddddd");
        log.info("连接数。。。。。"+WebSocketServer.webSocketSet.size());
        webSocketServer.sendInfo();
        return ResultObj.ok();
    }





}

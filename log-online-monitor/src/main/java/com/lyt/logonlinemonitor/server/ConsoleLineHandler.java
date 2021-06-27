package com.lyt.logonlinemonitor.server;

import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.Console;
import lombok.Data;

@Data
public class ConsoleLineHandler implements LineHandler {




    @Override
    public void handle(String line) {
        Console.log(line);
        CacheServer.QUEUE.add(line);
//        if(!WebSocketServer.webSocketSet.isEmpty()){
//
//        }
    }


}

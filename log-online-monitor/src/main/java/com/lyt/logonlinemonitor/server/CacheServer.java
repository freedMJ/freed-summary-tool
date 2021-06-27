package com.lyt.logonlinemonitor.server;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;

@Service
@Log4j2
public class CacheServer {

    public static Process process;
    public static InputStream inputStream;
    public static final ConcurrentLinkedQueue QUEUE = new ConcurrentLinkedQueue();
    @Value("${logInfo.fileName}")
    private  String fileName;
    //是否需要重新生成数据
    public static volatile Boolean createDateFlag=false;

    /**
     *
     * 创建消息
     *
     * **/
    public void createMessage() {
        //判断是否已经创建过数据了
        log.info("是否创建了----" + createDateFlag);
        if (!createDateFlag && WebSocketServer.webSocketSet.size() > 0) {
        // 执行tail -f命令
            try {
                log.info("执行的文件查看命令1是：" + fileName);
                createDateFlag = true;
                String format = String.format("tail -f %s", fileName);
                process = Runtime.getRuntime().exec(format);
                inputStream = process.getInputStream();
                // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
                TailLogThread thread = new TailLogThread(inputStream);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 返回输出的值
     * **/
    public  Object getMessage(){
        if(QUEUE.isEmpty()){
            return null;
        }
        return QUEUE.poll();
    }


}

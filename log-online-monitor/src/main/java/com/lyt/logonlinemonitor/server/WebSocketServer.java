package com.lyt.logonlinemonitor.server;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/logs")
@Service
@Slf4j
@Data
public class WebSocketServer {

    private String fileName;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //接收fileName,访问指定文件名会返回指定的输出流
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    @Autowired
    private CacheServer cacheServer;




    //Executors.newF
    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("已建立的连接数为-----------"+webSocketSet.size());
    }

    /**
     * 群发自定义消息
     * */

    public void  sendInfo() {

        log.info("socketsWitch" + webSocketSet.size());

        //没有发过
        Object message;
        log.info("第一次发送信息，。。。");
        log.info("CacheServer.createDateFlag====="+CacheServer.createDateFlag);
        while (!webSocketSet.isEmpty()){
            message = cacheServer.getMessage();
            for (WebSocketServer ws:webSocketSet) {
                try {
                    if(!ObjectUtils.isEmpty(message)&&!ObjectUtils.isEmpty(ws)){
                        ws.session.getBasicRemote().sendText(message.toString()+"</br>");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("socketsWitch" + webSocketSet.size());
        log.info("暂无连接，停止发送消息。。。。。");
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);

        log.info("断开连接。。。。剩余连接数"+webSocketSet.size());
        if(webSocketSet.isEmpty()){
            //清空queue的所有元素
            int size = CacheServer.QUEUE.size();
            //停止生产数据
            try {
                CacheServer.inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(CacheServer.process!=null){
                CacheServer.process.destroy();
            }
            log.info("缓存队列清空，队列容量----"+CacheServer.QUEUE.size());
            CacheServer.QUEUE.remove(size);
            CacheServer.createDateFlag=false;

        }
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}

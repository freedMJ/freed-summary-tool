package com.lyt.logonlinemonitor.server;


import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Log4j2
public class TailLogThread extends Thread {

    private BufferedReader reader;

    public TailLogThread(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in));

    }

    @Override
    public void run() {
        String line;
        try {
            while((line = reader.readLine()) != null&&!WebSocketServer.webSocketSet.isEmpty()) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                CacheServer.QUEUE.add(line);
                System.out.println("line----------------"+line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
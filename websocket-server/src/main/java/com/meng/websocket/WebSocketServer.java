package com.meng.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/webSocket/{sid}")
public class WebSocketServer {

    Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    // 在线人数
    private static AtomicInteger onlineNum = new AtomicInteger();
    // 在线的客户端连接
    private static ConcurrentHashMap<String, Session> clientSessions = new ConcurrentHashMap<>();

    /**
     * 发送消息
     */
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 指定用户发送信息
     */
    public void sendMsgToUser(String userId, String message) {
        Session session = clientSessions.get(userId);
        try{
            sendMessage(session, message);
        }catch (Exception e) {
            logger.error("send message fail,userId:{}, message:{}, exception message:{}", userId, message, e.getMessage(), e);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userId){
        clientSessions.put(userId, session);
        addOnlineNum();
        try{
            sendMessage(session, "欢迎" + userId + "加入，当前人数：" + clientSessions.size());
        }catch (Exception e) {
            logger.error("connect fail,userId:{}, exception message:{}", userId, e.getMessage(), e);
        }
    }

    // 关闭连接
    @OnClose
    public void onClose(@PathParam(value = "sid") String userId){
        clientSessions.remove(userId);
        logger.info("{} 断开webSocket连接！当前人数为:{}", userId, clientSessions.size());
    }

    // 收到客户端信息
    @OnMessage
    public void onMessage(Session session, String message) throws IOException{
        logger.info("收到客户端：{} 请求,请求内容：{}", session.getId(), message);
    }

    // 错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){
        logger.error("连接出错， 会话id：{}", session.getId(), throwable);
    }

    private void addOnlineNum() {
        onlineNum.getAndIncrement();
    }

}

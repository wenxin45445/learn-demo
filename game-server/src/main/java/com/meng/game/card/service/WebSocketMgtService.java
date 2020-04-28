package com.meng.game.card.service;

import org.springframework.stereotype.Component;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketMgtService {
    private final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**
     * 踢用户下线
     */
    public Session kickOffClient(Session session) {
        Session remove = sessionMap.remove(session.getId());
        CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.getCloseCode(5001),
                "you have been kick off");
        try {
            remove.close(closeReason);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return remove;
    }

    /**
     * 踢用户下线
     */
    public Session removeSession(Session session) {
        return sessionMap.remove(session.getId());
    }

    /**
     * 踢用户下线
     */
    public Session kickOffClient(String id) {
        Session remove = sessionMap.remove(id);
        CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.getCloseCode(1006),
                "you have been kick off");
        try {
            remove.close(closeReason);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return remove;
    }

    /**
     * 客户端连接
     */
    public Session addSession(Session session) {
        return sessionMap.put(session.getId(), session);
    }

    public Session getSession(String id) {
        return sessionMap.get(id);
    }
}

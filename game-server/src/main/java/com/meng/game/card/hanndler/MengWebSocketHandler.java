package com.meng.game.card.hanndler;

import org.springframework.web.socket.*;

import java.util.concurrent.ConcurrentHashMap;

public class MengWebSocketHandler implements WebSocketHandler {
    private static ConcurrentHashMap<String, WebSocketSession> userSession = new ConcurrentHashMap();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        userSession.put(webSocketSession.getId(), webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        webSocketSession.sendMessage(new TextMessage("get a message:" + webSocketMessage.getPayload().toString()));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        userSession.remove(webSocketSession.getId());
        webSocketSession.close(closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

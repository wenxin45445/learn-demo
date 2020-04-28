package com.meng.game.card.websocket.orgin;

import com.meng.game.card.service.WebSocketMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws", configurator = EndpointConfigure.class)
public class WebSocketServer {

    @Autowired
    WebSocketMgtService webSocketMgtService;

    @OnOpen
    public void onOpen(Session session){
        webSocketMgtService.addSession(session);
    }

    @OnMessage
    public void  onMessage(String msg, Session session){
        System.out.println(msg);
    }

    @OnClose
    public void onClose(Session session){
        webSocketMgtService.removeSession(session);
    }

    @OnError
    public void onError(Throwable error, Session session){
        session.getAsyncRemote().sendText(error.getMessage());
    }
}

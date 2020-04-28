package com.meng.game.card.websocket.inweb;

import com.sun.deploy.net.HttpUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketInterceptor extends TextWebSocketHandler implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        String token = getToken(request.getURI().toString());

//        JSONObject params = new JSONObject();
//        params.put("accessToken", token);
//        UrlProperties properties = UrlProperties.getUrlProperties();
//
//        int code = HttpUtils.checkToken(properties.getChecktokenurl(), params);
//
//        if (code == 200) {
//            logger.info("TOKEN为【{}】的用户准备建立消息连接", token);
//            return true;
//        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Exception e) {
        String token = getToken(request.getURI().toString());
//        logger.info("TOKEN为【{}】的用户建立消息连接已完成", token);
    }

    private String getToken(String uri) {
        int index = uri.indexOf("accessToken=");
        return uri.substring(index + 12, uri.length());
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        logger.info("xxx用户进入系统。。。");
//        logger.info("用户信息:" + session.getAttributes());
        System.out.println(session.getAttributes());
        System.out.println("xxx用户进入系统。。。");

        Map<String, Object> map = session.getAttributes();
        for (String key : map.keySet()) {
            System.out.println("key:" + key + " and value:" + map.get(key));
        }

    }
}

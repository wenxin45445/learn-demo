package com.meng.game.card.netty;

import com.meng.game.card.bo.WebSocketPermissionBO;
import com.sun.jndi.toolkit.url.UrlUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
public class PermissionWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(PermissionWebSocketHandler.class);

    private String websocketPath;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            if (!StringUtils.isEmpty(uri)) {
                Map<String, String> map = UrlUtil.parseUrl(uri);
                // 获取用户token
                String token = map.get("token");
                AttributeKey<WebSocketPermissionBO> key = AttributeKey.valueOf("perssion");
                WebSocketPermissionBO webSocketPerssionVerify = ctx.channel().attr(key).get();
                if (null == webSocketPerssionVerify) {
                    webSocketPerssionVerify = new WebSocketPermissionBO();
                    webSocketPerssionVerify.setToken(token);
                }
                ctx.channel().attr(key).setIfAbsent(webSocketPerssionVerify);
                request.setUri(websocketPath);
                ctx.fireChannelRead(request.retain());
            }
        }
        ctx.fireChannelRead(msg);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("websocket exception", cause);
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        channelHandlerContext.fireChannelRead(textWebSocketFrame);
    }
}

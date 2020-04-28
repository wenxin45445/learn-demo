package com.meng.game.card.netty;

import com.meng.game.card.service.SendMsgService;
import com.meng.game.card.token.TokenVerifyServiceFacade;
import com.meng.game.card.websocket.inweb.WebSocketConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SendMsgService sendMsgServie;
    @Autowired
    private TokenVerifyServiceFacade tokenVerifyServiceFacade;

    /**
     * channel被启用的时候触发（在建立连接的时候）,服务端监听到客户端活动
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("connect to client");
        // 添加到channelGroup 通道组
        NettyConfig.getChannelGroup().add(ctx.channel());
    }

    /**
     * channel断开时候触发
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("disconnect to client");
        // 删除渠道
        NettyConfig.getChannelGroup().remove(ctx.channel());
        removeChannelId(ctx);
    }

    /**
     * 删除channelId: <br>
     *
     * @author yangxiaodong<br>
     * @taskId <br>
     * @param ctx 上下文<br>
     */
    private void removeChannelId(ChannelHandlerContext ctx) {
        String userId = getUserId(ctx);
        if (!StringUtils.isEmpty(userId)) {
            redisTemplate.opsForSet().remove(WebSocketConstant.BOSS_MSG_CHANNELID + userId, ctx.channel().id());
        }
    }

    /**
     * 工程出现异常的时候调用: <br>
     *
     * @author yangxiaodong<br>
     * @taskId <br>
     * @param ctx 处理上下文
     * @param cause 异常
     * @throws Exception <br>
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("websocket exception", cause);
        removeChannelId(ctx);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 握手成功以后，查询用户未读消息，发送未读消息
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            AttributeKey<WebSocketPerssionVerify> key = AttributeKey.valueOf("perssion");
            WebSocketPerssionVerify webSocketPerssionVerify = ctx.channel().attr(key).get();
            if (null != webSocketPerssionVerify) {
                String token = webSocketPerssionVerify.getToken();
                if (StringUtils.isEmpty(token)) {
                    sendUserNotLoginMsg(ctx);
                    return;
                }
                // 校验token是否登陆
                TokenVerifyReq tokenVerifyReq = new TokenVerifyReq();
                tokenVerifyReq.setToken(token);
                TokenVerifyResp resp = tokenVerifyServiceFacade.verifyToken(tokenVerifyReq);
                ReplyStatus status = resp.getStatus();
                if (!status.equals(ReplyStatus.OK)) {
                    sendUserNotLoginMsg(ctx);
                    return;
                }
                String userId = resp.getUserId();
                redisTemplate.opsForSet().add(WebSocketConstant.BOSS_MSG_CHANNELID + userId, ctx.channel().id());
                redisTemplate.expire(WebSocketConstant.BOSS_MSG_CHANNELID + userId, 1, TimeUnit.DAYS);
                webSocketPerssionVerify.setUserId(userId);
                ctx.channel().attr(key).setIfAbsent(webSocketPerssionVerify);
                // 发送未读消息
                QueryUnreadInMailMsg queryUnreadInMailMsg = new QueryUnreadInMailMsg();
                queryUnreadInMailMsg.setUserId(userId);
                queryUnreadInMailMsg(queryUnreadInMailMsg);
            } else {
                sendUserNotLoginMsg(ctx);
                return;
            }
        }
        // 用于触发用户事件，包含触发读空闲、写空闲、读写空闲
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                removeChannelId(ctx);
                // 关闭无用channel，以防资源浪费
                channel.close();
            }
        }
    }

    /**
     * 发送用户为登陆消息: <br>
     *
     * @author yangxiaodong<br>
     * @taskId <br>
     * @param ctx <br>
     */
    public void sendUserNotLoginMsg(ChannelHandlerContext ctx) {
        removeChannelId(ctx);
        ChannelFuture future = ctx.writeAndFlush(new CloseWebSocketFrame(InMailMsgResp.USER_NOT_LOGIN, InMailMsgResp.USER_NOT_LOGIN_MSG));
        future.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 获取用户id: <br>
     *
     * @author yangxiaodong<br>
     * @taskId <br>
     * @param ctx 上下文
     * @return <br>
     */
    private String getUserId(ChannelHandlerContext ctx) {
        AttributeKey<WebSocketPerssionVerify> key = AttributeKey.valueOf("perssion");
        WebSocketPerssionVerify webSocketPerssionVerify = ctx.channel().attr(key).get();
        if (null != webSocketPerssionVerify) {
            return webSocketPerssionVerify.getUserId();
        }
        return null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("server receiver client msg:{}", msg.text());
        }
        String text = msg.text();
        if (StringUtils.isNotEmpty(text)) {
            JSONObject parseObject = JSONObject.parseObject(text);
            String actionType = parseObject.getString("actionType");
            if (WebSocketConstant.INMAIL_ACTION_TYPE_SEND.equals(actionType)) {
                // 发送消息
                SendInMailMsg sendInMailMsg = JSONObject.parseObject(text, SendInMailMsg.class);
                sendMsg(sendInMailMsg);
            } else if (WebSocketConstant.INMAIL_ACTION_TYPE_MARK.equals(actionType)) {
                // 标记已读
                MarkInMailMsgRead markInMailMsgRead = JSONObject.parseObject(text, MarkInMailMsgRead.class);
                markMsg(markInMailMsgRead);
            }
        }
    }

    /**
     * 发送消息: <br>
     *
     * @author yangxiaodong<br>
     * @taskId <br>
     * @param sendInMailMsg 消息
     */
    private void sendMsg(SendInMailMsg sendInMailMsg) {
        sendInMailMsgService.sendMsg(sendInMailMsg);
    }

    /**
     * 标记消息已读: <br>
     *
     * @author yangxiaodong<br>
     * @taskId <br>
     * @param markInMailMsgRead 消息主体
     */
    private void markMsg(MarkInMailMsgRead markInMailMsgRead) {
        MarkInMailMsgReadReqDTO req = new MarkInMailMsgReadReqDTO();
        req.setMsgIds(markInMailMsgRead.getMsgIds());
        req.setUserId(markInMailMsgRead.getUserId());
        sendInMailMsgService.markMsg(markInMailMsgRead);
    }

    /**
     * 查询未读站内信信息: <br>
     * yangxiaodong<br>
     *
     * @taskId <br>
     * @param queryUnreadInMailMsg 参数<br>
     */
    private void queryUnreadInMailMsg(QueryUnreadInMailMsg queryUnreadInMailMsg) {
        sendInMailMsgService.queryUnreadInMailMsg(queryUnreadInMailMsg);
    }
}

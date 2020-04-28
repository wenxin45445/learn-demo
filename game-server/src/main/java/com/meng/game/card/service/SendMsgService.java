package com.meng.game.card.service;

import com.meng.game.card.websocket.inweb.WebSocketConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

public class SendMsgService {
    /**
     * redis模板工具类 <br>
     */
    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * inMailMessageServiceFacade <br>
     */
    @Reference
    private InMailMessageServiceFacade inMailMessageServiceFacade;

    /**
     * 发送前方法: <br>
     */
    private void beforeSend(SendInMailMsg sendMessage) {
        List<InMailMsgBody> contentList = sendMessage.getContentList();
        if (CollectionUtils.isNotEmpty(contentList)) {
            // 添加消息前缀
            contentList.forEach(item -> item.setMsgId(WebSocketConstant.INMAIL_MSG_PREFIX + item.getMsgId()));
        }
    }

    /**
     * 保存消息记录: <br>
     */
    private void saveMsgRecord(SendInMailMsg sendMessage) {
        AddInMailMsgReqDTO req = new AddInMailMsgReqDTO();
        List<InMailMsgBody> contentList = sendMessage.getContentList();
        if (CollectionUtils.isNotEmpty(contentList)) {
            List<AddInMailMsg> msgList = new ArrayList<>();
            contentList.forEach(item -> {
                AddInMailMsg addInMailMsg = ModelMapperUtil.strictMap(item, AddInMailMsg.class);
                addInMailMsg.setMsgType(sendMessage.getSendType());
                addInMailMsg.setSenderId(sendMessage.getSenderId());
                addInMailMsg.setSendTime(sendMessage.getSendTime());
                addInMailMsg.setReceivers(sendMessage.getReceivers());
                msgList.add(addInMailMsg);
            });
            req.setMsgList(msgList);
            inMailMessageServiceFacade.addInMailMsg(req);
        }
    }

    /**
     * 发送消息
     *
     */
    public void sendMsg(SendInMailMsg sendInMailMsg) {
        beforeSend(sendInMailMsg);
        if (WebSocketConstant.SEND_TYPE_ONE_TO_ONE.equals(sendInMailMsg.getSendType())
                || WebSocketConstant.SEND_TYPE_ONE_TO_MANY.equals(sendInMailMsg.getSendType())) {
            send(sendInMailMsg);
        } else if (WebSocketConstant.SEND_TYPE_GROUP.equals(sendInMailMsg.getSendType())) {
            sendToAll(sendInMailMsg);
        }
    }

    /**
     * 查询未: <br>
     *
     */
    public void queryUnreadInMailMsg(QueryUnreadInMailMsg queryUnreadInMailMsg) {
        ChannelGroup channelGroup = NettyConfig.getChannelGroup();
        if (null != channelGroup) {
            QueryUnReadInMailMsgReqDTO req = new QueryUnReadInMailMsgReqDTO();
            req.setUserId(queryUnreadInMailMsg.getUserId());
            QueryUnReadInMailMsgRespDTO resp = inMailMessageServiceFacade.queryUnReadInMailMsg(req);
            if (resp.getSuccess()) {
                sendUnreadInMailMsg(channelGroup, resp, queryUnreadInMailMsg);
            }
        }
    }

    /**
     * 发送未读站内信: <br>
     *
     */
    @SuppressWarnings("unchecked")
    private void sendUnreadInMailMsg(ChannelGroup channelGroup, QueryUnReadInMailMsgRespDTO resp, QueryUnreadInMailMsg queryUnreadInMailMsg) {
        List<InMailMsgInfo> msgList = resp.getMsgList();
        Set<ChannelId> members = redisTemplate.opsForSet().members(getChannelId(queryUnreadInMailMsg.getUserId()));
        if (CollectionUtils.isNotEmpty(members)) {
            InMailMsgData unReadInMailMsg = new InMailMsgData();
            unReadInMailMsg.setCount(resp.getCount());
            unReadInMailMsg.setMsgList(msgList);
            String jsonString = JSONObject.toJSONString(unReadInMailMsg);
            for (ChannelId channelId : members) {
                Channel channel = channelGroup.find(channelId);
                if (null != channel) {
                    channelGroup.find(channelId).writeAndFlush(new TextWebSocketFrame(jsonString));
                }
            }
        }
    }

    /**
     * 标记消息已读: <br>
     */
    public void markMsg(MarkInMailMsgRead markInMailMsgRead) {
        MarkInMailMsgReadReqDTO req = new MarkInMailMsgReadReqDTO();
        req.setMsgIds(markInMailMsgRead.getMsgIds());
        req.setUserId(markInMailMsgRead.getUserId());
        inMailMessageServiceFacade.markInMailMsgRead(req);
    }

    /**
     * 发送一对多消息: <br>
     */
    @SuppressWarnings("unchecked")
    private void send(SendInMailMsg sendMessage) {
        ChannelGroup channelGroup = NettyConfig.getChannelGroup();
        if (null != channelGroup) {
            List<String> receivers = sendMessage.getReceivers();
            if (CollectionUtils.isNotEmpty(receivers)) {
                for (String receive : receivers) {
                    Set<ChannelId> members = redisTemplate.opsForSet().members(getChannelId(receive));
                    if (CollectionUtils.isNotEmpty(members)) {
                        for (ChannelId channelId : members) {
                            if (null != channelId) {
                                sendToUser(channelId, channelGroup, sendMessage);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 给用户发送消息: <br>
     *
     */
    private void sendToUser(ChannelId channelId, ChannelGroup channelGroup, SendInMailMsg sendMessage) {
        if (null != channelId) {
            Channel channel = channelGroup.find(channelId);
            if (null != channel) {
                InMailMsgData inMailMsgData = new InMailMsgData();
                inMailMsgData.setCount(DigitConst.ONE);
                List<InMailMsgInfo> inMailMsgInfoList = ModelMapperUtil.strictMapList(sendMessage.getContentList(), InMailMsgInfo.class);
                inMailMsgData.setMsgList(inMailMsgInfoList);
                ChannelFuture channelFuture = channel
                        .writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(inMailMsgData)));
                // 获取用户信息
                AttributeKey<WebSocketPerssionVerify> key = AttributeKey.valueOf("perssion");
                WebSocketPerssionVerify webSocketPerssionVerify = channel.attr(key).get();
                String userId = webSocketPerssionVerify.getUserId();
                sendMessage.setReceivers(Arrays.asList(userId));
                saveMsgRecord(sendMessage);
                // 消息发送成功后，保存发送记录
                channelFuture.addListener(future -> {
                    if (future.isSuccess()) {
                        saveMsgRecord(sendMessage);
                    }
                });
            }
        }
    }

    /**
     * 群发消息: <br>
     */
    private void sendToAll(SendInMailMsg sendMessage) {
        ChannelGroup channelGroup = NettyConfig.getChannelGroup();
        if (null != channelGroup) {
            channelGroup.writeAndFlush(new TextWebSocketFrame(sendMessage.getContentString()));
            saveMsgRecord(sendMessage);
        }
    }

    /**
     * 获取channelId: <br>
     *
     */
    private String getChannelId(String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(WebSocketConstant.BOSS_MSG_CHANNELID).append(userId);
        return sb.toString();
    }
}

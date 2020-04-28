package com.meng.game.card.websocket.inweb;

public class WebSocketConstant {
    /**
     * 发送的方式:1-一对一
     */
    public static final String SEND_TYPE_ONE_TO_ONE = "1";

    /**
     * 发送的方式:2-一对多
     */
    public static final String SEND_TYPE_ONE_TO_MANY = "2";

    /**
     * 发送的方式:3-群发
     */
    public static final String SEND_TYPE_GROUP = "3";

    /**
     * redis websocket 用户id前缀
     */
    public static final String BOSS_SOCKET_USER_ID_PREFIX = "websocket:userid:";

    /**
     * 执行方法：1-注册
     */
    public static final String EXEC_METHOD_LOGIN = "1";

    /**
     * 执行方法：2-发送消息
     */
    public static final String EXEC_METHOD_SEND_MSG = "2";

    /**
     * 执行方法：3-注销
     */
    public static final String EXEC_METHOD_LOGOUT = "3";

    /**
     * 消息id <br>
     */
    public static final String BOSS_MSG_CHANNELID = "msg:channel:";

    /**
     * 站内信前缀
     */
    public static final String INMAIL_MSG_PREFIX = "msg_";

    /**
     * 动作类型：1-登陆 <br>
     */
    public static final String INMAIL_ACTION_TYPE_LOGIN = "1";

    /**
     * 动作类型：2-发送消息<br>
     */
    public static final String INMAIL_ACTION_TYPE_SEND = "2";

    /**
     * 动作类型：3-标记消息已读 <br>
     */
    public static final String INMAIL_ACTION_TYPE_MARK = "3";

    /**
     * 动作类型：4-查询未读站内信 <br>
     */
    public static final String INMAIL_ACTION_TYPE_QUERY_UNREAD_MSG = "4";

    /**
     * 私有构造方法
     */
    private WebSocketConstant() {

    }
}

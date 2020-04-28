package com.meng.game.card.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyConfig {
    /**
     * 渠道组
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 私有构造方法
     */
    private NettyConfig() {

    }

    /**
     * 获取channel组
     */
    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}

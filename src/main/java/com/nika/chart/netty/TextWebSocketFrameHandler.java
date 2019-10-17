package com.nika.chart.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * WebSocketFrame 中定义的对应6种帧的类型:
 * BinaryWebSocketFrame	包含了二进制数据
 * TextWebSocketFrame 	   包含了文本数据
 * ContinuationWebSocketFrame 	包含属于上一个BinaryWebSocketFrame或TextWebSocketFrame 的文本数据或者二进制数据
 * CloseWebSocketFrame 	表示一个CLOSE 请求，包含一个关闭的状态码和关闭的原因
 * PingWebSocketFrame 	 请求传输一个PongWebSocketFrame
 * PongWebSocketFrame 	作为一个对于PingWebSocketFrame 的响应被发送
 *
 * @Date 0:12 2019/10/18
 * @Author Nika
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrameHandler> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrameHandler textWebSocketFrameHandler) throws Exception {

    }
}

package com.nika.chart.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
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
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new TextWebSocketFrame("[" + incoming.remoteAddress() + "]" + msg.text() + "\r\n"));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() + "\r\n"));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush(new TextWebSocketFrame("SERVER - " + incoming.remoteAddress() + "加入\r\n"));

        channels.add(incoming);
        System.out.println("Client：" + incoming.remoteAddress() + "加入\r\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channels.writeAndFlush(new TextWebSocketFrame("SERVER - " + incoming.remoteAddress() + "离开\r\n"));
        System.out.println("Client：" + incoming.remoteAddress() + "离开\r\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client：" + incoming.remoteAddress() + "在线\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client：" + incoming.remoteAddress() + "掉线\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client：" + incoming.remoteAddress() + "异常\r\n");
        cause.printStackTrace();
        ctx.close();
    }
}

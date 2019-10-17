package com.nika.chart.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Date 16:56 2019/10/17
 * @Author Nika
 */
public class ChartServer {
    @Value("$netty.port")
    private Integer port;

    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
    }
}

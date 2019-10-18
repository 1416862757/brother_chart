package com.nika.chart;

import com.nika.chart.netty.ChartServer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication
public class ChartApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(ChartApplication.class);

    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private ChartServer chartServer;

    @Order(value=1)
    public static void main(String[] args) {
        SpringApplication.run(ChartApplication.class, args);
    }

    @Bean
    public ChartServer chartServer(){
        return new ChartServer(port);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = new ChartServer(port).start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("【系统消息】：ChartServer 开始注销！");
            try {
                chartServer.destory();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        future.channel().closeFuture().syncUninterruptibly();
    }
}

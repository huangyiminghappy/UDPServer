package com.hqyg.udpserver.udp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

import com.hqyg.udpserver.udp.client.handler.UDPClientHandler;
import com.hqyg.udpserver.udp.helper.HandlerHelper;

 
public class UDPClient {
    private EventLoopGroup group;

    public void run(int port) throws Exception {
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UDPClientHandler());
            Channel ch = b.bind(0).sync().channel();
            ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(HandlerHelper.REQ_STR, CharsetUtil.UTF_8),
                    new InetSocketAddress("127.0.0.1", port))).sync();
            if (!ch.closeFuture().await(15000)) {
                System.out.println("查询超时！");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        // 初始化端口参数
        int port = 9001;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new UDPClient().run(port);
        // 服务端启动
    }
}
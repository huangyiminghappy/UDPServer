package com.hqyg.udpserver.udp.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import com.hqyg.udpserver.udp.server.handler.UDPServerHandler;

/**
 * udp server
 * 
 * 
 *
 */
public class UDPServer {

	private EventLoopGroup group;

	 
	public void run(int port) throws Exception {
 		group = new NioEventLoopGroup();
		try {
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true)
				/*.handler(new UDPServerHandler());*/
		 .handler(new ChannelInitializer<Channel>() {
 											@Override
											protected void initChannel(Channel ch)
													throws Exception {
												ch.pipeline().addLast(new UDPServerHandler());
		 									}
										});
			b.bind(port).sync().channel().closeFuture().await();
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
 		int port = 9001;
		if (args.length > 0) {
			try {
			port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		new UDPServer().run(port);
 	}

}

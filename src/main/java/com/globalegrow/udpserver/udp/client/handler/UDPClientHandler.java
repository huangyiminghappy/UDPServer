package com.globalegrow.udpserver.udp.client.handler;

import com.globalegrow.udpserver.udp.helper.HandlerHelper;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * udp客户端处理类
 * 
 *
 */
public class UDPClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		String resp = msg.content().toString(CharsetUtil.UTF_8);
		if (resp.startsWith(HandlerHelper.NOW_TIME)) {
			System.out.println(resp);
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}

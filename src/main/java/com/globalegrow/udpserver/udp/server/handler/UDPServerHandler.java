package com.globalegrow.udpserver.udp.server.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.globalegrow.udpserver.udp.helper.HandlerHelper;

/**
 * udp¥¶¿Ì¿‡
 * 
 *
 */
public class UDPServerHandler   extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
         String req = msg.content().toString(CharsetUtil.UTF_8);
        if (req.trim().equals(HandlerHelper.REQ_STR)) {
             System.out.println("receive req, from " + msg.sender().getHostName() + ":" + msg.sender().getPort());
            SimpleDateFormat sdf = new SimpleDateFormat(HandlerHelper.DATE_PATTERN);
            String dateStr = sdf.format(new Date());
            ctx.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(HandlerHelper.NOW_TIME + dateStr, CharsetUtil.UTF_8), msg.sender()));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

}
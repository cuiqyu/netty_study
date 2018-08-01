package com.limpid.introduction.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @auther cuiqiongyu
 * @create 2018/8/1 14:28
 */
public class ServerHandler2 extends ChannelInboundHandlerAdapter implements ChannelHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("handler2 ------" + msg);
        ctx.write("偶数");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

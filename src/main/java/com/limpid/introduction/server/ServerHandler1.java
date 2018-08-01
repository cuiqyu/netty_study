package com.limpid.introduction.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @auther cuiqiongyu
 * @create 2018/8/1 14:28
 */
public class ServerHandler1 extends ChannelInboundHandlerAdapter implements ChannelHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        int temp = (Integer) msg;
        if (temp % 2 == 0) {
            System.out.println("handler1 ------" + temp + "是偶数，继续调用下个handler");
            ctx.fireChannelRead(msg);
        } else {
            System.out.println("handler1 ------" + temp + "是奇数，直接返回");
            ctx.write("奇数");
        }
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

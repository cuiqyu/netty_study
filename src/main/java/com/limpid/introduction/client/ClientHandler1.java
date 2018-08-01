package com.limpid.introduction.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @auther cuiqiongyu
 * @create 2018/8/1 14:32
 */
public class ClientHandler1 extends ChannelInboundHandlerAdapter implements ChannelHandler {
    
    private final Integer msg = 33;
    private static int i = 0;

    /**
     * Creates a client-side handler.
     */
    public ClientHandler1() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        if (i < 10) {
            ctx.write(i++);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}

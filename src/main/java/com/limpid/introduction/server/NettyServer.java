package com.limpid.introduction.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * netty服务
 *
 * @auther cuiqiongyu
 * @create 2018/8/1 14:14
 */
public class NettyServer {

    // netty服务端启动
    public static void main(String[] args) throws Exception {
        /**
         * EventLoopGroup 从名字上看它肯定和netty的事件驱动有关。 EventLoopGroup是一个处理I/O操作的多线程事件环，更直白的说是一个线程池，它由一个或多个EventLoop来构成，是EventLoop的容器。
         * 第一个通常称为’boss’，用于接收发来的连接请求
         * 第二个称为’worker’，用于处理boss接受并且注册给worker的连接中的信息。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // NioServerSockerChannel：我的理解是netty的channel是对nio channel的封装，eventloop是对selector的封装 从AbstractNioChannel这个类中已经可以看到netty的channel是如何和socket 的nio channel关联的了，以及channel是如何和eventloop关联的。
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline(); // 是操作处理的入口，是ChannelHandler的管理者，提供了很多方法对handler进行操作。每个Channel都有一个对应的pipeline，每个pipeline有两个ChannelHandlerContext，分别包含默认的TailHandler和HeadHandler，并且它们构成一个双向链表结构。TailHandler处理inbound类型的数据；HeadHandler处理outbound类型的数据。利用ChannelHandlerContext上下文获取Handler，然后间接调用Handler的函数实现处理逻辑
                            p.addLast(new ObjectEncoder(), new ObjectDecoder(ClassResolvers.cacheDisabled(null)), new ServerHandler1());
                            p.addLast(new ServerHandler2());
                        }
                    });

            ChannelFuture f = bootstrap.bind(8007).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }


}

package com.limpid.three;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInboundHandler讲解以及需要注意的地方
 *
 * @auther cuiqiongyu
 * @create 2018/8/1 16:12
 */
public class SimpleChannelInboundHandlerExplain extends SimpleChannelInboundHandler<String> {

    /**
     * 请看文章 https://blog.csdn.net/linuu/article/details/51307060
     *
     * SimpleChannelInboundHandler重写的channelRead方法：
     *
     * @Override
     * public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
     *      boolean release = true;
     *      try {
     *          if (acceptInboundMessage(msg)) {
     *              @SuppressWarnings("unchecked")
     *              I imsg = (I) msg;
     *              channelRead0(ctx, imsg);
     *          } else {
     *              release = false;
     *              ctx.fireChannelRead(msg);
     *          }
     *      } finally {
     *          if (autoRelease && release) {
     *              ReferenceCountUtil.release(msg);
     *          }
     *      }
     * }
     *
     * 问题原因：
     *  原来我们SimpleChannelInboundHandler后面指定了处理类型，也就是源码中的"I",acceptInboundMessage方法判断msg是不是SimpleChannelInboundHandler中指定的类型
     *  我们这边指定的是ByteBuf，感觉没啥问题啊，但是我们忽略了一个问题，我们客户端中有3个处理器，两个inbound类型的处理器，其中一个就是HelloWorldClientHandler
     *  还有一个就是StringDecoder，此时我内心是崩溃的，尼玛，上一个处理器已经把服务器端的信息转化成String，我还用ByteBuf来接收，能处理才有鬼，修改一下我们的代码就可以了，重新指定一下处理的类型
     */

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Client channelRead0 received:" + msg);
    }

}

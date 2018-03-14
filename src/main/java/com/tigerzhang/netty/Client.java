package com.tigerzhang.netty;

import com.google.common.util.concurrent.Uninterruptibles;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by tigerzhang on 2018/3/14.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("localhost", 7777))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            for (int i = 0; i < 10000000; i++) {
                if (ctx.channel().isWritable()) {
                   ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));
                   future.addListener(future1 -> {
                       if (future1.isSuccess()) {                       } else {
                           System.out.println("write error");
                       }
                   });
                } else {
                    System.out.println("sleep" + i);
                    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                }
            }
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
            System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

    }

}

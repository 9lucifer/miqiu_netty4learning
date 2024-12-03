package netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author：丁浩然
 * @Package：netty.simple
 * @Project：miqiu-netty
 * @name：nettyServer
 * @Date：2024/12/4 1:10
 * @Filename：nettyServer
 * @Purpose：null
 */
public class nettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建两个group
        //创建了两个线程组
        //boss只是处理链接
        //worker处理业务
        //两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务端启动对象
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup,workerGroup)//设置两个线程组
                .channel(NioServerSocketChannel.class)//使用这个作为通道的实现
                .option(ChannelOption.SO_BACKLOG,128)//设置线程队列等待的链接个数
                .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动链接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道对象
                    //给pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);

                    }
                });//给workergroup的eventLoop设置处理器

        System.out.println("服务器is" +
                " ready");

        //绑定端口，生成这样一个对象
        //启动
        ChannelFuture cf = bootstrap.bind(6668).sync();

        //对关闭通道进行监听
        //异步模型
        cf.channel().closeFuture().sync();

        //todo hanlder还没写，45节课
    }
}

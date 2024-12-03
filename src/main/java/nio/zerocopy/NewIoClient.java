package nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.LoggingMXBean;

/**
 * @Author：丁浩然
 * @Package：nio.zerocopy
 * @Project：miqiu-netty
 * @name：NewIoClient
 * @Date：2024/12/3 9:58
 * @Filename：NewIoClient
 * @Purpose：null
 */
public class NewIoClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String filename = "protoc-3.6.1-win32.zip";
        //得到一个文件的channel
        FileChannel channel = new FileInputStream(filename).getChannel();
        //准备发送
        Long start = System.currentTimeMillis();
        //在Linux下的一个transto函数就可以
        //在windows下面一次只能传输8M的，所以需要分段，要注意传输的起始点
        //传输的位置
        //transderto底层用了零拷贝
        long count = channel.transferTo(0,channel.size(),socketChannel);
        System.out.println("发送的总的字节数："+count+ " 耗时："+(System.currentTimeMillis() - start));
        channel.close();

    }
}

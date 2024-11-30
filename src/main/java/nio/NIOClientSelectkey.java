package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：NIOClientSelectkey
 * @Date：2024/11/30 18:12
 * @Filename：NIOClientSelectkey
 * @Purpose：null
 */
public class NIOClientSelectkey {
    public static void main(String[] args) throws Exception{
        //get a net channel
        SocketChannel socketChannel = SocketChannel.open();

        //set nio mod
        socketChannel.configureBlocking(false);

        //give ip and port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);

        //connect
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("need time to connect,so I can do other work");
            }
        }

        //if success to connect
        String str = "hello lucifer";
        //wrap array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //send data,write data into channel
        socketChannel.write(buffer);
        System.in.read();

    }
}

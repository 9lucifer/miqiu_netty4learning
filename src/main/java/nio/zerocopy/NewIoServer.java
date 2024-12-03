package nio.zerocopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author：丁浩然
 * @Package：nio.zerocopy
 * @Project：miqiu-netty
 * @name：NewIoServer
 * @Date：2024/12/3 9:54
 * @Filename：NewIoServer
 * @Purpose：服务器
 */
public class NewIoServer {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress(7001);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        ServerSocket socket = serverSocketChannel.socket();

        socket.bind(address);

        ByteBuffer buffer = ByteBuffer.allocate(4096);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            int readcount = 0;
            while(-1!=readcount){

                try {
                    readcount = socketChannel.read(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //buffer倒带
                buffer.rewind();

            }
        }
    }
}

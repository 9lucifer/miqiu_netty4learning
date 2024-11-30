package nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：NIOServerSelectKey
 * @Date：2024/11/30 9:50
 * @Filename：NIOServerSelectKey
 * @Purpose：null
 */
public class NIOServerSelectKey {
    public static void main(String[] args) throws Exception {
        // 创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
// 获取 selector
        Selector selector = Selector.open();
// 绑定端口并监听
        serverSocketChannel.bind(new InetSocketAddress(6666));
// 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
// 将serverSocketChannel注册到selector，interest in accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //loop
        while (true){
            //no event,return
            if(selector.select(1000) == 0){
                System.out.println("server waits for 1s,no connection");
                continue;
            }
            //if > 0 , interested event has been got
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                SelectionKey selectionKey = keyIterator.next();
                if(selectionKey.isAcceptable()){
                    //client generate a socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //bind to a buffer
                    socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                }
                if(selectionKey.isReadable()){//read event
                    //get channel via key
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    //get buffer related to channel
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    socketChannel.read(buffer);
                    System.out.println("from client:"+new String(buffer.array()));
                }
                //delete key from current set
                keyIterator.remove();
            }
        }
    }
}

package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Author：丁浩然
 * @Package：nio.groupchat
 * @Project：miqiu-netty
 * @name：MyServer
 * @Date：2024/12/3 3:30
 * @Filename：MyServer
 * @Purpose：null
 */
public class MyServer {

    //定义属性
    private Selector selector;
        //做监听的
    private ServerSocketChannel listenChannel;
    public static final int Port = 6667;


    //构造器
    //初始化
    public MyServer(){

        try{
            //得到选择器
            selector = Selector.open();
            //
            listenChannel = ServerSocketChannel.open();
            //bind port
            listenChannel.socket().bind(new InetSocketAddress(Port));
            //set 非阻塞
            listenChannel.configureBlocking(false);
            //将listener注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void listen(){

        try {
            //循环
            while(true){
                int count = selector.select(2000);
                if(count>0){//event come
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectorkey
                        SelectionKey key = iterator.next();

                        //listened accept
                        if(key.isAcceptable()){

                            SocketChannel socketChannel = listenChannel.accept();
                            //将该sc注册到select上面
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);

                            System.out.println(socketChannel.getRemoteAddress()+"上线");
                        }

                        if(key.isReadable()){//channel is readable
                            //del reading
                            readData(key);
                        }


                        //delete current key
                        iterator.remove();
                    }
                }else {
                    System.out.println("waiting……");
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
        }
    }


    //读取客户端消息
    private void readData(SelectionKey key){
        //取到关联的channel
        SocketChannel socketChannel = null;

        try {
            socketChannel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);

            if(count>0){//得到数据
                String msg = new String(buffer.array());
                //输出
                System.out.println("from client："+msg);

                //向其他客户端转发消息
                sendInfoToOtherClients(msg,socketChannel);

            }

        } catch (IOException e) {
            try {
                System.out.println(socketChannel.getRemoteAddress()+"离线了");
                //取消注册
                key.cancel();
                //关闭通道
                socketChannel.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
        }

    }

    //转发消息给其他的通道
    private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
        //遍历所有注册到seleector上的socketchannel 排除self
        for (SelectionKey key : selector.keys()) {
            Channel target = key.channel();
            //排除自己
            if(target instanceof SocketChannel && target != self){
                SocketChannel dest = (SocketChannel) target;
                ByteBuffer wrap = ByteBuffer.wrap(msg.getBytes());
                //将数据写入通道
                dest.write(wrap);

            }


        }
    }

    public static void main(String[] args) {
        //创建一个服务器
        MyServer myServer = new MyServer();
        myServer.listen();
    }
}

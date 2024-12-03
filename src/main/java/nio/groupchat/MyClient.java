package nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author：丁浩然
 * @Package：nio.groupchat
 * @Project：miqiu-netty
 * @name：Client
 * @Date：2024/12/3 8:09
 * @Filename：Client
 * @Purpose：null
 */
public class MyClient {
    //定义相关的属性
    public static final String Host = "127.0.0.1";
    public static int  Port = 6667;

    private Selector selector;

    private SocketChannel socketChannel;

    private String username;

    //构造器
    public MyClient() throws IOException {
        //初始化
        selector = Selector.open();

        //连接服务器
        socketChannel = socketChannel.open(new InetSocketAddress(Host,Port));

        socketChannel.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_READ);

        username = socketChannel.getLocalAddress().toString().substring(1);

        System.out.println(username+" is ok");
    }

    public void sendInfo(String info){
        info = username + "说：" +info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }


    public void readInfo(){

        try {
            int readChannels = selector.select(2000);

            if(readChannels>0){//有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if(key.isReadable()){
                        //得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        //读取
                        sc.read(buffer);
                        //把缓冲区的数据转成字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                }
                iterator.remove();

            }else {
                //System.out.println("没有可用的通道");

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }

    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        MyClient myClient = new MyClient();

        //启动一个线程，每隔三秒读取数据
        new Thread(){
            @Override
            public void run(){
                while (true){
                    myClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        //发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            myClient.sendInfo(s);
        }
    }

}

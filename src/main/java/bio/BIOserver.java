package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author：丁浩然
 * @Package：bio
 * @Project：miqiu-netty
 * @name：BIOserver
 * @Date：2024/11/14 9:47
 * @Filename：BIOserver
 * @Purpose：null
 */
public class BIOserver {
    public static void main(String[] args) throws Exception{

        //先创建一个线程池等待连接
        //监听到请求就做出应答

        ExecutorService ThreadPool = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        while (true){
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            ThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //调用处理的函数
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }


    }

    //客户端方法
    public static void handler(Socket socket) throws IOException {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true){
                int read = inputStream.read(bytes);
                if (read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            socket.close();
        }
    }
}


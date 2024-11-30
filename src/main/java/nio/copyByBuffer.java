package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：copyByBuffer
 * @Date：2024/11/17 16:43
 * @Filename：copyByBuffer
 * @Purpose：null
 */
public class copyByBuffer {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("file01.txt");
        FileChannel channel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("file02.txt");
        FileChannel channel02 = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        while (true){
            //重要的操作：对buffer做清空的操作
            buffer.clear();
            int read = channel01.read(buffer);
            if(read == -1){
                break;
            }
            //将buffer里面的数据写入
            buffer.flip();
            channel02.write(buffer);
        }
        //关相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}

package nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：NIOFileChannel
 * @Date：2024/11/17 15:16
 * @Filename：NIOFileChannel
 * @Purpose：null
 */
public class NIOFileChannel {
    public static void main(String[] args) throws IOException {

        String str = "hello lucifer";

        FileOutputStream fileOutputStream = new FileOutputStream("file01.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}

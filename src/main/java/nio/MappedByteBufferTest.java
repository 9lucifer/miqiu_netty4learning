package nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：MappedByteBufferTest
 * @Date：2024/11/17 18:30
 * @Filename：MappedByteBufferTest
 * @Purpose：null
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("file01.txt","rw");

        FileChannel channel = randomAccessFile.getChannel();


        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE,0,5);

        mappedByteBuffer.put(0,(byte) '3');
        mappedByteBuffer.put(1,(byte) '3');
        mappedByteBuffer.put(3,(byte) '3');


        randomAccessFile.close();
    }
}

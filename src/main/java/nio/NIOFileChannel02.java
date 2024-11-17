package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：NIOFileChannel02
 * @Date：2024/11/17 16:36
 * @Filename：NIOFileChannel02
 * @Purpose：null
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception {
        //创建文件的输入流
        File file = new File("file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        //通过输入流获取对应的channel
        FileChannel fileChannel = fileInputStream.getChannel();


        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());

        //将缓冲区的数据写入到buffer里面
        fileChannel.read(byteBuffer);

        //打印Buffer里面的数据
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();


    }
}

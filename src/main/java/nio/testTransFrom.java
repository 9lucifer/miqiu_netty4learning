package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：testTransFrom
 * @Date：2024/11/17 16:54
 * @Filename：testTransFrom
 * @Purpose：null
 */
public class testTransFrom {
    public static void main(String[] args) throws Exception{
        //获取输入输出流对象
        FileInputStream fileInputStream = new FileInputStream("01.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("02.jpg");

        //获取流对应的通道
        FileChannel sourcech = fileInputStream.getChannel();
        FileChannel descch = fileOutputStream.getChannel();

        //使用transfrom完成拷贝
        descch.transferFrom(sourcech,0,sourcech.size());

        fileOutputStream.close();
        fileInputStream.close();
    }
}

package nio;

import java.nio.IntBuffer;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：basicBuffer
 * @Date：2024/11/17 12:23
 * @Filename：basicBuffer
 * @Purpose：null
 */
public class basicBuffer {
    public static void main(String[] args) {
        //举例说明buffer的使用
        IntBuffer intBuffer = IntBuffer.allocate(5);
        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);

        //从buffer里取出数据
        //这个方法的作用是读写转换!!!!!

        intBuffer.flip();
        intBuffer.position(3);//在第三个开始读
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}

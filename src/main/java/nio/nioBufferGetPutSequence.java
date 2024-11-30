package nio;

import java.nio.ByteBuffer;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：nioBufferGetPutSequence
 * @Date：2024/11/17 17:04
 * @Filename：nioBufferGetPutSequence
 * @Purpose：null
 */
public class nioBufferGetPutSequence {
//读写的类型的顺序是不可以写错的，否则会取到内存之外的值
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(5);
        buffer.putShort((short) 5);
        buffer.putChar('h');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}

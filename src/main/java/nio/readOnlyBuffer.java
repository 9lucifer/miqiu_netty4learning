package nio;

import java.nio.ByteBuffer;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：readOnlyBuffer
 * @Date：2024/11/17 17:14
 * @Filename：readOnlyBuffer
 * @Purpose：null
 */
public class readOnlyBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(5);
        buffer.putShort((short) 5);
        buffer.putChar('h');

        buffer.flip();
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}

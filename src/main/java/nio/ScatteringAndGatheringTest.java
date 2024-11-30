package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author：丁浩然
 * @Package：nio
 * @Project：miqiu-netty
 * @name：ScatteringAndGetteringTest
 * @Date：2024/11/30 0:23
 * @Filename：ScatteringAndGetteringTest
 * @Purpose：演示 Scattering 和 Gathering 操作
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        // serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7001);

        // 绑定端口并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        // create buffer arrays
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);

        System.out.println("等待客户端连接...");

        // 等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("客户端已连接，开始读取数据...");

        int messageLength = 8; // 消息长度

        // read in a loop
        while (true) {
            long byteRead = 0;
            while (byteRead < messageLength) {
                // 读取数据到buffers
                long bytes = socketChannel.read(buffers);
                if (bytes == -1) {
                    break; // 如果读取到文件结束标志，则跳出
                }
                byteRead += bytes;

                System.out.println("读取到 " + byteRead + " 字节");

                // 使用流打印 buffers 状态
                Arrays.asList(buffers).stream().map(buffer ->
                        "position:" + buffer.position() +
                                ", limit:" + buffer.limit()).forEach(System.out::println);
            }

            // 将所有的buffer翻转
            Arrays.asList(buffers).forEach(ByteBuffer::flip);

            long byteWrite = 0;
            while (byteWrite < messageLength) {
                // 将数据回写到客户端
                long bytes = socketChannel.write(buffers);
                byteWrite += bytes;
            }

            // 将所有的buffer清除
            Arrays.asList(buffers).forEach(ByteBuffer::clear);

            System.out.println("byteRead: " + byteRead + " byteWrite: " + byteWrite);
        }
    }
}

package org.lzhen.demo02;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

public class UDPProvider {

    public static void main(String[] args) throws Exception {
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();

        // 收到键盘消息退出
        System.in.read();
        provider.exist();

    }

    public static class Provider extends Thread {
        private String sn;
        private boolean flag = false;
        private DatagramSocket ds = null;

        Provider(String sn) {
            this.sn = sn;
        }

        @Override
        public void run() {
            super.run();

            System.out.println("UDPProvider Started.");
            try {
                // 作为接收者，指定端口用于数据接收
                ds = new DatagramSocket(20000);

                while (!flag) {
                    // 接收实体
                    final byte[] buf = new byte[512];
                    DatagramPacket receivePack = new DatagramPacket(buf, 0, buf.length);
                    // 接收
                    ds.receive(receivePack);
                    System.out.println("UDPProvider 接收成功。。。");
                    // 发送者ip，port
                    String ip = receivePack.getAddress().getHostAddress();
                    int port = receivePack.getPort();
                    // 打印接收到的信息
                    String data = new String(receivePack.getData(), 0, receivePack.getLength(), "utf-8");
                    System.out.println("UDPProvider receive form ip:" + ip + "\tport:" + port + "\tdata:" + data);

                    // 解析回送端口
                    int responsePort = MessageCreator.parsePort(data);
                    if (-1 != responsePort) {
                        // 构建回送数据
                        String responseData = MessageCreator.buidWithSn(sn);
                        DatagramPacket responsePacket = new DatagramPacket(responseData.getBytes(), responseData.getBytes().length,
                                receivePack.getAddress(), responsePort);
                        // 发送
                        ds.send(responsePacket);
                        System.out.println("UDPProvider 发送成功。。。");
                    }
                }
            } catch (Exception e) {
            } finally {
                close();
            }
            // 完成
            System.out.println("UDPProvider Finished.");
        }

        // 结束循环
        private void exist() {
            flag = true;
            close();
        }

        // 释放资源
        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }
    }

}

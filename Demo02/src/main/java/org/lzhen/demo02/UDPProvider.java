package org.lzhen.demo02;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPProvider {

    public static void main(String[] args) throws Exception {
        System.out.println("UDPSearcher Started.");

        DatagramSocket ds = new DatagramSocket(20000);

        // 接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, 0, buf.length);
        // 接收
        ds.receive(receivePack);

        // 打印接收到的信息，发送至的信息
        String data = new String(receivePack.getData(),0, receivePack.getLength(),"utf-8");
        System.out.println("UDPProvider receive form ip:" + receivePack.getAddress().getHostAddress()
                + "\tport:" + receivePack.getPort() + "\tdata:" + data);

        // 会送
        DatagramPacket responsePacket = new DatagramPacket("收到".getBytes(), "收到".getBytes().length, receivePack.getAddress(), receivePack.getPort());
        ds.send(responsePacket);

        // 完成
        System.out.println("UDPProvider Finished.");

        ds.close();
    }
}

package org.lzhen.demo02;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSearcher {
    public static void main(String[] args) throws Exception {
        System.out.println("UDPSearcher sendBroadcast started.");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        // 构建一份请求数据
        String requestData = MessageCreator.buidWithPort(20000);
        byte[] requestDataByte = requestData.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(requestDataByte, requestDataByte.length);

        // 本机20000端口
        requestPacket.setAddress(InetAddress.getLocalHost());
        requestPacket.setPort(20000);

        // 发送
        ds.send(requestPacket);
        System.out.println("UDPSearcher 发送成功。。。");

        // 接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
        // 接收
        ds.receive(receivePack);
        System.out.println("UDPSearcher 接收成功。。。");

        // 打印接收到的信息，发送至的信息
        String data = new String(receivePack.getData(),0, receivePack.getLength(),"utf-8");
        System.out.println("UDPSearcher receive form ip:" + receivePack.getAddress().getHostAddress()
                + "\tport:" + receivePack.getPort() + "\tdata:" + data);


        // 完成
        System.out.println("UDPSearcher Finished.");
        ds.close();

    }
}

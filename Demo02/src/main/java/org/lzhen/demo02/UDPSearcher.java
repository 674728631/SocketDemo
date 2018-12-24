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
        String requestData = "nishishame";
        byte[] requestDataByte = requestData.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(requestDataByte, requestDataByte.length);

        // 20000端口, 广播地址
        requestPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        requestPacket.setPort(20000);

        // 发送
        ds.send(requestPacket);
        ds.close();
        // 完成
        System.out.println("UDPSearcher sendBroadcast finished.");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L5_UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author OS
 */
public class UDPClient {
    public static void main(String[] args) {
        //1. gửi chuỗi lên server
        DatagramSocket client = new DatagramSocketocket();
        byte[] data = "ptit client xyz udp".getBytes();
        DatagramPacket dpSend = new DatagramPacket(data, data.length, InetAddress.getByName(), 0);
        client.send(dpsend);
        //2. Nhận về chuỗi đã được đao ngược
    }
}

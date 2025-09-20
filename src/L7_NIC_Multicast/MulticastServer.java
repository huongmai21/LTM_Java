/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L7_NIC_Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author OS
 */
public class MulticastServer {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, InterruptedException {
        DatagramSocket socket = new DatagramSocket();
        System.out.println("Server start send...");
        int i = 0;
        while (true) {
            String strMessage = "message " + i;
            byte[] data = strMessage.getBytes();
            
            // gửi tới multicast group 224.2.2.3 cổng 2207
            DatagramPacket dp = new DatagramPacket(
                data, data.length, InetAddress.getByName("224.2.2.3"), 2207
            );
            
            socket.send(dp);
            System.out.println("Sent: " + strMessage);
            
            Thread.sleep(2000); // nghỉ 2s trước khi gửi tiếp
            i++;
        }
    }
}

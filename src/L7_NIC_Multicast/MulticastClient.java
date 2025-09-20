/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L7_NIC_Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author OS
 */
public class MulticastClient {
    public static void main(String[] args) throws IOException {
        MulticastSocket client = new MulticastSocket(2207);
        client.joinGroup(InetAddress.getByName("224.2.2.3"));
        while(true){
            byte[] buf = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            client.receive(dp);
            String strReceive = new String(dp.getData()).trim();
            System.out.println("receive: " +strReceive);
        }
    }
}

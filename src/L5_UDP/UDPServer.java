/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L5_UDP;

import java.util.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author OS
 */
public class UDPServer {
    public static void main(String[] args) throws SocketException, IOException {
        
        DatagramSocket server = new DatagramSocket(2207);
        System.out.println("UDP server start listening...");
        // luoon lang nghe
        while(true){
            // nhaanj chuoi can xuwr lys
            byte[] buf = new byte[1024];
            DatagramPacket dpReceive = new DatagramPacket(buf, buf.length);
            server.receive(dpReceive);
            String strReceive = dpReceive.getData();
            System.out.println("receive string "+strReceive);
            
            //dao nguoc chuoi va tra ve client
            String strReverse = new StringBuilder();
            DatagramPacket dpRespone = new DatagramPacket(strReverse.getBytes(), strReverse.getBytes().length,
                                                            dpReceive.getAddress(), dpReceive.getPort());
            server.send(dpRespone);
            
            System.out.println(""+ strReceive);
       }
        

        
        
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L4_TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("www.google.com",80);
        System.out.println(client);
//        client.getInputStream();
        // nhan 2 so nguyen a,b
        DataInputStream dis = new DataInputStream(client.getInputStream());
        int a = dis.readInt();
        int b = dis.readInt();
        System.out.format("receive: {a: %d, b: %d\n}", a,b);
        // Tinh tong 
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeInt(a+b);
        
        dis.close();
        dos.close();
        client.close();
    }
}

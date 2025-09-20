/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L4_TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2207);
        System.out.println("TCP start listening...");
        while(true){
            Socket conn = server.accept();
            System.out.println("a new connection " + conn);
            
            // gui 2 gia tri so nguyen ve cho client
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream()); // gui dl nguyen thuy, tuan tu
            
            dos.write((int)Math.random()*100);
            dos.write((int)Math.random()*100);
            
            // nhan kq
            DataInputStream dis = new DataInputStream(conn.getInputStream());
            int sum = dis.readInt();
            System.out.println("sum from client" + sum);
            
            // thuwj hieenj song song
//            while(true){
//                Socket conn = server.accept();
//                new clientHandler(conn.start);
//            }
            conn.close();
        }
    }
}

class clientHandler extends Thread{
    Socket  conn;

    public clientHandler(Socket conn) {
        
    }
    
    
}
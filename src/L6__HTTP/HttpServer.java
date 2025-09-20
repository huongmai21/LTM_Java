/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L6__HTTP;

//import 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

        
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket httpServer = new ServerSocket(8080);
        System.out.println("running...");
        while(true){
            Socket httpRequest = httpServer.accept();
            System.out.println("http request: "+ httpRequest);
            //http request
            BufferedReader br = new BufferedReader(new InputStreamReader(httpRequest.getInputStream()));
            
            String line = br.readLine();
            while (!line.isEmpty() && line!=null) {
                System.out.println(line);
                line = br.readLine();
            }
            
            // http respone
            OutputStream os = httpRequest.getOutputStream();
            String strRespone = "HTTP/1.1 200 OK \r\n\r\n";
            Date today = new Date();
            
            os.write((strRespone+today).getBytes());
            
            //close
            br.close();
            httpRequest.close();
        }
    }
}

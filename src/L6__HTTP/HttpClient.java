/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L6__HTTP;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class HttpClient {
    public static void main(String[] args) throws IOException {
        Socket httpClient = new Socket("localhost",8080);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(httpClient.getOutputStream()));
        String strRequest = "GET localhost HTTP/1.1 \r\n\r\n"
                + "User-Agent: HTTPClientPTIT 1.0 \r\n"
                + "Host: www.ptit.edu.vn\r\n\r\n";
        bw.write(strRequest);
        bw.flush(); // để đẩy dl đi
        InputStream is = httpClient.getInputStream();
        byte[] b = new byte[1024];
        while(is.read(b)!=-1){
            System.out.println(new String(b));
        }
        
        //close
        bw.close();
        is.close();
        httpClient.close();
    }
}

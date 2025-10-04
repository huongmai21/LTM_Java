/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class UDPClient_JQCO3izC {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        // a. Gửi request
        String request = ";B22DCCN424;JQCO3izC";
        byte[] data = request.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
        socket.send(packet);

        // b. Nhận dữ liệu
        byte[] buf = new byte[4096];
        DatagramPacket response = new DatagramPacket(buf, buf.length);
        socket.receive(response);

        String received = new String(response.getData(), 0, response.getLength());
        System.out.println("Nhận: " + received);

        String[] parts = received.split(";", 3);
        String reqId = parts[0];
        String str1 = parts[1];
        String str2 = parts[2];

        // c. Xử lý hiệu chuỗi
        Set<Character> set = new HashSet<>();
        for (char c : str2.toCharArray()) {
            set.add(c);
        }

        StringBuilder resultStr = new StringBuilder();
        for (char c : str1.toCharArray()) {
            if (!set.contains(c)) {
                resultStr.append(c);
            }
        }

        String result = reqId + ";" + resultStr.toString();
        System.out.println("Gửi: " + result);

        // d. Gửi lại kết quả
        byte[] sendData = result.getBytes();
        DatagramPacket outPacket = new DatagramPacket(sendData, sendData.length, host, port);
        socket.send(outPacket);

        socket.close();
    }
}


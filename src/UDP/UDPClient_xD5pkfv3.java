/*
[Mã câu hỏi (qCode): xD5pkfv3].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
b.	Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50” 
-	requestId là chuỗi ngẫu nhiên duy nhất
-	a1 -> a50 là 50 số nguyên ngẫu nhiên
c.	Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông điệp lên lên server theo định dạng “requestId;max,min”
d.	Đóng socket và kết thúc chương trình
*/
package UDP;

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
public class UDPClient_xD5pkfv3 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        //Tạo socket
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        // gửi request
        String request = ";B22DCCN424;xD5pkfv3";
        byte[] sendReq = request.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(sendPacket);
        
        //Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận: "+ data);
        
        //Xử lý
        String[] part = data.split(";");
        String reqId = part[0];
        String[] nums = part[1].split("\\,");
        
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for(String x : nums){
            int n = Integer.parseInt(x);
            if(n < min) min = n;
            if(n>max) max = n;
        }
        
        String res = reqId+ ";" + max + "," + min;
        
        // Gửi kq
        byte[] outData = res.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, host, port);
        System.out.println("Kết quả: " + res);
        socket.send(outPacket);
        
        socket.close();
    }
}

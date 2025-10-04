/*
BÀI 1. TÌM SỐ CÒN THIẾU[002] 
[Mã câu hỏi (qCode): wAKCZwjj].  Một chương trình server cho phép giao tiếp qua giao thức UDP 
tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch 
bản: 
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
“;studentCode;qCode”. Ví dụ: “;B15DCCN001;73457A17” 
b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;n;A1,A2,...An” , với - requestId là chuỗi ngẫu nhiên duy nhất - n là một số ngẫu nhiên nhỏ hơn 100. -            A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau. 
Ex: requestId;10;2,3,5,6,5 
c. Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng “requestId;B1,B2,...,Bm” 
Ex: requestId;1,4,7,8,9,10 
d. Đóng socket và kết thúc chương trình.  
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 *
 * @author OS
 */
public class UDPClient_wAKCZwjj {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;

        // a. Gửi request
        String request = ";B22DCCN424;wAKCZwjj";
        socket.send(new DatagramPacket(request.getBytes(), request.length(), host, port));

        // b. Nhận dữ liệu
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);

        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận: " + data);

        String[] part = data.split(";");
        String reqId = part[0];
        int n = Integer.parseInt(part[1]);
        String[] nums = part[2].split(",");

         // Đếm tần suất các số
        int[] cnt = new int[n+1];
        for (String x : nums) {
            int val = Integer.parseInt(x.trim());
            if (val <= n) cnt[val]++;
        }

        // c. Tìm số còn thiếu
        StringBuilder sb = new StringBuilder(reqId + ";");
        for (int i = 1; i <= n; i++) {
            if (cnt[i] == 0) sb.append(i).append(",");
        }
        if (sb.charAt(sb.length()-1) == ',') sb.deleteCharAt(sb.length()-1);


        // c. Gửi lại kết quả
        String res = sb.toString();
        System.out.println("Gửi: " + res);
        socket.send(new DatagramPacket(res.getBytes(), res.length(), host, port));

        socket.close();
    }
}

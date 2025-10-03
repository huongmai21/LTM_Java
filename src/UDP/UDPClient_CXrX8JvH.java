/*
[Mã câu hỏi (qCode): CXrX8JvH].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: ";B15DCCN009;EF56GH78"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;data", với:
•	requestId là chuỗi ngẫu nhiên duy nhất.
•	data là một chuỗi ký tự chứa nhiều từ, được phân cách bởi dấu cách.
Ví dụ: "EF56GH78;The quick brown fox"
c. Sắp xếp các từ trong chuỗi theo thứ tự từ điển ngược (z đến a) và gửi thông điệp lên server theo định dạng "requestId;word1,word2,...,wordN".
Ví dụ: Với data = "The quick brown fox", kết quả là: "EF56GH78;quick,fox,brown,The"
d. Đóng socket và kết thúc chương trình
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author OS
 */
public class UDPClient_CXrX8JvH {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String request = ";B22DCCN424;CXrX8JvH";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        // Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận: "+ data);
        String[] part = data.split(";");
        String reqId = part[0];
        String[] s = part[1].split("\\s+");
        
        Arrays.sort(s, Collections.reverseOrder());
        String res = reqId + ";" + String.join(",",s);
        System.out.println("KQ: " + res);
        byte[] outData = res.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, host, port);
        socket.send(outPacket);
        
        socket.close();
        
        
    }
}

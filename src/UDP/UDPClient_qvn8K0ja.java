/*
[Mã câu hỏi (qCode): qvn8K0ja].  Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;5B35BCC1”
b.	Nhận thông điệp từ server theo định dạng “requestId;data” 
-	requestId là một chuỗi ngẫu nhiên duy nhất
-	data là chuỗi dữ liệu cần xử lý
c.	Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc 
i.	Ký tự đầu tiên của từng từ trong chuỗi là in hoa
ii.	Các ký tự còn lại của chuỗi là in thường
Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng “requestId;data”
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
public class UDPClient_qvn8K0ja {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String request = ";B22DCCN424;qvn8K0ja";
        byte[] sendReq = request.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(sendPacket);
        System.out.println("Đã gửi request");
        
        // Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
        System.out.println("Nhận dl: " + data);
        
        // Xu ly
        String[] part = data.split(";");
        String reqId = part[0];
        String[] s = part[1].split("\\s+");
        
        StringBuilder sb = new StringBuilder("");
        for(String x : s){
            sb.append(Character.toUpperCase(x.charAt(0)));
            for(int i= 1;i<x.length();++i){
                sb.append(Character.toLowerCase(x.charAt(i)));
            }
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        String res = reqId + ";" + sb.toString();
        
        byte[] outData = res.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, host, port);
        socket.send(outPacket);
        System.out.println("Gửi Kq: " + res);
        
        socket.close();
    }
}

/*
[Mã câu hỏi (qCode): AyjraDg3]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. 
Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: 
Đầu tiên, client gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”, ví dụ “;B21DCCN554;AyjraDg3”. 
Sau đó, client nhận thông điệp phản hồi từ server, trong đó 08 byte đầu tiên là chuỗi requestId, 
các byte còn lại chứa dữ liệu có dạng “requestId;chuỗiKýTự;sốNguyên”. 
Tiếp theo, client cần xử lý chuỗi ký tự bằng cách dịch vòng mỗi ký tự theo số nguyên nhận được: 
nếu là chữ thường thì dịch trong bảng chữ cái thường, nếu là chữ hoa thì dịch trong bảng chữ cái hoa, theo công thức (c’ = (c – base + sốNguyên) mod 26 + base). 
Ví dụ, với chuỗi “abc” và số nguyên 2 sẽ thu được “cde”, hoặc “XYZ” với số nguyên 3 sẽ thu được “ABC”. Cuối cùng, client gửi lại kết quả về server theo định dạng “requestId;chuỗiSauKhiMãHóa”, 
sau đó đóng socket và kết thúc chương trình. 
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
public class UDPClient_AyjraDg3 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        String request = ";B22DCCN424;AyjraDg3";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        // Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận: "+data);
        String[] part = data.split(";");
        String reqId = part[0];
        String s = part[1];
        int num = Integer.parseInt(part[2]);
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                sb.append((char) ((s.charAt(i) - 'a' + num) % 26 + 'a'));
            } else {
                sb.append((char) ((s.charAt(i) - 'A' + num) % 26 + 'A'));
            }
        }
         
        String res = reqId + ";" + sb.toString();
        System.out.println("KQ: "+res);
        byte[] outData = res.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, host, port);
        socket.send(outPacket);
        
        socket.close();
    }
}

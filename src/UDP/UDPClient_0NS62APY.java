/*
[Mã câu hỏi (qCode): 0NS62APY].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode".
Ví dụ: ";B15DCCN010;D3F9A7B8"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;a;b", với:
•	requestId là chuỗi ngẫu nhiên duy nhất.
•	a và b là chuỗi thể hiện hai số nguyên lớn (hơn hoặc bằng 10 chữ số).
Ví dụ: "X1Y2Z3;9876543210;123456789"
c. Tính tổng và hiệu của hai số a và b, gửi thông điệp lên server theo định dạng "requestId;sum;difference".Ví dụ: 
Nếu nhận được "X1Y2Z3;9876543210,123456789", tổng là 9999999999 và hiệu là 9753086421. Kết quả gửi lại sẽ là "X1Y2Z3;9999999999,9753086421".
d. Đóng socket và kết thúc chương trình
*/
package UDP;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author OS
 */
public class UDPClient_0NS62APY {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        String request = ";B22DCCN424;0NS62APY";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        //Nhân dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
        System.out.println("Nhận: "+data);
        String[] part = data.split(";");
        String reqId = part[0];
        BigInteger a = new BigInteger(part[1]);
        BigInteger b = new BigInteger(part[2]);
        
        BigInteger sum = a.add(b);
        BigInteger diff = a.subtract(b);
        
        String res = reqId +";" + sum + "," +diff;
        System.out.println("Kq: " + res);
        byte[] outData = res.getBytes();
        
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, host, port);
        socket.send(outPacket);
        
        socket.close();
    }
}

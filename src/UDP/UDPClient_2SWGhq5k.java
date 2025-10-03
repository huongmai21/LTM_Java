/*
[Mã câu hỏi (qCode): 2SWGhq5k].  
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN004;99D9F604”
b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;z1,z2,...,z50” requestId là chuỗi ngẫu nhiên duy nhất
    z1 -> z50 là 50 số nguyên ngẫu nhiên
    c. Thực hiện tính số lớn thứ hai và số nhỏ thứ hai của thông điệp trong z1 -> z50 và gửi thông điệp lên server theo định dạng “requestId;secondMax,secondMin”
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

/**
 *
 * @author OS
 */
public class UDPClient_2SWGhq5k {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;
        
        String request = ";B22DCCN424;2SWGhq5k";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        // Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận dl: " +data);
        String[] part = data.split(";");
        String reqId = part[0];
        String[] s = part[1].split("\\,");
        
        int[] arr = new int[s.length];
        int n = s.length;
        for(int i = 0;i<n;++i){
            arr[i] = Integer.parseInt(s[i]);
        }
        
        Arrays.sort(arr); //sx tăng dần
        int seMin = arr[0], seMax = arr[n-1];
        for(int i=1;i<n;++i){
            if(arr[i] != seMin){
                seMin = arr[i];
                break;
            }
        }
        
        for(int i= n-2;i>=0;i--){
            if(arr[i] != seMax){
                seMax = arr[i];
                break;
            }
        }
        
        //Gửi kq
        String res = reqId + ";" + seMax + "," + seMin;
        System.out.println("Kq: " + res);
        byte[] sendRes = res.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendRes, sendRes.length, host, port);
        socket.send(sendPacket);
        
        socket.close();
    }
}

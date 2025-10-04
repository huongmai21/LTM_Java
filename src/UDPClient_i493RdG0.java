
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/*
[Mã câu hỏi (qCode): i493RdG0]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B21DCCN554;i493RdG0”.

b. Nhận thông điệp phản hồi từ server. Nội dung thông điệp gồm: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa dữ liệu có dạng “requestId;chuỗiKýTự”.

c. Thực hiện: với chuỗi ký tự nhận được, cần đếm số lần xuất hiện của từng ký tự theo đúng thứ tự xuất hiện ban đầu, sau đó tạo ra chuỗi mới theo định dạng “sốLầnXuấtHiện + kýTự”. Ví dụ: nếu nhận “aabac” thì kết quả sẽ là “3a1b1c”.

d. Gửi lại kết quả xử lý về server theo định dạng “requestId;chuỗiKếtQuả”.

e. Đóng socket và kết thúc chương trình. 
*/

/**
 *
 * @author OS
 */
public class UDPClient_i493RdG0 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;
        
        String request = ";B22DCCN424;i493RdG0";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        // Nhận dl 
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
        System.out.println("Nhận: "+data);
        String[] part = data.split(";");
        String reqId = part[0];
        String s = part[1];
        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0;i<s.length();++i){
            if(map.containsKey(s.charAt(i))){
                map.put(s.charAt(i), map.get(s.charAt(i)+1));
            }
            else{
                map.put(s.charAt(i), 1);
            }
        }
        String ans = "";
        for(int i=0;i<s.length();++i){
            if(map.get(s.charAt(i)) > 0){
                ans += String.valueOf(map.get(s.charAt(i))) + s.charAt(i);
                map.put(s.charAt(i), 0);
            }
        }
        
        String res = reqId + ";" + ans;
        System.out.println("KQ: "+res);
        byte[] outData = res.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, host, port);
        socket.send(outPacket);
        
        socket.close();
    }
}

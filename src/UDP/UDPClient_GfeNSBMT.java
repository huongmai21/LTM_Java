/*
BÀI 3. LỌC KÝ TỰ [010] 
[Mã câu hỏi (qCode): GfeNSBMT].  [Loại bỏ ký tự đặc biệt, số, trùng và giữ nguyên thứ tự xuất 
hiện] 
Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng 
một chương trình client trao đổi thông tin với server theo kịch bản dưới đây: 
a. 
Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
";studentCode;qCode". Ví dụ: ";B15DCCN001;06D6800D" 
b. 
Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strInput" 
• requestId là chuỗi ngẫu nhiên duy nhất 
• strInput là chuỗi thông điệp cần xử lý 
c. 
Thực hiện loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của chúng. 
Gửi thông điệp lên server theo định dạng "requestId;strOutput", trong đó strOutput là chuỗi đã được 
xử lý ở trên 
d. 
Đóng socket và kết thúc chương trình
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author OS
 */
public class UDPClient_GfeNSBMT {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        // a. Gửi request
        String request = ";B22DCCN424;GfeNSBMT";
        byte[] data = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(data, data.length, host, port);
        socket.send(reqPacket);

        // b. Nhận dữ liệu
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận: " + received);

        String[] parts = received.split(";", 2);
        String reqId = parts[0];
        String strInput = parts[1];

        // c. Xử lý: loại bỏ số, ký tự đặc biệt, ký tự trùng
        StringBuilder sb = new StringBuilder();
        Set<Character> seen = new HashSet<>();

        for (char ch : strInput.toCharArray()) {
            if (Character.isLetter(ch)) { // chỉ giữ chữ cái
                if (!seen.contains(ch)) {
                    sb.append(ch);
                    seen.add(ch);
                }
            }
        }

        String strOutput = sb.toString();

        // d. Gửi lại kết quả
        String result = reqId + ";" + strOutput;
        System.out.println("Gửi: " + result);

        byte[] sendData = result.getBytes();
        DatagramPacket outPacket = new DatagramPacket(sendData, sendData.length, host, port);
        socket.send(outPacket);

        socket.close();
    }
}

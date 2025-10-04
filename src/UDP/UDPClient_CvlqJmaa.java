/*
BÀI 5. KÝ TỰ XUẤT HIỆN NHIỀU LẦN NHẤT[011] 
[Mã câu hỏi (qCode): CvlqJmaa].  Một chương trình server cho phép kết nối qua giao thức UDP 
tại cổng 2208. Yêu cầu là xây dựng một chương trình client tương tác với server kịch bản dưới đây: 
a. 
Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
“;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059” 
b. 
Nhận thông điệp từ server theo định dạng “requestId; data”  - - 
requestId là một chuỗi ngẫu nhiên duy nhất 
data là chuỗi dữ liệu đầu vào cần xử lý 
Ex: “requestId;Qnc8d5x78aldSGWWmaAAjyg3” 
c. 
Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng 
“requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó”  
ví dụ: “requestId;8:4,9,” 
d. 
Đóng socket và kết thúc chương trình 
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class UDPClient_CvlqJmaa {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        // a. Gửi request
        String request = ";B22DCCN424;CvlqJmaa";
        byte[] data = request.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
        socket.send(packet);

        // b. Nhận dữ liệu
        byte[] buf = new byte[4096];
        DatagramPacket response = new DatagramPacket(buf, buf.length);
        socket.receive(response);

        String received = new String(response.getData(), 0, response.getLength());
        System.out.println("Nhận: " + received);

        String[] parts = received.split(";", 2);
        String reqId = parts[0];
        String str = parts[1];

        // c. Tìm ký tự xuất hiện nhiều nhất
        Map<Character, Integer> freq = new HashMap<>();
        int maxFreq = 0;
        char mostChar = ' ';

        for (char c : str.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            if (freq.get(c) > maxFreq) {
                maxFreq = freq.get(c);
                mostChar = c;
            }
        }

        // Lấy các vị trí xuất hiện
        StringBuilder pos = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == mostChar) {
                pos.append(i).append(",");
            }
        }

        // d. Gửi kết quả
        String result = reqId + ";" + mostChar + ":" + pos.toString();
        System.out.println("Gửi: " + result);

        byte[] sendData = result.getBytes();
        DatagramPacket outPacket = new DatagramPacket(sendData, sendData.length, host, port);
        socket.send(outPacket);

        socket.close();
    }
}

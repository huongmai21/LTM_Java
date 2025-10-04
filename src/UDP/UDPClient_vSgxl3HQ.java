/*
BÀI 2. ĐẾM SỐ LẦN XUẤT HIỆN[002] 
[Mã câu hỏi (qCode): vSgxl3HQ].  Một chương trình server cho phép giao tiếp qua giao thức UDP 
tại cổng 2208. Yêu cầu xây dựng chương trình client trao đổi thông tin với server theo kịch bản: 
a. Gửi một thông điệp chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví 
dụ: ";B15DCCN001;9F8C2D3A". 
b. Nhận một thông điệp từ server theo định dạng "requestId;data", với: 
    requestId là chuỗi ngẫu nhiên duy nhất. 
    data là một chuỗi ký tự liên tiếp cần xử lý. Ví dụ: "requestId;aaabbbccdaa" 
Luyện thi UDP 
c. Xử lý chuỗi bằng cách đếm số lượng ký tự và gom chúng lại theo định dạng "số_lần_ký_tự". 
Gửi kết quả về server theo định dạng: 
"requestId;processedData" 
Ví dụ: Với chuỗi "aaabbbccdaa", kết quả sẽ là: "requestId;5a3b2c1d" 
d. Đóng socket và kết thúc chương trình. 
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author OS
 */
public class UDPClient_vSgxl3HQ {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2208;

        // a. Gửi request
        String request = ";B22DCCN424;vSgxl3HQ";
        socket.send(new DatagramPacket(request.getBytes(), request.length(), host, port));

        // b. Nhận dữ liệu
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);

        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Nhận: " + data);

        String[] parts = data.split(";");
        String reqId = parts[0];
        String str = parts[1];

        // c. Đếm ký tự
        int[] cnt = new int[256]; // ASCII
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            cnt[ch]++;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (cnt[ch] > 0) {
                ans.append(cnt[ch]).append(ch);
                cnt[ch] = 0; // tránh lặp lại
            }
        }

        // d. Gửi kết quả về server
        String res = reqId + ";" + ans.toString();
        System.out.println("Gửi: " + res);
        socket.send(new DatagramPacket(res.getBytes(), res.length(), host, port));

        socket.close();
    }
}

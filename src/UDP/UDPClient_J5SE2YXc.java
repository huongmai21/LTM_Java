/*
BÀI 4. MÃ HOÁ CAESAR [014] 
[Mã câu hỏi (qCode): J5SE2YXc].  Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì 
mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký 
tự “A” sẽ được thay thế bằng ký tự “D”. 
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu xây dựng 
chương trình client trao đổi thông tin với server theo kịch bản mô tả dưới đây: 
a. 
Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
";studentCode;qCode". Ví dụ: ";B15DCCN001;825EE3A7" 
b. 
Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strEncode;s". 
• requestId là chuỗi ngẫu nhiên duy nhất 
• strEncode là chuỗi thông điệp bị mã hóa 
• s là số nguyên chứa giá trị độ dịch của mã 
c. 
Giải mã tìm thông điệp ban đầu và gửi lên server theo định dạng “requestId;strDecode” 
d. 
Đóng socket và kết thúc chương trình.
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
public class UDPClient_J5SE2YXc {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;

        // a. Gửi request
        String request = ";B22DCCN424;J5SE2YXc";
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
        int k = Integer.parseInt(parts[2]);

        // c. Mã hóa Caesar
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isLowerCase(ch)) {
                ans.append((char) ((ch - 'a' + k) % 26 + 'a'));
            } else if (Character.isUpperCase(ch)) {
                ans.append((char) ((ch - 'A' + k) % 26 + 'A'));
            } else {
                ans.append(ch); // giữ nguyên ký tự khác
            }
        }

        // d. Gửi kết quả
        String res = reqId + ";" + ans.toString();
        System.out.println("Gửi: " + res);
        socket.send(new DatagramPacket(res.getBytes(), res.length(), host, port));

        socket.close();
    }
}

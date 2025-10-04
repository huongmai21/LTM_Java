/*
BÀI 10. TỔNG CÁC CHỮ SỐ [001] 
[Mã câu hỏi (qCode): 0Iend7Pp].  Một chương trình server cho phép giao tiếp qua giao thức UDP 
tại cổng 2207. Yêu cầu là xây dựng chương trình client trao đổi thông tin với server theo kịch bản: 
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
";studentCode;qCode". Ví dụ: ";B15DCCN011;A1F3D5” 
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;num", với: - requestId là chuỗi ngẫu nhiên duy nhất. - num là một số nguyên lớn. 
c. Tính tổng các chữ số trong num và gửi lại tổng này về server theo định dạng 
"requestId;sumDigits". 
d. Đóng socket và kết thúc chương trình.
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient_0Iend7Pp {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;

        // a. Gửi request
        String request = ";B22DCCN424;0Iend7Pp";
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
        String num = parts[1];

        // c. Tính tổng các chữ số
        long sum = 0;
        for (char c : num.toCharArray()) {
            if (Character.isDigit(c)) {
                sum += c - '0';
            }
        }

        String result = reqId + ";" + sum;
        System.out.println("Gửi: " + result);

        // d. Gửi lại kết quả
        byte[] sendData = result.getBytes();
        DatagramPacket outPacket = new DatagramPacket(sendData, sendData.length, host, port);
        socket.send(outPacket);

        socket.close();
    }
}


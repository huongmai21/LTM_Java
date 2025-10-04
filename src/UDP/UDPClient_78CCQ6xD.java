/*
BÀI 11. LIỆT KÊ SỐ NGUYÊN TỐ [B17DCAT176] 
[Mã câu hỏi (qCode): 78CCQ6xD].  Một chương trình server cho phép giao tiếp qua giao thức UDP 
tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch 
bản: 
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng 
";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4". 
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n, n", với: --- requestId là chuỗi ngẫu nhiên duy nhất. --- n là một số nguyên ngẫu nhiên ≤ 100. 
c. Tính và gửi về server danh sách n số nguyên tố đầu tiên theo định dạng "requestId;p1,p2,...,pk", 
trong đó p1,p2,...,pk là các số nguyên tố. 
d. Đóng socket và kết thúc chương trình. 
*/
package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient_78CCQ6xD {
    
    // Hàm kiểm tra số nguyên tố
    public static boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2207;

        // a. Gửi request
        String request = ";B22DCCN424;78CCQ6xD";
        byte[] data = request.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
        socket.send(packet);

        // b. Nhận dữ liệu
        byte[] buf = new byte[4096];
        DatagramPacket response = new DatagramPacket(buf, buf.length);
        socket.receive(response);

        String received = new String(response.getData(), 0, response.getLength());
        System.out.println("Nhận: " + received);

        String[] parts = received.split(";");
        String reqId = parts[0];
        int n = Integer.parseInt(parts[1]);

        // c. Tìm n số nguyên tố đầu tiên
        StringBuilder sb = new StringBuilder(reqId + ";");
        int count = 0, num = 2;
        while (count < n) {
            if (isPrime(num)) {
                sb.append(num).append(",");
                count++;
            }
            num++;
        }
        sb.deleteCharAt(sb.length() - 1); // bỏ dấu phẩy cuối

        String result = sb.toString();
        System.out.println("Gửi: " + result);

        // Gửi lại server
        byte[] sendData = result.getBytes();
        DatagramPacket outPacket = new DatagramPacket(sendData, sendData.length, host, port);
        socket.send(outPacket);

        socket.close();
    }
}


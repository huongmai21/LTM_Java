//[Mã câu hỏi (qCode): 9UKCYEtp].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
//Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
//a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
//b.	Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
//Ex: 2|5|9|11
//c.	Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
//Ex: 27
//d.	Đóng kết nối và kết thúc


package BT_TrenWeb.Bai_9UKCYEtp_TCP_ByteStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TCPClient {
    public static void main(String[] args) throws IOException {
        String host = "203.162.10.109";
        int port = 2206;
        
        String studentCode = "B22DCCN424";  
        String qCode = "9UKCYEtp";    
        
        Socket socket = new Socket(host, port);
        
        try {
            socket.setSoTimeout(5000); // thời gian chờ tối đa 5s

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // a. Gửi "studentCode;qCode"
            String request = studentCode + ";" + qCode;
            out.write(request.getBytes());
            out.flush();
//            System.out.println("Đã gửi thông tin: B22DCCN424;9UKCYEtp");

            // b. Nhận dữ liệu từ server
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);
            if (bytesRead == -1) {
                System.out.println("Không nhận được dữ liệu từ server");
                return;
            }
            String received = new String(buffer, 0, bytesRead).trim();
            System.out.println("Nhận từ server: " + received);

            // c. Tính tổng các số
            String[] parts = received.split("\\|");
            int sum = 0;
            for (String p : parts) {
                sum += Integer.parseInt(p);
            }
            System.out.println("Tổng tính được: " + sum);

            // gửi lại kết quả cho server
            out.write(String.valueOf(sum).getBytes());
            out.flush();

            // d. Đóng kết nối
            out.close();
            in.close();
            socket.close();
            System.out.println("Đã gửi kết quả và đóng kết nối.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

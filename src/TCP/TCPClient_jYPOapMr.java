/*
[Mã câu hỏi (qCode): jYPOapMr - Data Stream].  Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 
(hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, 
sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự: 
a.	Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D25ED92"
b.	Nhận lần lượt hai số nguyên a và b từ server
c.	Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
d.	Đóng kết nối và kết thúc
*/
package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TCPClient_jYPOapMr {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        String studentCode = "B22DCCN424";
        String qCode = "jYPOapMr";
        
        try {
            socket.setSoTimeout(5000);
            
            String resquest = studentCode + ";" + qCode;
            
            dos.writeUTF(resquest);
            dos.flush();
            
            // Nhận dl
            int a = dis.readInt();
            int b = dis.readInt();
            System.out.printf("2 số nguyên a, b là: %d, %d\n", a, b);
            int sum = a + b;
            int mul = a * b;
            
            dos.writeInt(sum);
            dos.writeInt(mul);
            System.out.printf("Kết quả: tổng %d, tích %d", sum, mul);
            dos.flush();
            
            dos.close();
            dis.close();
            socket.close();
            
        } catch (Exception e) {
        }
    }
 
}

/*
[Mã câu hỏi (qCode): eWmTTXoM].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 
(hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client 
thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B16DCCN999;C89DAB45"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
Ví dụ: "8,4,2,10,5,6,1,3"
c. Tính tổng của tất cả các số nguyên tố trong chuỗi và gửi kết quả lên server.
Ví dụ: Với dãy "8,4,2,10,5,6,1,3", các số nguyên tố là 2, 5, 3, tổng là 10. Gửi lên server chuỗi "10".
d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.sqrt;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_eWmTTXoM {
    public static boolean nt(int x){
        if(x<2)
            return false;
        if(x == 2)
            return true;
        for(int i=2; i<= sqrt(x);++i){
            if(x%i == 0)
                return false;
        }   
        return x>2;
    }
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);
        
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        
        String studentCode = "B22DCCN424";
        String qCode = "eWmTTXoM";
        
        try {
            socket.setSoTimeout(5000);
            
            String request = studentCode + ";" + qCode;
            os.write(request.getBytes());
            os.flush();
            
            // Nhận dl
            byte[] buffer = new byte[1024];
            int byteRead = is.read(buffer);
            if(byteRead == -1){
                System.out.println("Không nhận được dữ liệu từ server");
                return;
            }
            
            String data = new String(buffer, 0, byteRead);
            System.out.println("Chuỗi số nhận được từ server: " + data);
            String[] d = data.split("\\,");
            int sum = 0;
            for(String digit : d){
                int x = Integer.parseInt(digit.strip());
                if(nt(x) == true){
                    sum +=x;
                }
            }
            
            os.write(String.valueOf(sum).getBytes());
            System.out.println("Tổng các số nguyên tố trong chuỗi là: " + sum);
            os.flush();
            
            os.close();
            is.close();
            socket.close();
        } catch (Exception e) {
        }
    }
}

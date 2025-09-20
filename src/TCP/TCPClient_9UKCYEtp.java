/*
[Mã câu hỏi (qCode): 9UKCYEtp - Byte Stream].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C64967DD"
b.	Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng  "|"
Ex: 2|5|9|11
c.	Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server
Ex: 27
d.	Đóng kết nối và kết thúc
*/
package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_9UKCYEtp {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);
        
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        
        String studentCode = "B22DCCN424";
        String qCode = "9UKCYEtp";
        try{
            socket.setSoTimeout(5000);
            
            String request = studentCode + ";" +qCode;
            os.write(request.getBytes());
            os.flush();

            // Nhận dl
            byte[] buffer = new byte[1024];
            int byteRead = is.read(buffer);
            if(byteRead == -1){
                System.out.println("Không nhận được dữ liệu từ server");
                return;
            }
            String data = new String(buffer, 0, byteRead).trim();
            System.out.println("Dữ liệu nhận được: " + data);
            
            String[] a = data.split("\\|");
            int sum = 0;
            for(int i=0;i<a.length;++i){
                sum += Integer.parseInt(a[i]);
            }

            //Gửi lại server
            os.write(String.valueOf(sum).getBytes());
            System.out.println("Kết quả: " + sum);
            os.flush();

            //Đóng
            os.close();
            is.close();
            socket.close();
            System.out.println("Đã gửi kết quả và đóng kết nối");
        }catch(Exception e){
            e.getStackTrace();
        }
        
    }
}

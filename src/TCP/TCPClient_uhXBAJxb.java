/*
[Mã câu hỏi (qCode): uhXBAJxb - Byte Stream].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B16DCCN999;D45EFA12"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
Ví dụ: "10,5,15,20,25,30,35"
c. Xác định hai số trong dãy có tổng gần nhất với gấp đôi giá trị trung bình của toàn bộ dãy. Gửi thông điệp lên server theo định dạng "num1,num2" (với num1 < num2)
Ví dụ: Với dãy "10,5,15,20,25,30,35", gấp đôi giá trị trung bình là 40, hai số có tổng gần nhất là 15 và 25. Gửi lên server chuỗi "15,25".
d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_uhXBAJxb {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);
        
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        
        String studentCode = "B22DCCN424";
        String qCode = "uhXBAJxb";
        
        try {
            socket.setSoTimeout(5000);
            
            String request = studentCode + ";" + qCode;
            os.write(request.getBytes());
            os.flush();
            
            //Nhận dl
            byte[] buffer = new byte[1024];
            int byteRead = is.read(buffer);
            if(byteRead == -1){
                System.out.println("Không nhận được dl");
                return;
            }
            
            String data = new String(buffer, 0, byteRead);
            System.out.println("Chuỗi só nhận được: " + data);
            String[] digits = data.split("\\,");
            int n = digits.length;
            int sum = 0;
            int[] arr = new int[n];
            
            for(int i=0;i<n;++i){
                arr[i] = Integer.parseInt(digits[i].trim());
                sum += arr[i];
            }
            int k = (int)sum/n*2;
            int Min = Integer.MAX_VALUE;
            int num1 = 0,num2 = 0;
            
            for(int i = 0;i<n-1;++i){
                for(int j=i+1;j<n;++j){
                    int sum2 = arr[i] + arr[j];
                    int kc = abs(sum2-k);
                    if(kc<Min){
                        Min = kc;
                        num1 = min(arr[i], arr[j]);
                        num2 = max(arr[i], arr[j]);
                    }
                }
            }
            
            String answer = num1 + "," +num2;
            os.write(answer.getBytes());
            System.out.println("Cặp số gửi lên server: " + answer);
            os.flush();
            
            os.close();
            is.close();
            socket.close();
            
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}

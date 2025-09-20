/*
[Mã câu hỏi (qCode): JMLvteYn].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B16DCCN999;E56FAB67"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
Ví dụ: " 3,7,2,5,8,1"
c. Tìm vị trí mà độ lệch của tổng bên trái và tổng bên phải là nhỏ nhất -> Gửi lên server vị trí đó, tổng trái, tổng phải và độ lệch. Ví dụ: với dãy " 3,7,2,5,8,1", vị trí 3 có độ lệch nhỏ nhất = 3 → Kết quả gửi server: "3,12,9,3"
d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import static java.lang.Math.abs;

/**
 *
 * @author OS
 */
public class TCPClient_JMLvteYn {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2206);
        
        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();
        
        String request = "B22DCCN424" + ";" + "JMLvteYn";
        
        try {
            socket.setSoTimeout(5000);
            
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
            System.out.println("Chuỗi số nhận được: "+ data);
            String[] arr = data.split("\\,");
            
            // Chuyển đổi mảng sang int
            int n = arr.length;
            int[] a = new int[n];
            for(int i=0; i<n;++i){
                a[i] = Integer.parseInt(arr[i].trim());
            }
            
            int index = -1, mindis = Integer.MAX_VALUE;
            int bestLeft = 0, bestRight = 0;
            
            for(int i=0;i<n;++i){
                int leftSum = 0, rightSum = 0;
                for(int j = 0;j<i;++j) leftSum += a[j];
                for(int j = i+1;j<n;++j) rightSum += a[j];
                
                int kc = abs(leftSum - rightSum);
                if(kc<mindis){
                    mindis = kc;
                    index = i;
                    bestLeft = leftSum;
                    bestRight = rightSum;
                }
            }
            
            String answer = index + "," + bestLeft +","+ bestRight +","+ mindis;
            os.write(answer.getBytes());
            
            System.out.println("Kết quả gửi: " + answer);
            os.flush();
            
            os.close();
            is.close();
            socket.close();
        } catch (Exception e) {
        }
    }
}

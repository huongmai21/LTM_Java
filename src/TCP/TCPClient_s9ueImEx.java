/*
[Mã câu hỏi (qCode): s9ueImEx].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B10DCCN002;B4C5D6E7"
b. Nhận chuỗi chứa mảng số nguyên từ server, các phần tử được phân tách bởi dấu phẩy ",". Ví dụ: "1,3,2,5,4,7,6"
c. Tính số lần đổi chiều và tổng độ biến thiên trong dãy số.
- Đổi chiều: Khi dãy chuyển từ tăng sang giảm hoặc từ giảm sang tăng 
-   Độ biến thiên: Tổng giá trị tuyệt đối của các hiệu số liên tiếp
Gửi lần lượt lên server: số nguyên đại diện cho số lần đổi chiều, sau đó là số nguyên đại diện cho tổng độ biến thiên. Ví dụ: Với mảng "1,3,2,5,4,7,6", số lần đổi chiều: 5 lần, Tổng độ biến thiên 11 -> Gửi lần lượt số nguyên 5 và 11 lên server.
d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.Math.abs;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_s9ueImEx {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        String request = "B22DCCN424;s9ueImEx";
        
        try {
            socket.setSoTimeout(5000);
            
            dos.writeUTF(request);
            dos.flush();
            
            //nhận dl
            String data = dis.readUTF();
            System.out.println("Nhận: "+data);
            String[] s = data.split("\\,");
            int n = s.length;
            int[] a = new int[n];
            for(int i=0;i<n;++i){
                a[i] = Integer.parseInt(s[i].trim());
            }
            
            int count=0; 
            int dir = 0; // 0: chưa xác định, 1: tăng, -1: giảm
            for(int i=1;i<n;++i){
                if(a[i] > a[i-1]){
                    if(dir == -1) count++;
                    dir = 1;
                }
                else if(a[i] < a[i-1]){
                    if(dir == 1) count++;
                    dir =-1;
                }
            }
            
            int sum =0;
            for(int i=1;i<n;i++){
                sum += abs(a[i]-a[i-1]);
            }
            
            //Gửi kq
            dos.writeInt(count);
            dos.writeInt(sum);
            System.out.printf("Kết quả gửi: %d %d ", count, sum);
            dos.flush();
            
            dos.close();
            dis.close();
            socket.close();
        } catch (Exception e) {
        }
    }
}

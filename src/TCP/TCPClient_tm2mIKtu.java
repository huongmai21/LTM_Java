/*
[Mã câu hỏi (qCode): tm2mIKtu].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
Ví dụ: "B10DCCN003;C6D7E8F9"
b. Nhận lần lượt:
•	Một số nguyên k là độ dài đoạn.
•	Chuỗi chứa mảng số nguyên, các phần tử được phân tách bởi dấu phẩy ",".
Ví dụ: Nhận k = 3 và "1,2,3,4,5,6,7,8".
c. Thực hiện chia mảng thành các đoạn có độ dài k và đảo ngược mỗi đoạn, sau đó gửi mảng đã xử lý lên server. Ví dụ: Với k = 3 và mảng "1,2,3,4,5,6,7,8", kết quả là "3,2,1,6,5,4,8,7". Gửi chuỗi kết quả "3,2,1,6,5,4,8,7" lên server.
d. Đóng kết nối và kết thúc chương trình
*/
package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_tm2mIKtu {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2207);
        
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        
        String request = "B22DCCN424" + ";" + "tm2mIKtu";
        
        try {
            socket.setSoTimeout(5000);
            
            dos.writeUTF(request);
            dos.flush();
            
            // Nhận dl
            int k = dis.readInt();
            System.out.println("Giá trị k: " +k);
            String data = dis.readUTF();
            System.out.println("Mảng: " + data);
            
            String[] arr = data.split("\\,");
            for(int i=0;i<arr.length;i+=k){
                int l = i;
                int r = Math.min(i+k-1, arr.length-1);
                
                while(l < r){
                    String tmp = arr[l].trim();
                    arr[l]= arr[r].trim();
                    arr[r] = tmp;
                    l++;
                    r--;
                }
            }
            
            String result = String.join(",", arr);
            dos.writeUTF(result);
            System.out.println("Gửi server: " +result);
            dos.flush();
            
            dos.close();
            dis.close();
            socket.close();
        } catch (Exception e) {
        }
    }
}

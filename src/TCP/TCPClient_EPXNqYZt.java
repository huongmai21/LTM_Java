/*
[Mã câu hỏi (qCode): EPXNqYZt].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:

a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;C1234567"

b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng. Ví dụ: "hello world this is a test example"

c. Sắp xếp các từ trong chuỗi theo độ dài, thứ tự xuất hiện. Gửi danh sách các từ theo từng nhóm về server theo định dạng: "a, is, this, test, hello, world, example".

d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author OS
 */
public class TCPClient_EPXNqYZt{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2208);
        
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request = "B22DCCN424;EPXNqYZt";
        
        try {
            socket.setSoTimeout(5000);
            
            out.write(request);
            out.newLine();
            out.flush();
            
            // Nhận dl
            String data = in.readLine();
            System.out.println("Chuỗi nhận được: "+data);
            String[] s = data.split("\\s+");
//            Arrays.sort(s, Comparator.comparingInt(String::length)); //C1
            Arrays.sort(s, (a, b) -> a.length() - b.length()); //C2
//            Arrays.sort(s, (a, b) -> Integer.compare(a.length(), b.length())); //C3

            String result = String.join(", ", s);
            out.write(result);
            out.newLine();
            System.out.println("Kết quả: " + result);
            
            out.close();
            in.close();
            socket.close();
            
        } catch (Exception e) {
        }
    }
}

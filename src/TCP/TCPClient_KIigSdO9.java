/*
[Mã câu hỏi (qCode): KIigSdO9].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode".
Ví dụ: "B15DCCN999;1D08FX21"
b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng.
Ví dụ: "hello world programming is fun"
c. Thực hiện đảo ngược từ và mã hóa RLE để nén chuỗi ("aabb" nén thành "a2b2"). Gửi chuỗi đã được xử lý lên server. Ví dụ: "ol2eh dlrow gnim2argorp si nuf".
d. Đóng kết nối và kết thúc chương trình
*/
package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_KIigSdO9 {
    public static String dao(String s){
        StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }
    public static String mahoa(String s){
        StringBuilder sb = new StringBuilder("");
        int dem =1;
        for(int i=1;i<=s.length();++i){
            if(i< s.length() && s.charAt(i) == s.charAt(i-1)){
                dem++;
            }
            else{
                sb.append(s.charAt(i-1));
                if(dem>1) sb.append(dem);
                dem=1;
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2208);
        
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        String request = "B22DCCN424;KIigSdO9";
        
        try {
            socket.setSoTimeout(5000);
            
            out.write(request);
            out.newLine();
            out.flush();
            
            //Nhận dl
            String data = in.readLine();
            System.out.println("Dữ liệu nhận được: " +data);
            String[] s = data.split("\\s+");
            StringBuilder sb =new StringBuilder("");
            for(String word : s){
                String d = dao(word.trim());
                String mh = mahoa(d);
                sb.append(mh + " ");
            }
            sb.deleteCharAt(sb.length()-1);
            
            //Gửi
            out.write(sb.toString());
            out.newLine();
            System.out.println("Chuỗi sau chuẩn hoá: "+sb.toString());
            out.flush();
            
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
        }
    }
}

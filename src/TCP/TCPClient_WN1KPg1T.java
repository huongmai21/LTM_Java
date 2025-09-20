/*
[Mã câu hỏi (qCode): WN1KPg1T - Character Stream].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau: 
a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;EC4F899B"
b.	Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server
Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu
c.	Tìm kiếm các tên miền .edu và gửi lên server
Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu
d.	Đóng kết nối và kết thúc chương trình.

*/
package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class TCPClient_WN1KPg1T {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2208);
        
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   
        
        String studentCode = "B22DCCN424";
        String qCode = "WN1KPg1T";
        
        try {
            socket.setSoTimeout(5000);
            
            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine();  // kết thúc dòng để server đọc được
            writer.flush();
            
            //Nhận dl
           
            String data = reader.readLine();
            System.out.println("Dữ liệu nhận được: "+ data);
            String[] s = data.split(",\\s*"); 
                    // "," nghĩa là tách theo dấu phẩy.
                    // \\s* nghĩa là “0 hoặc nhiều ký tự khoảng trắng” (space, tab, newline...).
            
            String res = "";
            for(String p : s){
                if(p.endsWith(".edu")){
                    res += p +", ";
                }
            }
            res = res.trim();
            String m = res.substring(0, res.length()-1);
            
            /*
            List<String> resultList = new ArrayList<>();
            for (String p : s) {
                if (p.endsWith(".edu")) {
                    resultList.add(p);
                }
            }
            String m = String.join(", ", resultList);
            */
            
            
            writer.write(m);
            writer.newLine();
            writer.flush();
            System.out.println("Kết quả: "+ m);
            
            writer.close();
            reader.close();
            socket.close();
            System.out.println("Đóng kết nối và kết thúc chương trình.");
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}

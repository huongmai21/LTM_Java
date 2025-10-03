/*
[Mã câu hỏi (qCode): olLLZzpY].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
•	Tên đầy đủ lớp: UDP.Student
•	Các thuộc tính: id String,code String, name String, email String
•	02 Hàm khởi tạo: 
o	public Student(String id, String code, String name, String email)
o	public Student(String code)
•	Trường dữ liệu: private static final long serialVersionUID = 20171107
Thực hiện:
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
b.	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
c.	Yêu cầu:
-	Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
-	Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
-	Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
d.	Đóng socket và kết thúc chương trình.
*/
package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author OS
 */
public class UDPClient_olLLZzpY_Student {
    public  static String chTen(String name){
        StringBuilder sb = new StringBuilder();
        String[] s = name.split("\\s+");
        for(String x : s){
            sb.append(Character.toUpperCase(x.charAt(0)));
            for(int i=1;i<x.length();++i){
                sb.append(Character.toLowerCase(x.charAt(i)));
            }
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    
    public static String taoEmail(String name){
        StringBuilder sb = new StringBuilder();
        name = name.toLowerCase();
        String[] s = name.split("\\s+");
        sb.append(s[s.length-1]);
        for(int i=0;i<s.length-1;++i){
            sb.append(s[i].charAt(0));
        }
        sb.append("@ptit.edu.vn");
        return sb.toString();
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        String request = ";B22DCCN424;olLLZzpY";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        // Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        byte[] data = receivePacket.getData();
        String reqId = new String(data, 0, 8);
        byte[] objBytes = new byte[receivePacket.getLength()-8];
        System.arraycopy(data, 8, objBytes, 0, objBytes.length);
        
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Student s = (Student) ois.readObject();
        System.out.println("Nhận: "+ s);
        
        s.setName(chTen(s.getName()));
        s.setEmail(taoEmail(s.getName()));
        
        //Gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(s);
        oos.flush();
        
        byte[] sendObj = baos.toByteArray();
        byte[] finalData = new byte[8 + sendObj.length];
        System.arraycopy(reqId.getBytes(), 0, finalData, 0, 8);
        System.arraycopy(sendObj, 0, finalData, 8, sendObj.length);
        
        DatagramPacket outPacket = new DatagramPacket(finalData, finalData.length, host, port);
        socket.send(outPacket);
        System.out.println("KQ: " + s);
        
        socket.close();
        
    }
}


class Student implements Serializable{
    private String id, code, name, email;
    private static final long serialVersionUID = 20171107;

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public Student(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", code=" + code + ", name=" + name + ", email=" + email + '}';
    }
    
    
}
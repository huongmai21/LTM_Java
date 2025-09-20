/*
[Mã câu hỏi (qCode): B0b1Wfot].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) để gửi/nhận và chuẩn hóa thông tin địa chỉ của khách hàng.
Biết rằng lớp TCP.Address có các thuộc tính (id int, code String, addressLine String, city String, postalCode String) và trường dữ liệu private static final long serialVersionUID = 20180801L.
a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;A1B2C3D4"
b. Nhận một đối tượng là thể hiện của lớp TCP.Address từ server. Thực hiện chuẩn hóa thông tin addressLine bằng cách:
•	Chuẩn hóa addressLine: Viết hoa chữ cái đầu mỗi từ, in thường các chữ còn lại, loại bỏ ký tự đặc biệt và khoảng trắng thừa (ví dụ: "123 nguyen!!! van cu" → "123 Nguyen Van Cu") 
•	Chuẩn hóa postalCode: Chỉ giữ lại số và ký tự "-" ví dụ: "123-456"
c. Gửi đối tượng đã được chuẩn hóa thông tin địa chỉ lên server.
d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_B0b1Wfot {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2209);
        
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        
        String request = "B22DCCN424;B0b1Wfot";
        
        try {
            socket.setSoTimeout(5000);
            
            oos.writeObject(request);
            oos.flush();
            
            //Nhận dl
            Object data = ois.readObject();
            if(data instanceof Address){
                Address a = (Address) data;
                System.out.println("Đối tượng nhận được: " +a);
                
                //Chuẩn hoá địa chỉ
                String address = a.getAddressLine();
                String cleanA = address.replaceAll("[^a-zA-Z0-9\\s]", " ").replaceAll("\\s+", " ").trim();
                
                StringBuilder sb = new StringBuilder("");
                String[] arr = cleanA.split(" ");
                for(String x : arr){
                    sb.append(Character.toUpperCase(x.charAt(0)));
                    for(int i = 1;i<x.length();++i){
                        sb.append(Character.toLowerCase(x.charAt(i)));
                    }
                    sb.append(" ");
                }
                sb.deleteCharAt(sb.length()-1);
                a.setAddressLine(sb.toString());
                
                // Chuẩn hoá postalCode
                
                String postalCode = a.getPostalCode();
                String cleanP = postalCode.replaceAll("[^0-9-]", "");
                a.setPostalCode(cleanP);
                
                // Kết quả
                oos.writeObject(a);
                System.out.println("Đối tưởng sau chuẩn hoá là: "+ a);
                oos.flush();
            }   
            
            oos.close();
            ois.close();
            socket.close();
            
        } catch (Exception e) {
        }
    }
}

class Address implements Serializable{
    private int id;
    private String code, addressLine, city, postalCode;
    
    private static final long serialVersionUID = 20180801L;

    //Getter
    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }
    
    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + "\ncode=" + code + "\naddressLine=" + addressLine + "\ncity=" + city + "\npostalCode=" + postalCode + '}';
    }
     
    
}
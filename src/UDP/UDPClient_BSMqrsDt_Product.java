/*
[Mã câu hỏi (qCode): BSMqrsDt].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a.	Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
b.	Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899

Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
a.	Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
•	Tên đầy đủ của lớp: UDP.Product
•	Các thuộc tính: id String, code String, name String, quantity int
•	Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
•	Trường dữ liệu: private static final long serialVersionUID = 20161107; 
b.	Giao tiếp với server theo kịch bản
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”

•	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity đã được thiết lập giá trị.
•	Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng vừa được sửa đổi lên server theo cấu trúc:
08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
•	Đóng socket và kết thúc chương trình.
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
public class UDPClient_BSMqrsDt_Product {
    public static String fixname(String name){
        String[] s = name.split("\\s+");
        if(s.length  > 1){
            String tmp = s[0];
            s[0] = s[s.length-1];
            s[s.length-1] = tmp;
        }
        return String.join(" ", s);
    }
    
    public static int fixquality(int q){
        String tmp = String.valueOf(q);
        String dao = new StringBuilder(tmp).reverse().toString();
        return Integer.parseInt(dao);
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        String request = ";B22DCCN424;BSMqrsDt";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        //Nhận dl
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        byte[] data = receivePacket.getData();
        String reqId = new String(data,0,8);
        byte[] objBytes = new byte[receivePacket.getLength() - 8];
        // Copy từ data bắt đầu ở vị trí thứ 8 (sau requestId), chép vào objBytes
        System.arraycopy(data, 8, objBytes, 0, objBytes.length);
        
        //Giải nén Obj
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Product p = (Product) ois.readObject();
        System.out.println("Nhận dl: " + p);
        p.setName(fixname(p.getName()));
        p.setQuantity(fixquality(p.getQuantity()));
        
        // Gửi lại server
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(p);
        oos.flush();
        
        byte[] sendObj = baos.toByteArray();
        byte[] finalData = new byte[8+sendObj.length];
        System.arraycopy(reqId.getBytes(), 0, finalData, 0, 8);
        System.arraycopy(sendObj, 0, finalData, 8, sendObj.length);
        
        DatagramPacket outPacket = new DatagramPacket(finalData, finalData.length, host, port);
        socket.send(outPacket);
        System.out.println("Gửi kq" + p);
    }
}

class Product implements Serializable{
    private String id, code, name;
    private int quantity;
    private static final long serialVersionUID = 20161107; 

    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", code=" + code + ", name=" + name + ", quantity=" + quantity + '}';
    }
    
    
    
}

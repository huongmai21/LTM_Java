/*
[Mã câu hỏi (qCode): kPcrGNTZ].  Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b.	Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
c.	Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong


Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
a.	Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
•	Tên đầy đủ của lớp: UDP.Customer
•	Các thuộc tính: id String, code String, name String, , dayOfBirth String, userName String
•	Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
•	Trường dữ liệu: private static final long serialVersionUID = 20151107; 

b.	Client giao tiếp với server theo các bước
•       Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”

•	Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name,dayOfBirth đã được thiết lập sẵn.
•	Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc:
08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
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
public class UDPClient_kPcrGNTZ_Customer {

    // a. Chuẩn hóa tên
    public static String chTen(String name) {
        name = name.toLowerCase().trim();
        String[] parts = name.split("\\s+");
        StringBuilder sb = new StringBuilder();
        sb.append(parts[parts.length - 1].toUpperCase()); // last name
        sb.append(", ");
        for (int i = 0; i < parts.length - 1; i++) {
            sb.append(Character.toUpperCase(parts[i].charAt(0)));
            sb.append(parts[i].substring(1));
            if (i < parts.length - 2) sb.append(" ");
        }
        return sb.toString();
    }

    // b. Chuẩn hóa ngày sinh mm-dd-yyyy -> dd/mm/yyyy
    public static String chDob(String dob) {
        String[] s = dob.split("-");
        return s[1] + "/" + s[0] + "/" + s[2];
    }

    // c. Tạo tài khoản: chữ cái đầu của các từ (trừ họ cuối) + full họ cuối
    public static String chTk(String name) {
        name = name.toLowerCase().trim();
        String[] parts = name.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            sb.append(parts[i].charAt(0));
        }
        sb.append(parts[parts.length - 1]);
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2209;

        // 1. Gửi request ";studentCode;qCode"
        String request = ";B22DCCN424;kPcrGNTZ";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);

        // 2. Nhận phản hồi: 8 byte đầu là requestId, phần còn lại là Customer
        byte[] buf = new byte[4096];
        DatagramPacket resPacket = new DatagramPacket(buf, buf.length);
        socket.receive(resPacket);

        byte[] data = resPacket.getData();
        String reqId = new String(data, 0, 8);
        byte[] objBytes = new byte[resPacket.getLength() - 8];
        System.arraycopy(data, 8, objBytes, 0, objBytes.length);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(objBytes));
        Customer c = (Customer) ois.readObject();
        ois.close();

        // 3. Chuẩn hóa thông tin
        String rawName = c.getName();
        c.setName(chTen(rawName));
        c.setDayOfBirth(chDob(c.getDayOfBirth()));
        c.setUserName(chTk(rawName));

        // 4. Đóng gói lại: 8 byte reqId + Customer
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(c);
        oos.flush();
        byte[] sendObj = baos.toByteArray();

        byte[] finalData = new byte[8 + sendObj.length];
        System.arraycopy(reqId.getBytes(), 0, finalData, 0, 8);
        System.arraycopy(sendObj, 0, finalData, 8, sendObj.length);

        DatagramPacket outPacket = new DatagramPacket(finalData, finalData.length, host, port);
        socket.send(outPacket);

        System.out.println("Đã gửi lại sau chuẩn hóa: " + c);

        socket.close();
    }
}


class Customer implements Serializable {
    private String id, code, name, dayOfBirth, userName;
    private static final long serialVersionUID = 20151107L;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    // getter + setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDayOfBirth() { return dayOfBirth; }
    public void setDayOfBirth(String dayOfBirth) { this.dayOfBirth = dayOfBirth; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", dayOfBirth='" + dayOfBirth + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
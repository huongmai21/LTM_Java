/*
[Mã câu hỏi (qCode): BYEDgTlI].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
Đối tượng trao đổi là thể hiện của lớp UDP.Employee được mô tả:
    Tên đầy đủ lớp: UDP.Employee
    Các thuộc tính: id (String), name (String), salary (double), hireDate (String)
    Hàm khởi tạo:
        public Employee(String id, String name, double salary, String hireDate)
    Trường dữ liệu: private static final long serialVersionUID = 20261107L

Thực hiện:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN006;ITleSdqV"
b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Employee từ server. Trong đó, các thuộc tính id, name, salary và hireDate đã được thiết lập sẵn.
c. Thực hiện:
    Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
    Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm bắt đầu làm việc (hireDate).
    Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
    Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.
d. Đóng socket và kết thúc chương trình.
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
public class UDPClient_BYEDgTlI_Employee {
    public static String chTen(String name){
        String[] s = name.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(String x : s){
            sb.append(Character.toUpperCase(x.charAt(0)));
            for(int i=1 ;i<x.length();++i){
                sb.append(Character.toLowerCase(x.charAt(i)));
            }
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    
    public static double tangSalary(double salary, String hireDate){
        String year = hireDate.substring(0, 4);
        int s = Integer.parseInt(year);
        int x = 0;
        while(s>0){
            x+= s%10;
            s/=10;
        }
        double luong = salary*(100+x)/100;
        return luong;
    }
    
    public static String chDate(String hireDate){
        String[] s = hireDate.split("-");
        return s[2]+"/"+s[1]+"/"+s[0];
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        String request = ";B22DCCN424;BYEDgTlI";
        byte[] sendReq = request.getBytes();
        DatagramPacket reqPacket = new DatagramPacket(sendReq, sendReq.length, host, port);
        socket.send(reqPacket);
        
        //Nhận dl 
        byte[] buf = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
        socket.receive(receivePacket);
        
        byte[] data = receivePacket.getData();
        String reqId = new String(data, 0, 8);
        byte[] objBytes = new byte[receivePacket.getLength()-8];
        System.arraycopy(data, 8, objBytes, 0, objBytes.length);
        
        //Giải nén
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Employee e = (Employee) ois.readObject();
        System.out.println("Nhận " + e);
        
        e.setName(chTen(e.getName()));
        e.setSalary(tangSalary(e.getSalary(), e.getHireDate()));
        e.setHireDate(chDate(e.getHireDate()));
        
        //Gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(e);
        oos.flush();
        
        byte[] sendObj = baos.toByteArray();
        byte[] finalData = new byte[8 + sendObj.length];
        System.arraycopy(reqId.getBytes(), 0, finalData, 0, 8);
        System.arraycopy(sendObj, 0, finalData, 8, sendObj.length);
        
        DatagramPacket outPacket = new DatagramPacket(finalData, finalData.length, host, port);
        socket.send(outPacket);
        System.out.println("KQ: " + e);
        
        socket.close();
    }
}

class Employee implements Serializable{
    private String id, name, hireDate;
    private double salary;
    private static final long serialVersionUID = 20261107L;

    public Employee(String id, String name, String hireDate, double salary) {
        this.id = id;
        this.name = name;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", name=" + name + ", hireDate=" + hireDate + ", salary=" + salary + '}';
    }
    
    
}
/*
[Mã câu hỏi (qCode): 1ReovYJ7 - Object Stream].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:

Biết lớp TCP.Product gồm các thuộc tính (id int, name String, price double, int discount) và private static final long serialVersionUID = 20231107;

a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1E08CA31"

b. Nhận một đối tượng là thể hiện của lớp TCP.Product từ server.

c. Tính toán giá trị giảm giá theo price theo nguyên tắc: Giá trị giảm giá (discount) bằng tổng các chữ số trong phần nguyên của giá sản phẩm (price). Thực hiện gán giá trị cho thuộc tính discount và gửi lên đối tượng nhận được lên server.

d. Đóng kết nối và kết thúc chương trình.
*/
package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author OS
 */
public class TCPClient_1ReovYJ7 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("203.162.10.109", 2209);
        
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        
        String studentCode = "B22DCCN424";
        String qCode = "1ReovYJ7";
        
        try {
            socket.setSoTimeout(5000);
            
            String request = studentCode + ";" + qCode;
            
            oos.writeObject(request);
            oos.flush();
            
            //Nhận dl
            Object obj = ois.readObject();
            if(obj instanceof Product){
                Product p = (Product) obj;
                System.out.println("Đối tượng nhận được: " + p);
                
                // Tính discount
                int intPart = (int) p.getPrice();
                int sumDigits = 0;
                while(intPart > 0){
                    sumDigits += intPart%10;
                    intPart /= 10;
                }
                
                p.setDiscount(sumDigits);
                
                //Gửi lại server
                oos.writeObject(p);
                System.out.println("Sau khi tính discount: "+ p);
                oos.flush();
                
                
                oos.close();
                ois.close();
                socket.close();
                System.out.println("Đã gửi và đóng kết nối");
            }
            
            
        } catch (Exception e) {
        }
    }
}

class Product implements Serializable{
    private int id, discount;
    private double price;
    private String name;
    
    private static final long serialVersionUID = 20231107;
    
    public int getId() {
        return id;
    }

    public int getDiscount() {
        return discount;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price  + ", discount=" + discount +'}';
    }
    
}
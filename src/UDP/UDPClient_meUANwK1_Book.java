/*
[Mã câu hỏi (qCode): meUANwK1].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:

Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:

    Tên đầy đủ lớp: UDP.Book
    Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
    Hàm khởi tạo:
        public Book(String id, String title, String author, String isbn, String publishDate)
    Trường dữ liệu: private static final long serialVersionUID = 20251107L

Thực hiện:

a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"

b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.

c. Thực hiện:
        Chuẩn hóa title: viết hoa chữ cái đầu của mỗi từ.
        Chuẩn hóa author theo định dạng "Họ, Tên".
        Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
        Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.

Đóng socket và kết thúc chương trình.
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
import java.util.Arrays;

/**
 *
 * @author OS
 */
public class UDPClient_meUANwK1_Book {
    public static String chTitle(String title){
        StringBuilder sb = new StringBuilder();
        String[] s = title.split("\\s+");
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
    

    public static String chAuthor(String author) {
        String[] s = author.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        sb.append(s[0].toUpperCase());
        sb.append(", ");
        for(int i=1;i<s.length;++i){
            sb.append(Character.toUpperCase(s[i].charAt(0)));
            for(int j = 1;j<s[i].length();++j){
                sb.append(Character.toLowerCase(s[i].charAt(j)));
            }
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    
    public static String chISBN(String isbn){
        return isbn.substring(0, 3) + "-"
                + isbn.substring(3, 4) + "-"
                + isbn.substring(4, 6) + "-"
                + isbn.substring(6, 12) + "-" 
                + isbn.substring(12);
    }
    
    public static String chDate(String publishDate){
        String[] s = publishDate.split("-");
        return s[1] + "/"+s[0];
    }
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, ClassNotFoundException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress host = InetAddress.getByName("203.162.10.109");
        int port = 2209;
        
        String request = ";B22DCCN424;meUANwK1";
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
        ObjectInputStream ois =  new ObjectInputStream(bais);
        Book b = (Book) ois.readObject();
        System.out.println(b);
        
        b.setTitle(chTitle(b.getTitle()));
        b.setAuthor( chAuthor(b.getAuthor()));
        b.setIsbn(chISBN(b.getIsbn()));
//        b.setPublishDate(chDate(b.getPublishDate()));
        
        //Gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(b);
        oos.flush();
        
        byte[] sendObj = baos.toByteArray();
        byte[] finalData = new byte[8+sendObj.length];
        System.arraycopy(reqId.getBytes(), 0, finalData, 0, 8);
        System.arraycopy(sendObj, 0, finalData, 8, sendObj.length);
        
        DatagramPacket outPacket = new DatagramPacket(finalData, finalData.length, host, port);
        socket.send(outPacket);
        System.out.println("KQ: " + b);
        
        socket.close();
    }
}


class Book implements Serializable{
    private String id, title, author, isbn, publishDate;
    private static final long serialVersionUID = 20251107L;

    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title=" + title + ", author=" + author + ", isbn=" + isbn + ", publishDate=" + publishDate + '}';
    }
    
    
}
/*
[Mã câu hỏi (qCode): 74QUrRxS].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
Giao diện từ xa: 
public interface CharacterService extends Remote { 
public String requestCharacter(String studentCode, tring qCode) throws RemoteException; 
public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException; 
}
Trong đó: 
• Interface CharacterService được viết trong package RMI. 
• Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server: a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Khóa XOR;Chuỗi đầu vào". 
b. Thực hiện thao tác mã hóa XOR cho chuỗi đầu vào với khóa XOR nhận được từ server. Mã hóa XOR thực hiện bằng cách áp dụng phép XOR trên từng ký tự trong chuỗi đầu vào và ký tự tương ứng trong khóa (khóa được lặp lại để khớp độ dài chuỗi đầu vào). 
Ví dụ: Chuỗi ban đầu "A;HELLO" -> Khóa "A" -> chuỗi mã hóa là: "IFMMN"
c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được mã hóa trở lại server. 
d. Kết thúc chương trình client.
*/
package RMI;

/**
 *
 * @author OS
 */
public class RMIClient_74QUrRxS {
    
}

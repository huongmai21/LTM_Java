/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BT_TrenWeb.Bai_tm2mIKtu;

/**
 *
 * @author OS
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) {
        String host = "203.162.10.109"; // server thầy cung cấp
        int port = 2207;

        String studentCode = "B22DCCN424"; // thay bằng mã SV của bạn
        String qCode = "tm2mIKtu";        // mã câu hỏi

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // a. Gửi "studentCode;qCode"
            String request = studentCode + ";" + qCode;
            dos.writeUTF(request);
            dos.flush();
            System.out.println("Đã gửi: " + request);

            // b. Nhận k và mảng từ server
            int k = dis.readInt();              // số nguyên k
            String arrayStr = dis.readUTF();    // chuỗi mảng
            System.out.println("Nhận k = " + k);
            System.out.println("Nhận mảng: " + arrayStr);

            // c. Xử lý chia mảng thành đoạn và đảo ngược
            String[] parts = arrayStr.split(",");
            int n = parts.length;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = Integer.parseInt(parts[i].trim());
            }

            for (int i = 0; i < n; i += k) {
                int left = i;
                int right = Math.min(i + k - 1, n - 1);
                while (left < right) {
                    int tmp = arr[left];
                    arr[left] = arr[right];
                    arr[right] = tmp;
                    left++;
                    right--;
                }
            }

            // chuyển lại thành chuỗi kết quả
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                sb.append(arr[i]);
                if (i < n - 1) sb.append(",");
            }
            String result = sb.toString();
            System.out.println("Kết quả sau xử lý: " + result);

            // gửi kết quả về server
            dos.writeUTF(result);
            dos.flush();

            // d. Đóng kết nối
            dis.close();
            dos.close();
            socket.close();
            System.out.println("Đã gửi kết quả và đóng kết nối.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
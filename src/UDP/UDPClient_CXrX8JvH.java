/*
[Mã câu hỏi (qCode): CXrX8JvH].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: ";B15DCCN009;EF56GH78"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;data", với:
•	requestId là chuỗi ngẫu nhiên duy nhất.
•	data là một chuỗi ký tự chứa nhiều từ, được phân cách bởi dấu cách.
Ví dụ: "EF56GH78;The quick brown fox"
c. Sắp xếp các từ trong chuỗi theo thứ tự từ điển ngược (z đến a) và gửi thông điệp lên server theo định dạng "requestId;word1,word2,...,wordN".
Ví dụ: Với data = "The quick brown fox", kết quả là: "EF56GH78;quick,fox,brown,The"
d. Đóng socket và kết thúc chương trình
*/
package UDP;

/**
 *
 * @author OS
 */
public class UDPClient_CXrX8JvH {
    
}

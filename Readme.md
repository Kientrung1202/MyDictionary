# Dictionary by Trung Duy Nam
***
### Công nghệ
 - Ngôn ngữ : java.
 - Sử dụng Javafx, quản lý bằng Maven.
 - Từ điển lấy từ file txt.
### Chức năng : 
- Tìm và tra từ thông qua đọc file text.
- Lưu các từ đã tra vào lịch sử và có thể xem lại về sau.
- Thêm từ mới.
- Xóa từ đang có trong từ điển.
- Tra đoạn văn bản.
- Tích hợp giọng đọc tiếng anh từ freetts.
### Cài đặt và chạy chương trình:
- Sử dụng maven để cài đặt 1 số dependencies, thư viện liên quan.
- Nên sử dụng IDE có hỗ trợ tự động cài đặt giúp thuận tiện, đơn giản hơn trong chạy chương trình.
- Chạy câu lệnh: mvn compile trong cmd(chắc chắn rằng máy tính đã cài đặt maven hoặc được IDE tích hợp).
- Nếu gặp lỗi của compile plugin nhưng đã hoàn tất các cài đặt cho dependencies, mã nguồn không còn
thông báo lỗi thư viện, module thì sau có thể tự chạy phương thức main() trong DictionaryApplication.java .
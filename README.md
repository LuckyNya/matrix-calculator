# Matrix Calculator Web

![image](https://github.com/user-attachments/assets/393dfa9a-84f4-4e6a-ae5a-2c85db359c14)

## Tính năng
- Thực hiện các phép tính ma trận: cộng, trừ, nhân, chia, chuyển vị, định thức, nghịch đảo...
- Lựa chọn giao thức TCP/UDP

### Các công cụ được sử dụng
| Công cụ            | Phiên bản | Mục đích                    |
|--------------------|-----------|-----------------------------|
| Java               | 8+        | Tạo thuật toán backend      |
| JavaScript         | ES6       | Frontend logic              |
| Tomcat             | 9.0+      | Tạo server                  |
| Maven              | 3.9.9     | Quản lí dự án               |
| Gson               | 2.8.9     | Xử lý JSON                  |



### Cài đặt
1. **Yêu cầu**:
   - JDK 8+
   - Apache Tomcat 9+
   - Apache Maven 3.9.9+
   - Trình duyệt web

2. **Chạy với Eclipse**:
```bash
1. Import như một project Maven
2. Điều chỉnh Tomcat server( thêm trong Runtime Enviroment)
3. Chạy TCP.java và UDP.java như Java application
4. Chạy Project trên server và chọn localhost Tomcat
```

## Tự build hệ thống

Project này sử dụng Maven nên trong pom.xml cần có:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-math3</artifactId>
        <version>3.6.1</version> <!-- Matrix operations -->
    </dependency>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.9</version> <!-- JSON processing -->
    </dependency>
    
    <!--Tomcat đã cung cấp -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

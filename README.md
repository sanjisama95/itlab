# itlab
Bài 1: 

Chạy ở https://localhost:8080
Tài khoản facebook chỉ có tài khoản em tạo mới truy cập được project do facebook chưa cho public ứng dụng

Bài 2 và 3:
1.	Truy cập vào C:\xampp\apache\conf\extra\httpd-vhosts.conf
Thêm cái virtual host sau:
<VirtualHost *:1234>
    ServerAdmin webmaster@ex2.web2
    DocumentRoot "C:\xampp\htdocs\ex2app2"
    ServerName ex2.web2
</VirtualHost>

<VirtualHost *:88>
    ServerAdmin webmaster@ex2.web2
    DocumentRoot "C:\xampp\htdocs\ex2app2"
    ServerName ex2.web2
</VirtualHost>

<VirtualHost *:1234>
    ServerAdmin webmaster@localhost
    DocumentRoot "C:\xampp\htdocs"
    ServerName localhost
</VirtualHost>

2.	Vào C:\Windows\System32\drivers\etc\hosts
Thêm dòng 127.0.0.1 ex2.web2 và lưu dưới quyền admin
3.	Vào appche ở xampp
Thêm
Listen 80
Listen 88
Listen 1234

ServerName localhost:80
ServerName ex2.web1:88
ServerName ex2.web2:1234

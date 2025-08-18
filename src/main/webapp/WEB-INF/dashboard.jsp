<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <%-- Sử dụng biến title được gửi từ AdminController --%>
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <%-- Bạn nên tạo file CSS riêng thay vì dùng style inline --%>
    <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            margin: 0;
        }

        header {
            flex: 0 0 auto;
        }

        footer {
            flex: 0 0 auto;
        }

        .content {
            flex: 1 0 auto;
            display: flex;
        }

        /* Sidebar chiếm toàn bộ chiều cao phần content */
        .sidebar {
            background-color: #acc;
            padding: 15px;
            min-height: 100%; /* đầy đủ chiều cao phần content */
        }

        /* Tùy chỉnh các link */
        .sidebar a {
            display: block;
            padding: 10px 15px;
            color: #333;
            text-decoration: none;
            border-radius: 5px;
        }

        .sidebar a:hover {
            background-color: #e9ecef;
        }
    </style>
</head>
<body>
<header class="bg-primary text-white p-3">
    <%-- Sử dụng biến title ở header --%>
    <h3 class="text-center mb-0">${title}</h3>
</header>

<div class="container-fluid content">
    <div class="row flex-grow-1">
        <nav class="col-md-3 col-lg-2 d-md-block sidebar">
            <%-- Cập nhật các đường link để trỏ đến AdminController --%>
            <a href="admin?view=dashboard">Dashboard tổng quát</a>
            <a href="admin?view=categories">Danh sách loại sản phẩm</a>
            <a href="admin?view=products">Danh sách sản phẩm</a>

            <div class="mt-auto logout-btn">
                <a href="logout"><i class="fa fa-sign-out-alt me-2"></i> Đăng xuất</a>
            </div>
        </nav>

        <main class="col-md-9 ms-sm-auto col-lg-10 p-4">
            <%-- Nội dung động sẽ được nạp vào đây --%>
            <c:if test="${pageContent != null}">
                <jsp:include page="${pageContent}"/>
            </c:if>
        </main>
    </div>
</div>

<footer class="text-center bg-primary text-white p-3">
    <h4 class="text-center mb-0">&copy; 2025 - Hệ thống quản lý sản phẩm</h4>
</footer>
</body>
</html>
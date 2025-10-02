<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #007bff;
            --secondary-color: #6c757d;
            --background-light: #f8f9fa;
            --background-dark: #343a40;
            --text-dark: #212529;
            --text-light: #f8f9fa;
            --sidebar-bg: #495057;
            --sidebar-hover: #6c757d;
        }

        body {
            display: flex;
            min-height: 100vh;
            flex-direction: column;
            background-color: var(--background-light);
            color: var(--text-dark);
        }

        .header {
            background-color: var(--background-dark);
            color: var(--text-light);
            padding: 1rem;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .content-wrapper {
            display: flex;
            flex-grow: 1;
        }

        .sidebar {
            width: 250px;
            background-color: var(--sidebar-bg);
            color: var(--text-light);
            padding: 1rem 0;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
            position: sticky;
            top: 0;
            height: 100vh;
        }

        .sidebar a {
            display: block;
            padding: 1rem 1.5rem;
            color: rgba(255, 255, 255, 0.8);
            text-decoration: none;
            transition: background-color 0.3s, color 0.3s;
        }

        .sidebar a:hover {
            background-color: var(--sidebar-hover);
            color: white;
        }

        .sidebar a.active {
            background-color: var(--primary-color);
            color: white;
            font-weight: bold;
        }

        .main-content {
            flex-grow: 1;
            padding: 1.5rem;
        }

        .footer {
            background-color: var(--background-dark);
            color: var(--text-light);
            padding: 1rem;
            text-align: center;
        }

    </style>
</head>
<body>
<header class="header text-center">
    <h3>${title}</h3>
</header>

<div class="content-wrapper">
    <nav class="sidebar d-md-block">
        <div class="d-flex flex-column h-100">
            <a href="admin?view=dashboard" class="${param.view == 'dashboard' ? 'active' : ''}">
                <i class="fa fa-tachometer-alt me-2"></i> Dashboard Tổng quan
            </a>
            <a href="admin?view=users" class="${param.view == 'users' ? 'active' : ''}">
                <i class="fa fa-users me-2"></i> Danh sách người dùng
            </a>
            <a href="admin?view=categories" class="${param.view == 'categories' ? 'active' : ''}">
                <i class="fa fa-list me-2"></i> Danh sách loại sản phẩm
            </a>
            <a href="admin?view=products" class="${param.view == 'products' ? 'active' : ''}">
                <i class="fa fa-box me-2"></i> Danh sách sản phẩm
            </a>
            <a href="admin?view=orders" class="${param.view == 'orders' ? 'active' : ''}">
                <i class="fa fa-shopping-cart me-2"></i> Danh sách đơn hàng
            </a>
            <div class="mt-auto">
                <a href="logout">
                    <i class="fa fa-sign-out-alt me-2"></i> Đăng xuất
                </a>
            </div>
        </div>
    </nav>

    <main class="main-content">
        <c:if test="${pageContent != null}">
            <jsp:include page="${pageContent}"/>
        </c:if>
    </main>
</div>

<footer class="footer">
    &copy; 2025 - Hệ thống quản lý sản phẩm
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
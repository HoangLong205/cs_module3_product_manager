<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String ctx = request.getContextPath();
    request.setAttribute("ctx", ctx);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${pageTitle != null ? pageTitle : 'Shop'}"/></title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/assets/css/theme.css" rel="stylesheet">
</head>
<body class="bg-deep">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark-blue shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold text-black" href="${ctx}/">ElectroShop</a>
        <form class="d-flex ms-auto" role="search" action="${ctx}/home" method="get">
            <input class="form-control me-2" type="search" placeholder="Tìm sản phẩm" name="q" value="${q}">
            <button class="btn btn-primary" type="submit">Tìm</button>
        </form>
        <ul class="navbar-nav ms-3">
            <!-- Giỏ hàng -->
            <li class="nav-item">
                <a class="btn btn-outline-primary me-2" href="${ctx}/cart">Giỏ hàng</a>
            </li>

            <!-- Dropdown user -->
            <li class="nav-item dropdown">
                <a class="btn btn-outline-success dropdown-toggle" href="#" role="button"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    <c:out value="${sessionScope.user != null ? sessionScope.user.username : 'Khách'}"/>
                </a>
                <ul class="dropdown-menu dropdown-menu-end">
                    <li><a class="dropdown-item" href="${ctx}/logout">Đăng xuất</a></li>
                    <li><a class="dropdown-item" href="${ctx}/orders">Thông tin đơn hàng</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
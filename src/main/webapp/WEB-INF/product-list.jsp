<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Danh sách sản phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>Danh sách sản phẩm</h2>
<!-- Form tìm kiếm -->
<form action="products" method="get">
    <input type="hidden" name="action" value="search">
    <input type="text" name="keyword" placeholder="Nhập từ khóa...">
    <select name="field">
        <option value="name">Tên sản phẩm</option>
        <option value="category">Loại sản phẩm</option>
    </select>
    <button type="submit">Tìm kiếm</button>
</form>
<a href="products?action=new" class="btn btn-primary mb-3">Thêm sản phẩm</a>
<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Ảnh</th>
        <th>Danh mục</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.quantity}</td>
            <td><img src="${product.image}" width="80" alt=""></td>
            <td>${product.category.name}</td>
            <td>
                <a href="products?action=edit&id=${product.id}" class="btn btn-warning btn-sm">Sửa</a>
                <a href="products?action=delete&id=${product.id}" class="btn btn-danger btn-sm"
                   onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

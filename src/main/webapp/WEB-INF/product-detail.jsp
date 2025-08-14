<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
</head>
<body>
<h2>Thông tin chi tiết sản phẩm</h2>
<table border="1" cellpadding="10">
    <tr>
        <th>ID</th>
        <td>${product.id}</td>
    </tr>
    <tr>
        <th>Tên sản phẩm</th>
        <td>${product.name}</td>
    </tr>
    <tr>
        <th>Số lượng</th>
        <td>${product.quantity}</td>
    </tr>
    <tr>
        <th>Giá</th>
        <td>${product.price}</td>
    </tr>
    <tr>
        <th>Loại sản phẩm</th>
        <td>${product.category.name}</td>
    </tr>
    <tr>
        <th>Mô tả sản phẩm</th>
        <td>${product.description}</td>
    </tr>
</table>
<br>
<a href="products">Quay lại danh sách</a>
</body>
</html>

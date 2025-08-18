<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<html lang="vi">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Biểu mẫu loại sản phẩm</title>--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">--%>
<%--</head>--%>
<%--<body class="container mt-4">--%>
<h2>${category != null ? "Cập nhật" : "Thêm"} loại sản phẩm</h2>
<form method="post" action="categories">
    <input type="hidden" name="id" value="${category.id}">
    <div class="mb-3">
        <label for="name">Tên loại</label>
        <input id="name" type="text" class="form-control" name="name" value="${category.name}" required>
    </div>
    <button type="submit" class="btn btn-success">💾 Lưu</button>
    <a href="admin?view=categories" class="btn btn-secondary">↩ Hủy</a>
</form>
<%--</body>--%>
<%--</html>--%>

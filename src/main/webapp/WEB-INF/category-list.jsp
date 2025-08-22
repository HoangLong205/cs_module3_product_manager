<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<!DOCTYPE html>--%>
<%--<html lang="vi">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <title>Danh sách loại sản phẩm</title>--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">--%>
<%--</head>--%>
<%--<body class="container mt-4">--%>
<!-- Form tìm kiếm -->
<%--<form action="categories" method="get" class="row mb-3">--%>
<%--    <div class="col-auto">--%>
<%--        <input type="hidden" name="action" value="search" class="form-control"/>--%>
<%--    </div>--%>
<%--    <div class="col-auto">--%>
<%--        <label>--%>
<%--            <input type="text" name="name" placeholder="Nhập tên loại sản phẩm..."/>--%>
<%--        </label>--%>
<%--    </div>--%>
<%--    <div class="col-auto">--%>
<%--        <input type="submit" value="Tìm kiếm"/>--%>
<%--    </div>--%>
<%--</form>--%>
<form action="admin" method="get" class="mb-3">
    <input type="hidden" name="view" value="categories"> <%-- Quan trọng --%>
    <div class="input-group">
        <label>
            <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo tên..." value="${searchKeyword}" >
        </label>
        <button type="submit" class="btn btn-primary">🔍 Tìm kiếm</button>
    </div>
</form>

<a href="categories?action=new" class="btn btn-success mb-3">Thêm loại sản phẩm</a>

<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>Tên loại</th>
        <th>Hành động</th>
    </tr>
    <c:forEach var="cat" items="${list}">
        <tr>
            <td>${cat.id}</td>
            <td>${cat.name}</td>
            <td>
                <a href="categories?action=edit&id=${cat.id}" class="btn btn-warning btn-sm">Sửa</a>
                <a href="categories?action=delete&id=${cat.id}" class="btn btn-danger btn-sm"
                   onclick="return confirm('Bạn có chắc muốn xóa loại sản phẩm này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
<!-- Phân trang Bootstrap -->
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-end">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="admin?view=categories&page=${currentPage-1}&search=${searchKeyword}">Previous</a>
        </li>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="admin?view=categories&page=${i}&search=${searchKeyword}">${i}</a>
            </li>
        </c:forEach>

        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
            <a class="page-link" href="admin?view=categories&page=${currentPage+1}&search=${searchKeyword}">Next</a>
        </li>
    </ul>
</nav>
<%--</body>--%>
<%--</html>--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Danh sách sản phẩm</title>--%>
<%--    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">--%>
<%--</head>--%>
<%--<body class="container mt-4">--%>
<h2>Danh sách sản phẩm</h2>
<!-- Form tìm kiếm -->
<%--<form action="products" method="get">--%>
<%--    <input type="hidden" name="action" value="search">--%>
<%--    <input type="text" name="keyword" placeholder="Nhập từ khóa...">--%>
<%--    <select name="field">--%>
<%--        <option value="name">Tên sản phẩm</option>--%>
<%--        <option value="category">Loại sản phẩm</option>--%>
<%--    </select>--%>
<%--    <button type="submit">Tìm kiếm</button>--%>
<%--</form>--%>
<form action="admin" method="get" class="mb-3">
    <input type="hidden" name="view" value="products"> <%-- Quan trọng --%>
    <div class="input-group">
        <label>
            <input type="text" name="keyword" class="form-control" placeholder="Nhập từ khóa..." value="${keyword}">
        </label>
        <label>
            <select name="field" class="form-select" >
                <option value="name" ${field == 'name' ? 'selected' : ''}>Tên sản phẩm</option>
                <option value="category" ${field == 'category' ? 'selected' : ''}>Loại sản phẩm</option>
            </select>
        </label>
        <button type="submit" class="btn btn-primary">🔍 Tìm kiếm</button>
    </div>
</form>

<a href="products?action=new" class="btn btn-success mb-3">Thêm sản phẩm</a>
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
                <a href="products?action=view&id=${product.id}" class="btn btn-info btn-sm">Xem chi tiết</a>
                <a href="products?action=edit&id=${product.id}" class="btn btn-warning btn-sm">Sửa</a>
                <a href="products?action=delete&id=${product.id}" class="btn btn-danger btn-sm"
                   onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
            </td>
        </tr>
    </c:forEach>
</table>
<!-- Phân trang Bootstrap -->
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-end">
        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
            <a class="page-link" href="admin?view=products&page=${currentPage-1}&keyword=${keyword}&field=${field}">Previous</a>
        </li>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <a class="page-link" href="admin?view=products&page=${i}&keyword=${keyword}&field=${field}">${i}</a>
            </li>
        </c:forEach>

        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
            <a class="page-link" href="admin?view=products&page=${currentPage+1}&keyword=${keyword}&field=${field}">Next</a>
        </li>
    </ul>
</nav>
<%--</body>--%>
<%--</html>--%>

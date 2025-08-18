<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%-- Chỉ giữ lại nội dung chính --%>
<h2>Thông tin chi tiết sản phẩm</h2>
<c:if test="${product != null}">
    <table class="table table-bordered">
        <tr>
            <th style="width: 20%;">ID</th>
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
            <td><p>${product.description}</p></td>
        </tr>
        <tr>
            <th>Ảnh</th>
            <td><img src="${product.image}" alt="Ảnh sản phẩm" style="max-width: 200px;"/></td>
        </tr>
    </table>
</c:if>
<c:if test="${product == null}">
    <div class="alert alert-warning">Không tìm thấy sản phẩm.</div>
</c:if>

<%-- Sửa link quay lại --%>
<a href="admin?view=products" class="btn btn-secondary">↩ Quay lại danh sách</a>
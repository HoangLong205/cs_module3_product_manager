<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <p><b>Khách hàng:</b> ${order.user.username}</p>
    <p><b>Ngày đặt:</b> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></p>
    <p><b>Tổng tiền:</b> <fmt:formatNumber value="${order.total}" type="number" groupingUsed="true"/> VND</p>
    <p><b>Trạng thái:</b> ${order.status}</p>

    <h4>Sản phẩm</h4>
    <table class="table table-bordered">
        <thead class="table-light">
        <tr>
            <th>Ảnh</th>
            <th>Tên SP</th>
            <th>Số lượng</th>
            <th>Giá</th>
            <th>Thành tiền</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${items}">
            <tr>
                <td><img src="${item.productImage}" width="60"/></td>
                <td>${item.productName}</td>
                <td>${item.quantity}</td>
                <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VND</td>
                <td><fmt:formatNumber value="${item.quantity * item.price}" type="number" groupingUsed="true"/> VND</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="admin?view=orders" class="btn btn-secondary">⬅ Quay lại</a>
</div>

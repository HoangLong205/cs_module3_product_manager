<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid mt-4">
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-info text-white">
            <h5 class="mb-0">Thông tin đơn hàng #${order.id}</h5>
        </div>
        <div class="card-body">
            <p><b>Khách hàng:</b> ${order.user.username}</p>
            <p><b>Ngày đặt:</b> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></p>
            <p><b>Tổng tiền:</b> <fmt:formatNumber value="${order.total}" type="number" groupingUsed="true"/> VND</p>
            <p><b>Trạng thái:</b> <span class="badge bg-primary">${order.status}</span></p>
        </div>
    </div>

    <h4 class="mb-3">Sản phẩm trong đơn hàng</h4>
    <div class="table-responsive shadow-sm rounded">
        <table class="table table-bordered table-striped mb-0">
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
                    <td><img src="${item.productImage}" width="60" class="rounded"/></td>
                    <td>${item.productName}</td>
                    <td>${item.quantity}</td>
                    <td><fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VND</td>
                    <td><fmt:formatNumber value="${item.quantity * item.price}" type="number" groupingUsed="true"/> VND</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>
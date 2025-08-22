<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container mt-4">
    <div class="table-responsive shadow-sm rounded">
        <table class="table table-striped">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Khách hàng</th>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>#${order.id}</td>
                    <td>${order.user.username}</td>
                    <td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" /></td>
                    <td><fmt:formatNumber value="${order.total}" type="number" groupingUsed="true"/> VND</td>
                    <td>
                        <!-- Dropdown update status -->
                        <form action="admin" method="get">
                            <input type="hidden" name="view" value="updateOrderStatus"/>
                            <input type="hidden" name="id" value="${order.id}"/>
                            <select name="status" onchange="this.form.submit()" class="form-select form-select-sm">
                                <c:forEach var="st" items="${['PENDING','CONFIRMED','COMPLETED','CANCELLED']}">
                                    <option value="${st}" ${order.status == st ? 'selected' : ''}>${st}</option>
                                </c:forEach>
                            </select>
                        </form>
                    </td>
                    <td>
                        <!-- Xem chi tiết -->
                        <a href="admin?view=orderDetail&id=${order.id}" class="btn btn-sm btn-info">👁 Xem</a>

                        <!-- Xóa -->
                        <a href="admin?view=deleteOrder&id=${order.id}"
                           onclick="return confirm('Bạn có chắc muốn xóa đơn hàng này?');"
                           class="btn btn-sm btn-danger">🗑 Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid mt-4">
    <h3>Danh sách đơn hàng</h3>
    <div class="table-responsive shadow-sm rounded">
        <table class="table table-striped table-hover mb-0">
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
                        <button class="btn btn-sm btn-info view-order-detail" data-id="${order.id}">
                            <i class="fas fa-eye"></i> Xem chi tiết
                        </button>
                        <a href="admin?view=deleteOrder&id=${order.id}"
                           onclick="return confirm('Bạn có chắc muốn xóa đơn hàng này?');"
                           class="btn btn-sm btn-danger">
                            <i class="fas fa-trash-alt"></i> Xóa
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="modal fade" id="orderDetailModal" tabindex="-1" aria-labelledby="orderDetailModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="orderDetailModalLabel">Chi tiết đơn hàng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="orderDetailContent">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const orderDetailModal = new bootstrap.Modal(document.getElementById('orderDetailModal'));
        const detailContent = document.getElementById('orderDetailContent');

        document.querySelectorAll('.view-order-detail').forEach(button => {
            button.addEventListener('click', function() {
                const orderId = this.getAttribute('data-id');
                detailContent.innerHTML = 'Đang tải...';

                // Gửi yêu cầu AJAX để lấy nội dung chi tiết đơn hàng
                fetch('admin?view=orderDetail&id=' + orderId)
                    .then(response => response.text())
                    .then(html => {
                        detailContent.innerHTML = html;
                        orderDetailModal.show();
                    })
                    .catch(error => {
                        console.error('Lỗi khi tải chi tiết đơn hàng:', error);
                        detailContent.innerHTML = '<div class="alert alert-danger">Không thể tải thông tin chi tiết.</div>';
                        orderDetailModal.show();
                    });
            });
        });
    });
</script>
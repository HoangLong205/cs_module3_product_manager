<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/18/2025
  Time: 5:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container mt-4">
  <h3>Giỏ hàng của bạn</h3>
  <table class="table table-striped">
    <thead class="table-dark">
    <tr>
      <th>Ảnh</th>
      <th>Tên sản phẩm</th>
      <th>Giá</th>
      <th>Số lượng</th>
      <th>Thành tiền</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${cartItems}">
      <tr>
        <td><img src="${item.product.image}" width="80"></td>
        <td>${item.product.name}</td>
        <td>${item.product.price} đ</td>
        <td>${item.quantity}</td>
        <td>${item.totalPrice} đ</td>
        <td>
          <a href="${pageContext.request.contextPath}/cart/remove?id=${item.product.id}"
             class="btn btn-danger btn-sm">Xóa</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div class="d-flex justify-content-end">
    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary btn-lg">Thanh toán</a>
  </div>
</div>
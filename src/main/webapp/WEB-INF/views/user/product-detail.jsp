<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/18/2025
  Time: 5:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container mt-4">
  <div class="row">
    <div class="col-md-5">
      <img src="${product.image}" class="img-fluid rounded shadow">
    </div>
    <div class="col-md-7">
      <h2>${product.name}</h2>
      <p>Hãng: ${product.brand}</p>
      <p>Phân loại: ${product.category}</p>
      <p>Ngày cập nhật: ${product.updatedAt}</p>
      <p class="fw-bold fs-4 text-danger">${product.price} đ</p>
      <p>${product.description}</p>
      <a href="${pageContext.request.contextPath}/cart/add?id=${product.id}"
         class="btn btn-success btn-lg">Thêm vào giỏ hàng</a>
    </div>
  </div>
</div>

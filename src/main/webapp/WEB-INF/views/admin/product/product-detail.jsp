<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/18/2025
  Time: 5:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container mt-4">
  <div class="row">
    <div class="col-md-5">
      <img src="${product.image}" class="img-fluid rounded shadow">
    </div>
    <div class="col-md-7">
      <h2>${product.name}</h2>
      <p><strong>Phân loại:</strong> ${product.category}</p>
      <p><strong>Ngày cập nhật:</strong> ${product.updatedAt}</p>
      <p class="fw-bold fs-4 text-danger">${product.price} đ</p>
      <p><strong>Mô tả:</strong> ${product.description}</p>

      <a href="${pageContext.request.contextPath}/admin/product/product-edit?id=${product.id}"
         class="btn btn-warning btn-lg">Chỉnh sửa sản phẩm</a>
    </div>
  </div>
</div>

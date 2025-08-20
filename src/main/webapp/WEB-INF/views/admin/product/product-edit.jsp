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
  <div class="card shadow p-4">
    <h3 class="text-primary">Chỉnh sửa sản phẩm</h3>
    <form action="${pageContext.request.contextPath}/admin/product/update" method="post">
      <input type="hidden" name="id" value="${product.id}"/>

      <div class="mb-3">
        <label class="form-label">Tên sản phẩm</label>
        <input type="text" class="form-control" name="name" value="${product.name}" required/>
      </div>
      <div class="mb-3">
        <label class="form-label">Phân loại</label>
        <input type="text" class="form-control" name="category" value="${product.category}" required/>
      </div>
      <div class="mb-3">
        <label class="form-label">Giá</label>
        <input type="number" class="form-control" name="price" value="${product.price}" required/>
      </div>
      <div class="mb-3">
        <label class="form-label">Mô tả</label>
        <textarea class="form-control" rows="4" name="description">${product.description}</textarea>
      </div>
      <div class="mb-3">
        <label class="form-label">Ảnh (URL)</label>
        <input type="text" class="form-control" name="image" value="${product.image}" required/>
      </div>

      <button type="submit" class="btn btn-success">Lưu thay đổi</button>
      <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Hủy</a>
    </form>
  </div>
</div>


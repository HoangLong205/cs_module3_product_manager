<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>${product != null ? "Cập nhật" : "Thêm"} sản phẩm</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>${product != null ? "Cập nhật" : "Thêm"} sản phẩm</h2>
<form method="post" action="products">
  <input type="hidden" name="id" value="${product.id}">
  <div class="mb-3">
    <label>Tên sản phẩm</label>
    <input type="text" class="form-control" name="name" value="${product.name}" required>
  </div>
  <div class="mb-3">
    <label>Giá</label>
    <input type="number" step="0.01" class="form-control" name="price" value="${product.price}" required>
  </div>
  <div class="mb-3">
    <label>Số lượng</label>
    <input type="number" class="form-control" name="quantity" value="${product.quantity}" required>
  </div>
  <div class="mb-3">
    <label>Ảnh (URL)</label>
    <input type="text" class="form-control" name="image" value="${product.image}" required>
  </div>
  <div class="mb-3">
    <label>Mô tả</label>
    <textarea class="form-control" name="description">${product.description}</textarea>
  </div>
  <div class="mb-3">
    <label>ID Danh mục</label>
    <input type="number" class="form-control" name="category_id" value="${product.categoryId}" required>
  </div>
  <button type="submit" class="btn btn-success">Lưu</button>
  <a href="products" class="btn btn-secondary">Hủy</a>
</form>
</body>
</html>

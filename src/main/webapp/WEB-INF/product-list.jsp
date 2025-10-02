<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<div class="container-fluid mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Danh sách sản phẩm</h3>
        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#productModal"
                onclick="clearProductForm()">
            <i class="fas fa-plus-circle me-2"></i> Thêm mới
        </button>
    </div>

    <form action="admin" method="get" class="mb-3">
        <input type="hidden" name="view" value="products">
        <div class="input-group">
            <input type="text" name="keyword" class="form-control" placeholder="Nhập từ khóa..." value="${keyword}">
            <select name="field" class="form-select">
                <option value="name" ${field == 'name' ? 'selected' : ''}>Tên sản phẩm</option>
                <option value="category" ${field == 'category' ? 'selected' : ''}>Loại sản phẩm</option>
            </select>
            <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
        </div>
    </form>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table table-hover mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Ảnh</th>
                    <th>Tên</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Danh mục</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.id}</td>
                        <td><img src="${product.image}" width="80" alt="Ảnh sản phẩm"></td>
                        <td>${product.name}</td>
                        <td><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> VND</td>
                        <td>${product.quantity}</td>
                        <td>${product.category.name}</td>
                        <td>
                            <button class="btn btn-info btn-sm view-product-detail" data-id="${product.id}">
                                <i class="fas fa-eye"></i> Xem chi tiết
                            </button>
                            <button class="btn btn-warning btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#productModal"
                                    onclick="editProduct(
                                            '${product.id}',
                                            '${product.name}',
                                            '${product.price}',
                                            '${product.quantity}',
                                            '${product.image}',
                                            '${product.description}',
                                            '${product.category.id}'
                                            )">
                                <i class="fas fa-edit"></i> Sửa
                            </button>
                            <a href="products?action=delete&id=${product.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('Xóa sản phẩm này?')">
                                <i class="fas fa-trash-alt"></i> Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="productModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="productModalLabel">Thêm sản phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="productForm" method="post" action="products">
                    <div class="modal-body">
                        <input type="hidden" name="id" id="productId">
                        <div class="mb-3">
                            <label for="productName" class="form-label">Tên sản phẩm</label>
                            <input id="productName" type="text" class="form-control" name="name" required>
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="productPrice" class="form-label">Giá</label>
                                <input id="productPrice" type="number" step="0.01" class="form-control" name="price"
                                       required>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="productQuantity" class="form-label">Số lượng</label>
                                <input id="productQuantity" type="number" class="form-control" name="quantity" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="productImage" class="form-label">Ảnh (URL)</label>
                            <input id="productImage" type="text" class="form-control" name="image" required>
                        </div>
                        <div class="mb-3">
                            <label for="productDescription" class="form-label">Mô tả</label>
                            <textarea id="productDescription" class="form-control" name="description"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="productCategoryId" class="form-label">Danh Mục</label>
                            <select class="form-select" id="productCategoryId" name="category_id" required>
                                <option value="">-- Chọn Danh Mục --</option>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.id}">
                                        <c:out value="${category.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                        <button type="submit" class="btn btn-success">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <nav aria-label="Page navigation" class="mt-4">
        <ul class="pagination justify-content-end">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="admin?view=products&page=${currentPage-1}&keyword=${keyword}&field=${field}">Trước</a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="admin?view=products&page=${i}&keyword=${keyword}&field=${field}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="admin?view=products&page=${currentPage+1}&keyword=${keyword}&field=${field}">Tiếp</a>
            </li>
        </ul>
    </nav>
    <div class="modal fade" id="productDetailModal" tabindex="-1" aria-labelledby="productDetailModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="productDetailModalLabel">Chi tiết sản phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="productDetailContent">
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
    function editProduct(id, name, price, quantity, image, description, categoryId) {
        document.getElementById('productId').value = id;
        document.getElementById('productName').value = name;
        document.getElementById('productPrice').value = price;
        document.getElementById('productQuantity').value = quantity;
        document.getElementById('productImage').value = image;
        document.getElementById('productDescription').value = description;
        document.getElementById('productCategoryId').value = categoryId;
        document.getElementById('productModalLabel').innerText = 'Cập nhật sản phẩm';
    }

    function clearProductForm() {
        document.getElementById('productId').value = '';
        document.getElementById('productName').value = '';
        document.getElementById('productPrice').value = '';
        document.getElementById('productQuantity').value = '';
        document.getElementById('productImage').value = '';
        document.getElementById('productDescription').value = '';
        document.getElementById('productCategoryId').selectedIndex = 0;
        document.getElementById('productModalLabel').innerText = 'Thêm sản phẩm';
    }

    document.addEventListener('DOMContentLoaded', function () {
        const productDetailModal = new bootstrap.Modal(document.getElementById('productDetailModal'));
        const detailContent = document.getElementById('productDetailContent');

        document.querySelectorAll('.view-product-detail').forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.getAttribute('data-id');
                detailContent.innerHTML = 'Đang tải...';

                // Gửi yêu cầu AJAX để lấy nội dung chi tiết sản phẩm
                fetch('admin?view=product-detail&id=' + productId)
                    .then(response => response.text())
                    .then(html => {
                        detailContent.innerHTML = html;
                        productDetailModal.show();
                    })
                    .catch(error => {
                        console.error('Lỗi khi tải chi tiết sản phẩm:', error);
                        detailContent.innerHTML = '<div class="alert alert-danger">Không thể tải thông tin chi tiết.</div>';
                        productDetailModal.show();
                    });
            });
        });
    });
</script>
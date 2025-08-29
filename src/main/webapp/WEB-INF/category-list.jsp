<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Danh sách loại sản phẩm</h3>
        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#categoryModal" onclick="clearForm()">
            <i class="fas fa-plus-circle me-2"></i> Thêm mới
        </button>
    </div>

    <form action="admin" method="get" class="mb-3">
        <input type="hidden" name="view" value="categories">
        <div class="input-group">
            <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo tên..." value="${searchKeyword}">
            <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
        </div>
    </form>

    <div class="card shadow-sm">
        <div class="card-body">
            <table class="table table-hover mb-0">
                <thead class="table-light">
                <tr>
                    <th>ID</th>
                    <th>Tên loại</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="cat" items="${list}">
                    <tr>
                        <td>${cat.id}</td>
                        <td>${cat.name}</td>
                        <td>
                            <button class="btn btn-warning btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#categoryModal"
                                    onclick="editCategory('${cat.id}', '${cat.name}')">
                                <i class="fas fa-edit"></i> Sửa
                            </button>
                            <a href="categories?action=delete&id=${cat.id}" class="btn btn-danger btn-sm"
                               onclick="return confirm('Bạn có chắc muốn xóa loại sản phẩm này?')">
                                <i class="fas fa-trash-alt"></i> Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="modal fade" id="categoryModal" tabindex="-1" aria-labelledby="categoryModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="categoryModalLabel">Thêm loại sản phẩm</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form id="categoryForm" method="post" action="categories">
                    <div class="modal-body">
                        <input type="hidden" name="id" id="categoryId">
                        <div class="mb-3">
                            <label for="categoryName" class="form-label">Tên loại</label>
                            <input id="categoryName" type="text" class="form-control" name="name" required>
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
                <a class="page-link" href="admin?view=categories&page=${currentPage-1}&search=${searchKeyword}">Trước</a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${i == currentPage ? 'active' : ''}">
                    <a class="page-link" href="admin?view=categories&page=${i}&search=${searchKeyword}">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="admin?view=categories&page=${currentPage+1}&search=${searchKeyword}">Tiếp</a>
            </li>
        </ul>
    </nav>
</div>

<script>
    function editCategory(id, name) {
        document.getElementById('categoryId').value = id;
        document.getElementById('categoryName').value = name;
        document.getElementById('categoryModalLabel').innerText = 'Cập nhật loại sản phẩm';
    }

    function clearForm() {
        document.getElementById('categoryId').value = '';
        document.getElementById('categoryName').value = '';
        document.getElementById('categoryModalLabel').innerText = 'Thêm loại sản phẩm';
    }
</script>
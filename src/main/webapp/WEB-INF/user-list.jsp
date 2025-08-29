<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container-fluid mt-4">
  <h3>Danh sách người dùng</h3>
  <form action="admin" method="get" class="row mb-3 g-2">
    <input type="hidden" name="view" value="users" />
    <div class="col-md-4">
      <input type="text" name="search" class="form-control"
             placeholder="Tìm kiếm theo tên..." value="${searchKeyword}">
    </div>
    <div class="col-md-2">
      <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Tìm kiếm</button>
    </div>
  </form>

  <div class="card shadow-sm">
    <div class="card-body">
      <table class="table table-bordered table-striped table-hover mb-0">
        <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Username</th>
          <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
          <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.role}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty users}">
          <tr>
            <td colspan="3" class="text-center text-muted">Không có dữ liệu</td>
          </tr>
        </c:if>
        </tbody>
      </table>
    </div>
  </div>

  <nav aria-label="Page navigation" class="mt-4">
    <ul class="pagination justify-content-end">
      <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
        <a class="page-link"
           href="admin?view=users&page=${currentPage-1}&search=${searchKeyword}">
          Trước
        </a>
      </li>
      <c:forEach begin="1" end="${totalPages}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
          <a class="page-link"
             href="admin?view=users&page=${i}&search=${searchKeyword}">
              ${i}
          </a>
        </li>
      </c:forEach>
      <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
        <a class="page-link"
           href="admin?view=users&page=${currentPage+1}&search=${searchKeyword}">
          Tiếp
        </a>
      </li>
    </ul>
  </nav>
</div>
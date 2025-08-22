<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mt-4">

  <!-- Search box -->
  <form action="admin" method="get" class="row mb-3">
    <input type="hidden" name="view" value="users" />
    <div class="col-md-4">
      <input type="text" name="search" class="form-control"
             placeholder="Tìm kiếm theo tên..." value="${searchKeyword}">
    </div>
    <div class="col-md-2">
      <button type="submit" class="btn btn-primary">Tìm kiếm</button>
    </div>
  </form>

  <!-- User table -->
  <table class="table table-bordered table-striped">
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

  <!-- Pagination -->
  <nav aria-label="Page navigation">
    <ul class="pagination justify-content-end">

      <!-- Previous -->
      <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
        <a class="page-link"
           href="admin?view=users&page=${currentPage-1}&search=${searchKeyword}">
          Previous
        </a>
      </li>

      <!-- Page numbers -->
      <c:forEach begin="1" end="${totalPages}" var="i">
        <li class="page-item ${i == currentPage ? 'active' : ''}">
          <a class="page-link"
             href="admin?view=users&page=${i}&search=${searchKeyword}">
              ${i}
          </a>
        </li>
      </c:forEach>

      <!-- Next -->
      <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
        <a class="page-link"
           href="admin?view=users&page=${currentPage+1}&search=${searchKeyword}">
          Next
        </a>
      </li>

    </ul>
  </nav>

</div>

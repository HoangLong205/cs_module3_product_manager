<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/18/2025
  Time: 5:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<div class="container mt-4">
  <div class="d-flex justify-content-between mb-3">
    <form class="d-flex w-50" action="${pageContext.request.contextPath}/search" method="get">
      <input class="form-control me-2" type="search" name="keyword" placeholder="Tìm kiếm sản phẩm...">
      <button class="btn btn-outline-primary"><i class="bi bi-search"></i></button>
    </form>
    <c:if test="${sessionScope.account.role eq 'admin'}">
      <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-success">
        (+) Thêm sản phẩm
      </a>
    </c:if>
  </div>

  <div class="row">
    <c:forEach var="p" items="${products}">
      <div class="col-md-3 mb-4">
        <div class="card shadow h-100">
          <img src="${p.image}" class="card-img-top" alt="${p.name}" style="height:200px;object-fit:cover;">
          <div class="card-body">
            <h5 class="card-title">${p.name}</h5>
            <p class="card-text">${p.category} - ${p.brand}</p>
            <p class="text-danger fw-bold">${p.price} đ</p>
          </div>
          <div class="card-footer text-center">
            <c:choose>
              <c:when test="${sessionScope.account.role eq 'admin'}">
                <a href="${pageContext.request.contextPath}/admin/product/edit?id=${p.id}"
                   class="btn btn-warning w-100">Chỉnh sửa</a>
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/user/product/detail?id=${p.id}"
                   class="btn btn-primary w-100 mb-2">Xem chi tiết</a>
                <a href="${pageContext.request.contextPath}/cart/add?id=${p.id}"
                   class="btn btn-success w-100">Thêm giỏ hàng</a>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>
<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/25/2025
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<h3 class="text-white mb-3">Sản phẩm<c:if test="${not empty q}"> — kết quả cho "${q}"</c:if></h3>


<c:choose>
  <c:when test="${empty products}">
    <div class="alert alert-info">Không tìm thấy sản phẩm phù hợp.</div>
  </c:when>
  <c:otherwise>
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 g-4">
    <c:forEach var="p" items="${products}">
      <div class="col">
        <div class="card h-100 product-card">
          <a class="text-decoration-none" href="${ctx}/product/detail?id=${p.id}">
            <div class="ratio ratio-4x3 overflow-hidden">
              <c:choose>
                <c:when test="${fn:startsWith(p.image, 'http')}">
                  <img src="${p.image}" class="card-img-top object-fit-cover" alt="${p.name}">
                </c:when>
                <c:otherwise>
                  <img src="${ctx}/images/${p.image}" class="card-img-top object-fit-cover" alt="${p.name}">
                </c:otherwise>
              </c:choose>
            </div>
            <div class="card-body">
              <h6 class="card-title text-dark-blue">${p.name}</h6>
              <p class="card-text mb-1"><span class="badge bg-secondary">Còn: ${p.quantity}</span></p>
              <p class="fw-bold text-primary">${p.price} ₫</p>
            </div>
          </a>
          <div class="card-footer bg-transparent border-0 pb-3 px-3">
            <c:choose>
              <c:when test="${p.quantity > 0}">
                <form action="${ctx}/cart" method="post" class="d-grid">
                  <input type="hidden" name="action" value="add">
                  <input type="hidden" name="id" value="${p.id}">
                  <button class="btn btn-primary" type="submit">Thêm vào giỏ</button>
                </form>
              </c:when>
              <c:otherwise>
                <button class="btn btn-secondary w-100" type="button" disabled>Hết hàng — Liên hệ</button>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </c:forEach>
    </div>

    <!-- Pagination -->
    <nav aria-label="Page nav" class="mt-4">
      <ul class="pagination justify-content-center">
        <c:forEach var="i" begin="1" end="${totalPages}">
          <li class="page-item ${i == page ? 'active' : ''}">
            <a class="page-link" href="${ctx}/home?page=${i}&q=${fn:escapeXml(q)}">${i}</a>
          </li>
        </c:forEach>
      </ul>
    </nav>
  </c:otherwise>
</c:choose>


<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/25/2025
  Time: 5:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>


<h3 class="text-white mb-3">Đơn hàng của bạn</h3>


<c:if test="${empty orders}">
  <div class="alert alert-info">Bạn chưa có đơn hàng nào.</div>
</c:if>


<div class="row g-3">
  <c:forEach var="o" items="${orders}">
    <div class="col-12">
      <div class="card shadow-sm">
        <div class="card-body">
          <div class="d-flex justify-content-between align-items-center">
            <div>
              <h6 class="mb-1">Đơn hàng #${o.id}</h6>
              <div class="text-muted small">Ngày: ${o.orderDate}</div>
            </div>
            <span class="badge bg-primary">${o.status}</span>
          </div>
          <hr/>
          <ul class="list-group list-group-flush">
            <c:forEach var="it" items="${orderItemsMap[o.id]}">
              <li class="list-group-item d-flex justify-content-between">
                <span>${it.productName} × ${it.quantity}</span>
                <span>${it.unitPrice} ₫</span>
              </li>
            </c:forEach>
          </ul>
          <div class="text-end mt-2 fw-bold">Tổng: ${o.total} ₫</div>
        </div>
      </div>
    </div>
  </c:forEach>
</div>


<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
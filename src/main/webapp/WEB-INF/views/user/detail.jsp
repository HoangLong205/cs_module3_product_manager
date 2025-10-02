<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<div class="row g-4">
    <div class="col-md-6">
        <div class="ratio ratio-4x3">
            <c:choose>
                <c:when test="${fn:startsWith(p.image, 'http')}">
                    <img src="${p.image}" class="rounded-3 shadow object-fit-cover" alt="${p.name}">
                </c:when>
                <c:otherwise>
                    <img src="${ctx}/images/${p.image}" class="rounded-3 shadow object-fit-cover" alt="${p.name}">
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="col-md-6">
        <h3 class="text-white">${p.name}</h3>
        <p class="lead text-aqua fw-bold">
            <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> ₫
        </p>
        <p><span class="badge ${p.quantity > 0 ? 'bg-success' : 'bg-danger'}">${p.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}</span></p>
        <hr/>
        <h5 class="text-white">Mô tả</h5>
        <p class="text-white-50">${p.description}</p>


        <c:if test="${p.quantity > 0}">
            <form action="${ctx}/cart" method="post" class="d-flex gap-2 mt-3">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="id" value="${p.id}">
                <input type="number" class="form-control" name="qty" min="1" max="${p.quantity}" value="1" style="max-width:120px">
                <button class="btn btn-primary" type="submit">Thêm vào giỏ</button>
            </form>
        </c:if>
    </div>
</div>


<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
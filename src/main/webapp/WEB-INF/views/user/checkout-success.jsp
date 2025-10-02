<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>


<c:set var="orderId" value="${sessionScope.lastOrderId}"/>
<div class="text-center text-black py-5">
    <h3>Thanh toán thành công!</h3>
    <p>Mã đơn hàng của bạn: <span class="text-black fw-bold">#${orderId}</span></p>
    <a class="btn btn-aqua mt-3" href="${ctx}/">Trở về trang chủ</a>
</div>


<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
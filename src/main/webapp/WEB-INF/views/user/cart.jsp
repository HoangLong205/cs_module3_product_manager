<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>


<c:set var="cart" value="${sessionScope.cart}"/>
<c:if test="${empty cart}">
    <div class="alert alert-info">Giỏ hàng đang trống.</div>
</c:if>


<c:if test="${not empty cart}">
    <div class="table-responsive">
        <table class="table table-dark table-striped align-middle">
            <thead><tr>
                <th>Sản phẩm</th><th>Phân loại</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th><th></th>
            </tr></thead>
            <tbody>
            <c:set var="total" value="0"/>
            <c:forEach var="entry" items="${cart}">
                <c:set var="ci" value="${entry.value}"/>
                <tr>
                    <td class="text-white">${ci.product.name}</td>
                    <td class="text-white-50">${ci.product.category.name}</td>
                    <td>
                        <fmt:formatNumber value="${ci.product.price}" type="number" groupingUsed="true"/> ₫
                    </td>
                    <td>
                        <form action="${ctx}/cart" method="post" class="d-flex gap-2">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="id" value="${ci.product.id}">
                            <input type="number" class="form-control" name="qty" min="0" max="${ci.product.quantity}" value="${ci.quantity}" style="max-width:100px">
                            <button class="btn btn-outline-light" type="submit">Cập nhật</button>
                        </form>
                    </td>
                    <td>
                        <fmt:formatNumber value="${ci.lineTotal}" type="number" groupingUsed="true"/> ₫
                    </td>
                    <td>
                        <a href="${ctx}/cart?action=remove&id=${ci.product.id}" class="btn btn-sm btn-danger">Xoá</a>
                    </td>
                </tr>
                <c:set var="total" value="${total + ci.lineTotal}"/>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="4" class="text-end">Tổng cộng</th>
                <th colspan="2" class="text-aqua h5">
                    <fmt:formatNumber value="${total}" type="number" groupingUsed="true"/> ₫
                </th>
            </tr>
            </tfoot>
        </table>
    </div>
    <form action="${ctx}/checkout-success" method="post" class="text-end">
        <button class="btn btn-primary btn-lg" type="submit">Tiến hành thanh toán</button>
    </form>
</c:if>


<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
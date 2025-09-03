<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<div class="container-fluid mt-4">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">Thông tin chi tiết sản phẩm</h5>
        </div>
        <div class="card-body">
            <c:if test="${product != null}">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <img src="${product.image}" alt="Ảnh sản phẩm" class="img-fluid rounded shadow-sm" style="max-height: 300px; object-fit: cover;"/>
                    </div>
                    <div class="col-md-8">
                        <table class="table table-bordered">
                            <tr>
                                <th>ID</th>
                                <td>${product.id}</td>
                            </tr>
                            <tr>
                                <th>Tên sản phẩm</th>
                                <td>${product.name}</td>
                            </tr>
                            <tr>
                                <th>Số lượng</th>
                                <td>${product.quantity}</td>
                            </tr>
                            <tr>
                                <th>Giá</th>
                                <td><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> VND</td>
                            </tr>
                            <tr>
                                <th>Loại sản phẩm</th>
                                <td>${product.category.name}</td>
                            </tr>
                            <tr>
                                <th>Mô tả sản phẩm</th>
                                <td><p>${product.description}</p></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:if>
            <c:if test="${product == null}">
                <div class="alert alert-warning">Không tìm thấy sản phẩm.</div>
            </c:if>
        </div>
    </div>
</div>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow-lg p-4 rounded-4">
                <h3 class="text-center text-primary">Đăng nhập</h3>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label class="form-label">Tên tài khoản</label>
                        <input type="text" class="form-control rounded-3 shadow-sm" name="username" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control rounded-3 shadow-sm" name="password" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Vai trò</label>
                        <select class="form-select rounded-3 shadow-sm" name="role">
                            <option value="user">User</option>
                            <option value="admin">Admin</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 btn-rounded shadow">Đăng nhập</button>
                </form>
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng kí ngay</a>
                </div>
            </div>
        </div>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="alert alert-danger mt-3">${error}</div>
</c:if>
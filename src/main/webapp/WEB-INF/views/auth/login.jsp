<%--<%@ page pageEncoding="UTF-8" %>--%>
<%--<%@ include file="/WEB-INF/views/common/header.jsp" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<div class="container mt-5">--%>
<%--    <div class="row justify-content-center">--%>
<%--        <div class="col-md-4">--%>
<%--            <div class="card shadow-lg p-4 rounded-4">--%>
<%--                <h3 class="text-center text-primary">Đăng nhập</h3>--%>
<%--                <form action="${pageContext.request.contextPath}/login" method="post">--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Tên tài khoản</label>--%>
<%--                        <input type="text" class="form-control rounded-3 shadow-sm" name="username" required/>--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Mật khẩu</label>--%>
<%--                        <input type="password" class="form-control rounded-3 shadow-sm" name="password" required/>--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Vai trò</label>--%>
<%--                        <select class="form-select rounded-3 shadow-sm" name="role">--%>
<%--                            <option value="user">User</option>--%>
<%--                            <option value="admin">Admin</option>--%>
<%--                        </select>--%>
<%--                    </div>--%>
<%--                    <button type="submit" class="btn btn-primary w-100 btn-rounded shadow">Đăng nhập</button>--%>
<%--                </form>--%>
<%--                <div class="text-center mt-3">--%>
<%--                    <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng kí ngay</a>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<%--<c:if test="${not empty error}">--%>
<%--    <div class="alert alert-danger mt-3">${error}</div>--%>
<%--</c:if>--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập - ElectroShop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1d3557, #457b9d);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .card {
            border-radius: 20px;
            border: none;
        }
        .btn-custom {
            border-radius: 30px;
            font-weight: 600;
        }
        .form-control {
            border-radius: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow-lg p-4">
                <h3 class="text-center text-primary mb-4">Đăng nhập</h3>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3">
                        <label class="form-label">Tên tài khoản</label>
                        <input type="text" class="form-control" name="username" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" name="password" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Vai trò</label>
                        <select class="form-select" name="role">
                            <option value="user">User</option>
                            <option value="admin">Admin</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary w-100 btn-custom">Đăng nhập</button>
                </form>
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/register">Chưa có tài khoản? Đăng ký ngay</a>
                </div>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3">${error}</div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>

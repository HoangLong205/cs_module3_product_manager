<%--<%@ page pageEncoding="UTF-8" %>--%>
<%--<%@ include file="/WEB-INF/views/common/header.jsp" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<div class="container mt-5">--%>
<%--    <div class="row justify-content-center">--%>
<%--        <div class="col-md-4">--%>
<%--            <div class="card shadow-lg p-4">--%>
<%--                <h3 class="text-center text-success">Đăng kí</h3>--%>
<%--                <form action="${pageContext.request.contextPath}/register" method="post">--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Tên tài khoản</label>--%>
<%--                        <input type="text" class="form-control" name="username" required/>--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Mật khẩu</label>--%>
<%--                        <input type="password" class="form-control" name="password" required/>--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Nhập lại mật khẩu</label>--%>
<%--                        <input type="password" class="form-control" name="confirmPassword" required/>--%>
<%--                    </div>--%>
<%--                    <div class="mb-3">--%>
<%--                        <label class="form-label">Vai trò</label>--%>
<%--                        <select class="form-select" name="role">--%>
<%--                            <option value="user">User</option>--%>
<%--                            <option value="admin">Admin</option>--%>
<%--                        </select>--%>
<%--                    </div>--%>
<%--                    <button type="submit" class="btn btn-success w-100">Đăng kí</button>--%>
<%--                </form>--%>
<%--                <div class="text-center mt-3">--%>
<%--                    <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>--%>
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
    <title>Đăng ký - ElectroShop</title>
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
                <h3 class="text-center text-success mb-4">Đăng ký</h3>
                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="mb-3">
                        <label class="form-label">Tên tài khoản</label>
                        <input type="text" class="form-control" name="username" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Mật khẩu</label>
                        <input type="password" class="form-control" name="password" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Nhập lại mật khẩu</label>
                        <input type="password" class="form-control" name="confirmPassword" required/>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Vai trò</label>
                        <select class="form-select" name="role">
                            <option value="user">Người dùng</option>
                            <option value="admin">Quản trị viên</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success w-100 btn-custom">Đăng ký</button>
                </form>
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/login">Đã có tài khoản? Đăng nhập</a>
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

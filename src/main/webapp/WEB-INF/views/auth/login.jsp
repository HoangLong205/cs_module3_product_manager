<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/18/2025
  Time: 5:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
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
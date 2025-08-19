<%--
  Created by IntelliJ IDEA.
  User: Linh
  Date: 8/18/2025
  Time: 5:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
</head>
<nav class="navbar navbar-expand-lg bg-deep-dark shadow px-4">
  <a class="navbar-brand text-aqua fw-bold" href="${pageContext.request.contextPath}/home">
    MyShop
  </a>
  <div class="collapse navbar-collapse">
    <ul class="navbar-nav ms-auto">
      <c:choose>
        <c:when test="${empty sessionScope.account}">
          <li class="nav-item">
            <a class="nav-link text-white btn btn-primary btn-sm mx-1 btn-rounded shadow"
               href="${pageContext.request.contextPath}/login">Đăng nhập</a>
          </li>
          <li class="nav-item">
            <a class="nav-link text-white btn btn-success btn-sm mx-1 btn-rounded shadow"
               href="${pageContext.request.contextPath}/register">Đăng kí</a>
          </li>
        </c:when>
        <c:otherwise>
          <li class="nav-item">
            <span class="nav-link text-aqua fw-bold">Xin chào, ${sessionScope.account.username}</span>
          </li>
          <c:if test="${sessionScope.account.role eq 'user'}">
            <li class="nav-item">
              <a class="nav-link text-white btn btn-info btn-sm mx-1 btn-rounded shadow"
                 href="${pageContext.request.contextPath}/cart">
                <i class="bi bi-cart"></i> Giỏ hàng
              </a>
            </li>
          </c:if>
          <li class="nav-item">
            <a class="nav-link text-white btn btn-danger btn-sm mx-1 btn-rounded shadow"
               href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
          </li>
        </c:otherwise>
      </c:choose>
    </ul>
  </div>
</nav>



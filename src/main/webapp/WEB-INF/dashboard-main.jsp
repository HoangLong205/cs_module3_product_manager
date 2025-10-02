<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 8/15/2025
  Time: 8:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .dashboard-container {
        padding: 20px;
    }

    .stats-cards {
        display: flex;
        gap: 20px;
    }

    .card {
        flex: 1;
        background-color: #f0f0f0;
        border-radius: 8px;
        padding: 20px;
        text-align: center;
        box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.1);
    }

    .card h3 {
        margin-bottom: 10px;
    }

    .card p {
        font-size: 24px;
        font-weight: bold;
    }
</style>
<div class="dashboard-container">
    <h2>Dashboard Tổng Quan</h2>
    <div class="stats-cards">
        <div class="card">
            <h3>Tổng sản phẩm</h3>
            <p>${totalProducts}</p>
        </div>
        <div class="card">
            <h3>Tổng loại sản phẩm</h3>
            <p>${totalCategories}</p>
        </div>
    </div>
</div>

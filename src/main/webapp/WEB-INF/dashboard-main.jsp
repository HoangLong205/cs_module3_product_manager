<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Dashboard - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="container-fluid p-4">

    <!-- Cards Section -->
    <div class="row g-4 mb-4">
        <div class="col-md-3">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <h5 class="card-title">Tổng đơn hàng</h5>
                    <h3 class="text-primary"><fmt:formatNumber value="${totalOrders}" type="number"/></h3>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <h5 class="card-title">Doanh thu</h5>
                    <h3 class="text-success"><fmt:formatNumber value="${totalRevenue}" type="number" groupingUsed="true"/> VNĐ</h3>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <h5 class="card-title">Khách hàng</h5>
                    <h3 class="text-warning"><c:out value="${totalCustomers}"/></h3>
                </div>
            </div>
        </div>

        <div class="col-md-3">
            <div class="card shadow-sm border-0">
                <div class="card-body text-center">
                    <h5 class="card-title">Sản phẩm</h5>
                    <h3 class="text-danger"><c:out value="${totalProducts}"/></h3>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h5>Pending</h5>
                    <h3>${orderStatusCounts['PENDING'] != null ? orderStatusCounts['PENDING'] : 0}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h5>Confirmed</h5>
                    <h3>${orderStatusCounts['CONFIRMED'] != null ? orderStatusCounts['CONFIRMED'] : 0}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h5>Completed</h5>
                    <h3>${orderStatusCounts['COMPLETED'] != null ? orderStatusCounts['COMPLETED'] : 0}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card text-center">
                <div class="card-body">
                    <h5>Cancelled</h5>
                    <h3>${orderStatusCounts['CANCELLED'] != null ? orderStatusCounts['CANCELLED'] : 0}</h3>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

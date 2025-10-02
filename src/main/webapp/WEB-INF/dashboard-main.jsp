<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid">
    <h3 class="mb-4">Dashboard Tổng quan</h3>

    <div class="row g-4 mb-4">
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100 border-primary">
                <div class="card-body">
                    <h5 class="card-title text-primary"><i class="fas fa-shopping-cart me-2"></i> Tổng đơn hàng</h5>
                    <h3 class="fw-bold mt-2"><fmt:formatNumber value="${totalOrders}" type="number"/></h3>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100 border-success">
                <div class="card-body">
                    <h5 class="card-title text-success"><i class="fas fa-chart-line me-2"></i> Doanh thu</h5>
                    <h3 class="fw-bold mt-2"><fmt:formatNumber value="${totalRevenue}" type="number" groupingUsed="true"/> VNĐ</h3>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100 border-warning">
                <div class="card-body">
                    <h5 class="card-title text-warning"><i class="fas fa-users me-2"></i> Khách hàng</h5>
                    <h3 class="fw-bold mt-2"><c:out value="${totalCustomers}"/></h3>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100 border-danger">
                <div class="card-body">
                    <h5 class="card-title text-danger"><i class="fas fa-box me-2"></i> Sản phẩm</h5>
                    <h3 class="fw-bold mt-2"><c:out value="${totalProducts}"/></h3>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title text-muted">Pending</h5>
                    <h3 class="fw-bold">${orderStatusCounts['PENDING'] != null ? orderStatusCounts['PENDING'] : 0}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title text-info">Confirmed</h5>
                    <h3 class="fw-bold">${orderStatusCounts['CONFIRMED'] != null ? orderStatusCounts['CONFIRMED'] : 0}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title text-success">Completed</h5>
                    <h3 class="fw-bold">${orderStatusCounts['COMPLETED'] != null ? orderStatusCounts['COMPLETED'] : 0}</h3>
                </div>
            </div>
        </div>
        <div class="col-md-6 col-lg-3">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h5 class="card-title text-danger">Cancelled</h5>
                    <h3 class="fw-bold">${orderStatusCounts['CANCELLED'] != null ? orderStatusCounts['CANCELLED'] : 0}</h3>
                </div>
            </div>
        </div>
    </div>
</div>
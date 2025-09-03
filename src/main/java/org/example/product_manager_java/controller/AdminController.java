package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.*;
import org.example.product_manager_java.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = request.getParameter("view");
        if (view == null) {
            view = "dashboard";
        }

        // Kiểm tra xem yêu cầu có phải là AJAX để lấy nội dung modal không
        // Các yêu cầu này sẽ không cần header/footer
        if ("product-detail".equals(view)) {
            try {
                int productId = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("product", productDAO.getByIdProduct(productId));
//                request.getRequestDispatcher("/WEB-INF/product-detail.jsp").forward(request, response);
                request.getRequestDispatcher("/WEB-INF/views/admin/product-detail.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID sản phẩm không hợp lệ.");
                request.getRequestDispatcher("/WEB-INF/error-modal-content.jsp").forward(request, response);
                return;
            }
        }
        if ("orderDetail".equals(view)) {
            try {
                int orderId = Integer.parseInt(request.getParameter("id"));
                Order order = orderDAO.getOrderById(orderId);
                List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);

                request.setAttribute("order", order);
                request.setAttribute("items", orderItems);
//                request.getRequestDispatcher("/WEB-INF/orderDetail.jsp").forward(request, response);
                request.getRequestDispatcher("/WEB-INF/views/admin/orderDetail.jsp").forward(request, response);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Đây là xử lý cho các trang có layout đầy đủ (vẫn cần header/footer)
        String pageContent = null;
        String title = null;

        switch (view) {
            case "users":
                try {
                    handleUsers(request);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//                pageContent = "/WEB-INF/user-list.jsp";
                pageContent = "/WEB-INF/views/admin/user-list.jsp";
                title = "Quản lý người dùng";
                break;
            case "products":
                handleProducts(request);
                request.setAttribute("categories", categoryDAO.getAllCategory());
//                pageContent = "/WEB-INF/product-list.jsp";
                pageContent = "/WEB-INF/views/admin/product-list.jsp";
                title = "Quản lý sản phẩm";
                break;
            case "categories":
                handleCategories(request);
//                pageContent = "/WEB-INF/category-list.jsp";
                pageContent = "/WEB-INF/views/admin/category-list.jsp";
                title = "Quản lý loại sản phẩm";
                break;
            case "orders":
                try {
                    List<Order> orders = orderDAO.getAllOrders();
                    request.setAttribute("orders", orders);
//                    pageContent = "/WEB-INF/order-list.jsp";
                    pageContent = "/WEB-INF/views/admin/order-list.jsp";
                    title = "Quản lý đơn hàng";
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "updateOrderStatus":
                try {
                    int oid = Integer.parseInt(request.getParameter("id"));
                    String newStatus = request.getParameter("status");
                    orderDAO.updateOrderStatus(oid, Order.OrderStatus.valueOf(newStatus));
                    response.sendRedirect("admin?view=orders");
                    return;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            case "deleteOrder":
                try {
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    orderDAO.updateOrderStatus(deleteId, Order.OrderStatus.DELETED); // Chuyển trạng thái sang DELETED
                    response.sendRedirect("admin?view=orders");
                    return;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            default:
                handleDashboard(request);
//                pageContent = "/WEB-INF/dashboard-main.jsp";
                pageContent = "/WEB-INF/views/admin/dashboard-main.jsp";
                title = "Trang tổng quan";
                break;
        }

        request.setAttribute("pageContent", pageContent);
        request.setAttribute("title", title);

//        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

    private void handleUsers(HttpServletRequest request) throws SQLException {
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        String search = request.getParameter("search");
        if (search == null) {
            search = "";
        }

        int totalUsers = userDAO.countUsers(search);
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);
        List<User> users = userDAO.getUsers(page, pageSize, search);

        request.setAttribute("users", users);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchKeyword", search);
    }

    private void handleProducts(HttpServletRequest request) {
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String productKeyword = request.getParameter("keyword");
        String productField = request.getParameter("field");

        List<Product> products;
        int totalProducts;

        if (productKeyword != null && !productKeyword.trim().isEmpty()) {
            products = productDAO.searchProducts(productKeyword, productField, page, pageSize);
            totalProducts = productDAO.getTotalSearchCount(productKeyword, productField);
            request.setAttribute("keyword", productKeyword);
            request.setAttribute("field", productField);
        } else {
            products = productDAO.getProductsByPage(page, pageSize);
            totalProducts = productDAO.getTotalProductCount();
        }

        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);
        request.setAttribute("products", products);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
    }

    private void handleCategories(HttpServletRequest request) {
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String categoryKeyword = request.getParameter("search");

        List<Category> categories;
        int totalCategories;

        if (categoryKeyword != null && !categoryKeyword.trim().isEmpty()) {
            categories = categoryDAO.searchByNameCategory(categoryKeyword, page, pageSize);
            totalCategories = categoryDAO.getTotalSearchCount(categoryKeyword);
            request.setAttribute("searchKeyword", categoryKeyword);
        } else {
            categories = categoryDAO.getCategoriesByPage(page, pageSize);
            totalCategories = categoryDAO.getTotalCategoryCount();
        }

        int totalPages = (int) Math.ceil((double) totalCategories / pageSize);
        request.setAttribute("list", categories);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
    }

    private void handleDashboard(HttpServletRequest request) {
        int totalProducts = productDAO.getTotalProductCount();
        int totalCategories = categoryDAO.getTotalCategoryCount();
        try {
            int totalUsers = userDAO.countUsers("");
            request.setAttribute("totalUsers", totalUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalCategories", totalCategories);
        request.setAttribute("totalOrders", orderDAO.countAll());
        request.setAttribute("totalRevenue", orderDAO.sumRevenue());
        request.setAttribute("totalCustomers", userDAO.countCustomers());

        Map<String, Integer> orderStatusCounts = orderDAO.countOrdersByStatus();
        request.setAttribute("orderStatusCounts", orderStatusCounts);

        Map<String, Double> revenueByMonth = orderDAO.getRevenueByMonth(3);
        request.setAttribute("months", new ArrayList<>(revenueByMonth.keySet()));
        request.setAttribute("revenues", new ArrayList<>(revenueByMonth.values()));
    }
}


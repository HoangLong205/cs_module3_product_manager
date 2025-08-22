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
import java.util.Arrays;
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
//        System.out.println("!!! AdminController: ĐÃ NHẬN ĐƯỢC YÊU CẦU !!!");
//        String pathInfo = request.getPathInfo();
//        System.out.println(">>> AdminController: Path Info là: " + pathInfo);
        String view = request.getParameter("view");
        if (view == null) {
            view = "dashboard"; // Mặc định là trang dashboard tổng quan
        }
        String pageContent = null;
        String title = null;

        switch (view) {
            case "users":
                handleUsers(request, response);
                pageContent = "/WEB-INF/user-list.jsp";
                title = "Quản lý người dùng";
                break;
            case "products":
                handleProducts(request);
                pageContent = "/WEB-INF/product-list.jsp";
                title = "Quản lý sản phẩm";
                break;
            case "categories":
                handleCategories(request);
                pageContent = "/WEB-INF/category-list.jsp";
                title = "Quản lý loại sản phẩm";
                break;
            case "orders":
                try {
                    List<Order> orders = orderDAO.getAllOrders();
                    request.setAttribute("orders", orders);
                    pageContent = "/WEB-INF/order-list.jsp";
                    title = "Quản lý đơn hàng";
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "orderDetail":
                try {
                    int orderId = Integer.parseInt(request.getParameter("id"));
                    Order order = orderDAO.getOrderById(orderId);
                    List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);

                    request.setAttribute("order", order);
                    request.setAttribute("items", orderItems);
                    pageContent = "/WEB-INF/orderDetail.jsp";
                    title = "Chi tiết đơn hàng";
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
                int deleteId = Integer.parseInt(request.getParameter("id"));
                orderDAO.deleteOrder(deleteId);
                response.sendRedirect("admin?view=orders");
                return;
            case "product-detail":
                try {
                    int productId = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("product", productDAO.getByIdProduct(productId));
                    pageContent = "/WEB-INF/product-detail.jsp";
                    title = "Chi tiết sản phẩm";
                } catch (NumberFormatException e) {
                    // Xử lý nếu id không phải là số
                    pageContent = "/WEB-INF/dashboard-main.jsp";
                    title = "Lỗi";
                    request.setAttribute("error", "ID sản phẩm không hợp lệ.");
                }
                break;
            case "category-form":
                String categoryIdStr = request.getParameter("id");
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    // Đây là trường hợp SỬA: Lấy thông tin category và gửi sang form
                    int categoryId = Integer.parseInt(categoryIdStr);
                    request.setAttribute("category", categoryDAO.getByIdCategory(categoryId));
                    title = "Cập nhật loại sản phẩm";
                } else {
                    // Đây là trường hợp THÊM MỚI
                    title = "Thêm loại sản phẩm";
                }
                pageContent = "/WEB-INF/category-form.jsp";
                break;
            case "product-form":
                // Luôn cần danh sách category cho dropdown
                request.setAttribute("categories", categoryDAO.getAllCategory());

                String productIdStr = request.getParameter("id");
                if (productIdStr != null && !productIdStr.isEmpty()) {
                    // Trường hợp SỬA
                    int productId = Integer.parseInt(productIdStr);
                    request.setAttribute("product", productDAO.getByIdProduct(productId));
                    title = "Cập nhật sản phẩm";
                } else {
                    // Trường hợp THÊM MỚI
                    title = "Thêm sản phẩm";
                }
                pageContent = "/WEB-INF/product-form.jsp";
                break;
            default:
                // Trang dashboard mặc định
//                pageContent = "/WEB-INF/dashboard-main.jsp"; // Tạo một file riêng cho nội dung dashboard
//                title = "Trang tổng quan";
                // Lấy tổng số sản phẩm và loại sản phẩm
                int totalProducts = productDAO.getAllProduct().size();
                int totalCategories = categoryDAO.getAllCategory().size();
                int totalUsers = userDAO.selectAllUsers().size();

                // Gửi dữ liệu sang JSP
                request.setAttribute("totalProducts", totalProducts);
                request.setAttribute("totalCategories", totalCategories);
                request.setAttribute("totalUsers", totalUsers);

                // Tổng số
                request.setAttribute("totalOrders", orderDAO.countAll());
                request.setAttribute("totalRevenue", orderDAO.sumRevenue());
                request.setAttribute("totalCustomers", userDAO.countCustomers());
                request.setAttribute("totalProducts", productDAO.countProducts());

                // Trạng thái đơn hàng
                Map<String, Integer> orderStatusCounts = orderDAO.countOrdersByStatus();
                request.setAttribute("orderStatusCounts", orderStatusCounts);

                // Doanh thu theo tháng (ví dụ 3 tháng gần nhất)
                Map<String, Double> revenueByMonth = orderDAO.getRevenueByMonth(3);
                request.setAttribute("months", new ArrayList<>(revenueByMonth.keySet()));
                request.setAttribute("revenues", new ArrayList<>(revenueByMonth.values()));

                // Giao diện dashboard
                pageContent = "/WEB-INF/dashboard-main.jsp";
                title = "Trang tổng quan";

                break;
        }
        request.setAttribute("pageContent", pageContent);
        request.setAttribute("title", title);

        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }


    private void handleUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            int page = 1;
            int pageSize = 5; // số bản ghi trên 1 trang

            // lấy page hiện tại từ request
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }

            // lấy keyword search (nếu có)
            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }

            UserDAO userDAO = new UserDAO();

            // đếm tổng số user (có filter search)
            int totalUsers = userDAO.countUsers(search);

            // tính tổng số trang
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            // lấy danh sách user cho page hiện tại
            List<User> users = userDAO.getUsers(page, pageSize, search);

            // đẩy dữ liệu sang JSP
            request.setAttribute("users", users);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchKeyword", search);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            // Search có phân trang
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

}


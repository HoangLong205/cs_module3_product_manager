package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.CategoryDAO;
import org.example.product_manager_java.dao.ProductDAO;
import org.example.product_manager_java.model.Category;
import org.example.product_manager_java.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;


    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();

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
        String pageContent;
        String title;

        switch (view) {
            case "products":
//                String productKeyword = request.getParameter("keyword");
//                String productField = request.getParameter("field");
//
//                if (productKeyword != null && !productKeyword.trim().isEmpty()) {
//                    // Nếu có từ khóa -> thực hiện tìm kiếm
//                    request.setAttribute("products", productDAO.searchProducts(productKeyword, productField));
//                    // Gửi lại từ khóa và trường đã tìm kiếm để hiển thị trên form
//                    request.setAttribute("keyword", productKeyword);
//                    request.setAttribute("field", productField);
//                } else {
//                    // Nếu không -> hiển thị tất cả
//                    request.setAttribute("products", productDAO.getAllProduct());
//                }
                handleProducts(request);
                pageContent = "/WEB-INF/product-list.jsp";
                title = "Quản lý sản phẩm";
                break;
            case "categories":
//                // Lấy danh sách loại sản phẩm
//                String categoryKeyword = request.getParameter("search");
//
//                if (categoryKeyword != null && !categoryKeyword.trim().isEmpty()) {
//                    // Nếu có từ khóa -> thực hiện tìm kiếm
//                    request.setAttribute("list", categoryDAO.searchByNameCategory(categoryKeyword));
//                    // Gửi lại từ khóa đã tìm kiếm để hiển thị trên form
//                    request.setAttribute("searchKeyword", categoryKeyword);
//                } else {
//                    // Nếu không -> hiển thị tất cả
//                    request.setAttribute("list", categoryDAO.getAllCategory());
//                }
                handleCategories(request);
                pageContent = "/WEB-INF/category-list.jsp";
                title = "Quản lý loại sản phẩm";
                break;
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

                // Gửi dữ liệu sang JSP
                request.setAttribute("totalProducts", totalProducts);
                request.setAttribute("totalCategories", totalCategories);

                // Giao diện dashboard
                pageContent = "/WEB-INF/dashboard-main.jsp";
                title = "Trang tổng quan";

                break;
        }
        request.setAttribute("pageContent", pageContent);
        request.setAttribute("title", title);

        request.getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(request, response);
    }

    private void handleProducts(HttpServletRequest request) {
        int page = 1;
        int pageSize = 10;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try { page = Integer.parseInt(pageParam); } catch (NumberFormatException e) { page = 1; }
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
            try { page = Integer.parseInt(pageParam); } catch (NumberFormatException e) { page = 1; }
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

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


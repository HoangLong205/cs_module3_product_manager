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
import java.util.logging.Logger;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CategoryController.class.getName());

    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;

    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action: " + action);
        if (action == null) action = "list";
        System.out.println("🛠 [GET] Action = " + action);
        switch (action) {
            case "new":
                System.out.println("➡ Mở form thêm mới product");
                request.setAttribute("categories", categoryDAO.getAllCategory());
                request.getRequestDispatcher("/WEB-INF/product-form.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.getByIdProduct(id);
                request.setAttribute("product", product);
                request.setAttribute("categories", categoryDAO.getAllCategory());
                request.getRequestDispatcher("/WEB-INF/product-form.jsp").forward(request, response);
                break;
            default:
                System.out.println("📋 Lấy danh sách product");
                request.setAttribute("products", productDAO.getAllProduct());
                request.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        Category category = categoryDAO.getByIdCategory(categoryId);

        String idStr = request.getParameter("id");
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        product.setImage(request.getParameter("image"));
        product.setDescription(request.getParameter("description"));
        product.setCategory(category);

        if (idStr == null || idStr.isEmpty()) {
            productDAO.insertProduct(product);
            System.out.println("Thêm sản phẩm thành công!");
        }else {
            product.setId(Integer.parseInt(idStr));
            productDAO.updateProduct(product);
        }


        response.sendRedirect("products");
    }
}

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
import java.net.URLEncoder;
import java.util.logging.Logger;


@WebServlet("/products")
public class ProductController extends HttpServlet {


    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

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
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                System.out.println("🗑 Xóa product ID = " + idDelete);
                productDAO.deleteProduct(idDelete);
                break;
            case "search":
                String keyword = request.getParameter("keyword");
                String field = request.getParameter("field");
                response.sendRedirect("admin?view=products&keyword="
                        + URLEncoder.encode(keyword, "UTF-8")
                        + "&field=" + URLEncoder.encode(field, "UTF-8"));
                return;
        }

        response.sendRedirect("admin?view=products");
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
        } else {
            product.setId(Integer.parseInt(idStr));
            productDAO.updateProduct(product);
            System.out.println("Cập nhật sản phẩm thành công!");
        }

        response.sendRedirect("admin?view=products");
    }
}

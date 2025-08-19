package org.example.cs_module3_views.controller;

import org.example.cs_module3_views.DAO.ProductDAO;
import org.example.cs_module3_views.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/product-list", "/user/product-list"})
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = productDAO.findAll();
        request.setAttribute("products", products);

        String path = request.getServletPath();
        if (path.startsWith("/admin")) {
            request.getRequestDispatcher("/WEB-INF/views/admin/product-list.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/user/product-list.jsp").forward(request, response);
        }
    }
}
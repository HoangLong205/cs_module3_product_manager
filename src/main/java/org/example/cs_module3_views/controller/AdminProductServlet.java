package org.example.cs_module3_views.controller;

import org.example.cs_module3_views.DAO.ProductDAO;
import org.example.cs_module3_views.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/product/*")
public class AdminProductServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null || path.equals("/product-list")) {
            listProducts(request, response);
        } else if (path.equals("/product-detail")) {
            showDetail(request, response);
        } else if (path.equals("/product-add")) {
            request.getRequestDispatcher("/WEB-INF/views/admin/product/product-add.jsp").forward(request, response);
        } else if (path.equals("/product-edit")) {
            showEditForm(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path.equals("/insert")) {
            insertProduct(request, response);
        } else if (path.equals("/update")) {
            updateProduct(request, response);
        } else if (path.equals("/delete")) {
            deleteProduct(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> list = productDAO.findAll();
        request.setAttribute("products", list);
        request.getRequestDispatcher("/WEB-INF/views/admin/product/product-list.jsp").forward(request, response);
    }

    private void showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.findById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/views/admin/product/product-detail.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productDAO.findById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/views/admin/product/product-edit.jsp").forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product newProduct = new Product(0, name, price, quantity, image, description, null);
        productDAO.insert(newProduct, categoryId);

        response.sendRedirect(request.getContextPath() + "/admin/product/product-list");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String image = request.getParameter("image");
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        Product product = new Product(id, name, price, quantity, image, description, null);
        productDAO.update(product, categoryId);

        response.sendRedirect(request.getContextPath() + "/admin/product/product-list");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/product/product-list");
    }
}
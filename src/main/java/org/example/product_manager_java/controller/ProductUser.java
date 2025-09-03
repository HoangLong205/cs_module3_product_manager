package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.ProductDAO;
import org.example.product_manager_java.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product/detail")
public class ProductUser extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Product p = productDAO.findById(id);
        if (p == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        req.setAttribute("p", p);
        req.getRequestDispatcher("/WEB-INF/views/user/detail.jsp").forward(req, resp);
    }
}

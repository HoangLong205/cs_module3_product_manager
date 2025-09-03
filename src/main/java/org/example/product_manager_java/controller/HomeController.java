package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.ProductDAO;
import org.example.product_manager_java.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"", "/", "/home"})
public class HomeController extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private static final int PAGE_SIZE = 20; // 4 x 5 grid


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        int page = 1;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}
        if (page < 1) page = 1;


        int total = productDAO.countBySearch(q);
        int totalPages = (int) Math.ceil(total / (double) PAGE_SIZE);
        int offset = (page - 1) * PAGE_SIZE;
        List<Product> products = productDAO.findPaged(q, offset, PAGE_SIZE);


        req.setAttribute("q", q == null ? "" : q);
        req.setAttribute("products", products);
        req.setAttribute("page", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(req, resp);
    }
}

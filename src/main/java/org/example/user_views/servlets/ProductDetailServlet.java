package org.example.user_views.servlets;

import org.example.user_views.dao.ProductDAO;
import org.example.user_views.models.Product;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/product/detail")
public class ProductDetailServlet extends HttpServlet {
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
        req.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(req, resp);
    }
}

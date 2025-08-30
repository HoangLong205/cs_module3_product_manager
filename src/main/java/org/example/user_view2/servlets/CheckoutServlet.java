package org.example.user_view2.servlets;

import org.example.user_view2.dao.OrderDAO;
import org.example.user_view2.models.CartItem;
import org.example.user_view2.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet("/checkout-success")
public class CheckoutServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();

    @SuppressWarnings("unchecked")
    private Map<Integer, CartItem> getCart(HttpSession session) {
        Object obj = session.getAttribute("cart");
        return obj == null ? new LinkedHashMap<>() : (Map<Integer, CartItem>) obj;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Map<Integer, CartItem> cart = getCart(session);
        if (cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        int orderId = orderDAO.createOrder(user.getId(), cart.values());
        session.setAttribute("lastOrderId", orderId);
        session.removeAttribute("cart");
        resp.sendRedirect(req.getContextPath() + "/checkout-success");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/checkout-success.jsp").forward(req, resp);
    }
}
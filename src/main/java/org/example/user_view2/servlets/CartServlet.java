package org.example.user_view2.servlets;

import org.example.user_view2.dao.ProductDAO;
import org.example.user_view2.models.CartItem;
import org.example.user_view2.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();


    @SuppressWarnings("unchecked")
    private Map<Integer, CartItem> getCart(HttpSession session) {
        Object obj = session.getAttribute("cart");
        if (obj == null) {
            Map<Integer, CartItem> map = new LinkedHashMap<>();
            session.setAttribute("cart", map);
            return map;
        }
        return (Map<Integer, CartItem>) obj;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("remove".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            getCart(req.getSession()).remove(id);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Map<Integer, CartItem> cart = getCart(req.getSession());


        if ("add".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int qty = 1;
            try { qty = Integer.parseInt(req.getParameter("qty")); } catch (Exception ignored) {}
            Product p = productDAO.findById(id);
            if (p == null || p.getQuantity() <= 0) {
                resp.sendRedirect(req.getHeader("Referer"));
                return;
            }
            CartItem item = cart.get(id);
            int newQty = (item == null ? 0 : item.getQuantity()) + qty;
            if (newQty > p.getQuantity()) newQty = p.getQuantity();
            cart.put(id, new CartItem(p, newQty));
            resp.sendRedirect(req.getHeader("Referer"));
            return;
        }


        if ("update".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int qty = Integer.parseInt(req.getParameter("qty"));
            Product p = productDAO.findById(id);
            if (p != null) {
                if (qty <= 0) cart.remove(id); else cart.put(id, new CartItem(p, Math.min(qty, p.getQuantity())));
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}

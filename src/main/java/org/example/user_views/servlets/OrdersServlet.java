package org.example.user_views.servlets;

import org.example.user_views.dao.OrderDAO;
import org.example.user_views.models.Order;
import org.example.user_views.models.OrderItem;
import org.example.user_views.models.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;


@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        List<Order> orders = orderDAO.getOrdersByUser(user.getId());
        Map<Integer, List<OrderItem>> map = new LinkedHashMap<>();
        for (Order o : orders) {
            map.put(o.getId(), orderDAO.getItemsByOrder(o.getId()));
        }
        req.setAttribute("orders", orders);
        req.setAttribute("orderItemsMap", map);
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);
    }
}
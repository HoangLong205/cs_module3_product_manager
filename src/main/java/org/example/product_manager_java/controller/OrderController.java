package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.OrderDAO;
import org.example.product_manager_java.model.Order;
import org.example.product_manager_java.model.OrderItem;
import org.example.product_manager_java.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/orders")
public class OrderController extends HttpServlet {
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
        req.getRequestDispatcher("/WEB-INF/views/user/orders.jsp").forward(req, resp);
    }
}

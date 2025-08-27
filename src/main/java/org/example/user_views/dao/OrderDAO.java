package org.example.user_views.dao;

import org.example.user_views.models.*;
import org.example.user_views.utils.DBConnection;

import java.sql.*;
import java.util.*;


public class OrderDAO {
    public int createOrder(int userId, Collection<CartItem> cartItems) {
        String insertOrder = "INSERT INTO orders(user_id, status, created_at, total) VALUES(?, ?, NOW(), ?)";
        String insertItem = "INSERT INTO order_items(order_id, product_id, product_name, quantity, unit_price) VALUES(?,?,?,?,?)";
        String decQty = "UPDATE products SET quantity = quantity - ? WHERE id=? AND quantity >= ?";
        int orderId = -1;
        try (Connection cn = DBConnection.getConnection()) {
            cn.setAutoCommit(false);
            double total = cartItems.stream().mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity()).sum();


            try (PreparedStatement ps = cn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setString(2, OrderStatus.PENDING.name());
                ps.setDouble(3, total);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) orderId = keys.getInt(1); }
            }


            try (PreparedStatement itemPs = cn.prepareStatement(insertItem);
                 PreparedStatement decPs = cn.prepareStatement(decQty)) {
                for (CartItem ci : cartItems) {
                    itemPs.setInt(1, orderId);
                    itemPs.setInt(2, ci.getProduct().getId());
                    itemPs.setString(3, ci.getProduct().getName());
                    itemPs.setInt(4, ci.getQuantity());
                    itemPs.setDouble(5, ci.getProduct().getPrice());
                    itemPs.addBatch();


                    decPs.setInt(1, ci.getQuantity());
                    decPs.setInt(2, ci.getProduct().getId());
                    decPs.setInt(3, ci.getQuantity());
                    decPs.addBatch();
                }
                itemPs.executeBatch();
                int[] affected = decPs.executeBatch();
                for (int a : affected) if (a == 0) throw new SQLException("Out of stock while checking out");
            }


            cn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderId;
    }

    public List<Order> getOrdersByUser(int userId) {
        String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY created_at DESC";
        List<Order> list = new ArrayList<>();
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(userId);
                    o.setStatus(OrderStatus.valueOf(rs.getString("status")));
                    o.setTotal(rs.getDouble("total"));
                    o.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    list.add(o);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return list;
    }


    public List<OrderItem> getItemsByOrder(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id=?";
        List<OrderItem> items = new ArrayList<>();
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem it = new OrderItem();
                    it.setId(rs.getInt("id"));
                    it.setOrderId(orderId);
                    it.setProductId(rs.getInt("product_id"));
                    it.setProductName(rs.getString("product_name"));
                    it.setQuantity(rs.getInt("quantity"));
                    it.setUnitPrice(rs.getDouble("unit_price"));
                    items.add(it);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return items;
    }
}
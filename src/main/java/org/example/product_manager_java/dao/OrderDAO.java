package org.example.product_manager_java.dao;

import org.example.product_manager_java.model.Order;
import org.example.product_manager_java.model.OrderItem;
import org.example.product_manager_java.model.User;
import org.example.product_manager_java.utils.DBConnection;

import java.sql.*;
import java.text.DateFormatSymbols;
import java.util.*;

public class OrderDAO {
    // Lấy toàn bộ danh sách đơn hàng
    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT o.id, o.user_id, u.username, o.order_date, o.total, o.status " +
                "FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.id DESC";
        List<Order> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                order.setUser(user);
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(Order.OrderStatus.valueOf(rs.getString("status")));
                list.add(order);
            }
        }
        return list;
    }

    // Lấy chi tiết đơn hàng
    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT o.id, o.user_id, u.username, o.order_date, o.total, o.status " +
                "FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    order.setUser(user);
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setTotal(rs.getDouble("total"));
                    order.setStatus(Order.OrderStatus.valueOf(rs.getString("status")));
                    return order;
                }
            }
        }
        return null;
    }

    // Update trạng thái đơn hàng
    public void updateOrderStatus(int orderId, Order.OrderStatus status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    // Xóa đơn hàng
    public boolean deleteOrder(int orderId) {
        String deleteOrderItemsSQL = "DELETE FROM order_items WHERE order_id = ?";
        String deleteOrderSQL = "DELETE FROM orders WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            // Xóa order_items trước
            try (PreparedStatement ps1 = connection.prepareStatement(deleteOrderItemsSQL)) {
                ps1.setInt(1, orderId);
                ps1.executeUpdate();
            }

            // Sau đó mới xóa order
            try (PreparedStatement ps2 = connection.prepareStatement(deleteOrderSQL)) {
                ps2.setInt(1, orderId);
                int rows = ps2.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Đếm tổng số đơn hàng
    public int countAll() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Tính tổng doanh thu
    public double sumRevenue() {
        double total = 0;
        String sql = "SELECT SUM(total) FROM orders"; // total là cột tổng tiền đơn
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public Map<String, Integer> countOrdersByStatus() {
        Map<String, Integer> result = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as cnt FROM orders GROUP BY status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                int cnt = rs.getInt("cnt");
                result.put(status, cnt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Double> getRevenueByMonth(int monthsBack) {
        Map<String, Double> revenueMap = new LinkedHashMap<>();
        String sql = "SELECT MONTH(order_date) as m, COALESCE(SUM(total),0) as revenue\n" +
                "FROM orders\n" +
                "WHERE order_date >= DATE_SUB(CURDATE(), INTERVAL ? MONTH)\n" +
                "GROUP BY MONTH(order_date)\n" +
                "ORDER BY m ASC;";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, monthsBack);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("m");
                double revenue = rs.getDouble("revenue");
                revenueMap.put(getMonthName(month), revenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenueMap;
    }

    // Helper
    private String getMonthName(int month) {
        return new DateFormatSymbols().getShortMonths()[month-1]; // Jan, Feb, ...
    }
}
package org.example.product_manager_java.dao;

import org.example.product_manager_java.model.OrderItem;
import org.example.product_manager_java.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT oi.id, oi.order_id, oi.product_id, p.name AS product_name, p.image, oi.quantity, oi.price " +
                "FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
        List<OrderItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("product_name"));
                    item.setProductImage(rs.getString("image"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    items.add(item);
                }
            }
        }
        return items;
    }
}
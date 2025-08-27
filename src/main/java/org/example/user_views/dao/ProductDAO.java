package org.example.user_views.dao;

import org.example.user_views.models.Category;
import org.example.user_views.models.Product;
import org.example.user_views.utils.DBConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProductDAO {
    public int countBySearch(String q) {
        String sql = "SELECT COUNT(*) FROM products p JOIN categories c ON p.category_id=c.id " +
                "WHERE LOWER(p.name) LIKE ?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + (q == null ? "" : q.toLowerCase()) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Product> findPaged(String q, int offset, int limit) {
        String sql = "SELECT p.id, p.name, p.price, p.quantity, p.image, p.description, " +
                "c.id AS cid, c.name AS cname " +
                "FROM products p JOIN categories c ON p.category_id=c.id " +
                "WHERE LOWER(p.name) LIKE ? ORDER BY p.id DESC LIMIT ? OFFSET ?";
        List<Product> list = new ArrayList<>();
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            String search = "%" + (q == null ? "" : q.toLowerCase()) + "%";
            System.out.println(">>> SQL: " + sql);
            System.out.println(">>> search = " + search + ", limit = " + limit + ", offset = " + offset);

            ps.setString(1, search);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setImage(rs.getString("image"));
                    p.setDescription(rs.getString("description"));
                    Category c = new Category(rs.getInt("cid"), rs.getString("cname"));
                    p.setCategory(c);
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }


    public Product findById(int id) {
        String sql = "SELECT p.id, p.name, p.price, p.quantity, p.image, p.description, " +
                "c.id AS cid, c.name AS cname FROM products p " +
                "JOIN categories c ON p.category_id=c.id WHERE p.id=?";
        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setImage(rs.getString("image"));
                    p.setDescription(rs.getString("description"));
                    p.setCategory(new Category(rs.getInt("cid"), rs.getString("cname")));
                    return p;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
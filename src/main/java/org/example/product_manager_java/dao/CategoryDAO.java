package org.example.product_manager_java.dao;

import org.example.product_manager_java.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.product_manager_java.utils.DBConnection.getConnection;

public class CategoryDAO {

    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public void insertCategory(Category c) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void updateCategory(Category c) {
        String sql = "UPDATE categories SET name=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void deleteCategory(int id) {
        String sql = "DELETE FROM categories WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Category getByIdCategory(int id) {
        String sql = "SELECT * FROM categories WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }

    public List<Category> searchByNameCategory(String keyword, int page, int pageSize) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE name LIKE ? LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }


    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }


    public List<Category> getCategoriesByPage(int page, int pageSize) {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public int getTotalCategoryCount() {
        String sql = "SELECT COUNT(*) FROM categories";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    // Lấy tổng số category tìm kiếm
    public int getTotalSearchCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM categories WHERE name LIKE ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }
}

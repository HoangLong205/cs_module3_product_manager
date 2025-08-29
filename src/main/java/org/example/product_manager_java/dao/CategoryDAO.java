package org.example.product_manager_java.dao;

import org.example.product_manager_java.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.product_manager_java.utils.DBConnection.getConnection;

public class CategoryDAO {


    private static final String SELECT_ALL_CATEGORIES_PAGED = "SELECT * FROM categories LIMIT ? OFFSET ?";
    private static final String COUNT_ALL_CATEGORIES = "SELECT COUNT(*) FROM categories";
    private static final String SEARCH_CATEGORIES_PAGED = "SELECT * FROM categories WHERE name LIKE ? LIMIT ? OFFSET ?";
    private static final String COUNT_SEARCH_CATEGORIES = "SELECT COUNT(*) FROM categories WHERE name LIKE ?";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
    private static final String INSERT_CATEGORY = "INSERT INTO categories (name) VALUES (?)";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET name=? WHERE id=?";
    private static final String DELETE_CATEGORY = "DELETE FROM categories WHERE id=?";

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


    public List<Category> getCategoriesByPage(int page, int pageSize) {
        List<Category> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_CATEGORIES_PAGED)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Category(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public int getTotalCategoryCount() {
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(COUNT_ALL_CATEGORIES)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    public List<Category> searchByNameCategory(String keyword, int page, int pageSize) {
        List<Category> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SEARCH_CATEGORIES_PAGED)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Category(rs.getInt("id"), rs.getString("name")));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public int getTotalSearchCount(String keyword) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(COUNT_SEARCH_CATEGORIES)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    public void insertCategory(Category c) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_CATEGORY)) {
            ps.setString(1, c.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void updateCategory(Category c) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_CATEGORY)) {
            ps.setString(1, c.getName());
            ps.setInt(2, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void deleteCategory(int id) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_CATEGORY)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Category getByIdCategory(int id) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_CATEGORY_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Category(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
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
}

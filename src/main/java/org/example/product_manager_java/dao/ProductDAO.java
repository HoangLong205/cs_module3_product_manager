package org.example.product_manager_java.dao;

import org.example.product_manager_java.model.Category;
import org.example.product_manager_java.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.product_manager_java.utils.DBConnection.getConnection;

public class ProductDAO {

    private static final String SELECT_ALL_PRODUCTS_PAGED = "SELECT p.*, c.name AS category_name FROM products p LEFT JOIN categories c ON p.category_id = c.id LIMIT ? OFFSET ?";
    private static final String COUNT_ALL_PRODUCTS = "SELECT COUNT(*) FROM products";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT p.*, c.name as category_name FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO products (name,price,quantity,image,description,category_id) VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_PRODUCT = "UPDATE products SET name = ?, price = ?, quantity = ?, image = ?, description = ?, category_id = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id=?";
    private static final String SEARCH_PRODUCTS_PAGED_BY_NAME = "SELECT p.*, c.name AS category_name FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE p.name LIKE ? LIMIT ? OFFSET ?";
    private static final String SEARCH_PRODUCTS_PAGED_BY_CATEGORY = "SELECT p.*, c.name AS category_name FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE c.name LIKE ? LIMIT ? OFFSET ?";
    private static final String COUNT_SEARCH_PRODUCTS_BY_NAME = "SELECT COUNT(*) FROM products p WHERE p.name LIKE ?";
    private static final String COUNT_SEARCH_PRODUCTS_BY_CATEGORY = "SELECT COUNT(*) FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE c.name LIKE ?";

    private Product getProductFromResultSet(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));
        p.setQuantity(rs.getInt("quantity"));
        p.setImage(rs.getString("image"));
        p.setDescription(rs.getString("description"));

        Category category = new Category();
        category.setId(rs.getInt("category_id"));
        category.setName(rs.getString("category_name"));
        p.setCategory(category);
        return p;
    }

    public List<Product> getProductsByPage(int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ALL_PRODUCTS_PAGED)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(getProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public int getTotalProductCount() {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(COUNT_ALL_PRODUCTS);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    public void insertProduct(Product p) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_PRODUCT)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getQuantity());
            ps.setString(4, p.getImage());
            ps.setString(5, p.getDescription());
            ps.setInt(6, p.getCategory().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void updateProduct(Product product) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PRODUCT)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setString(4, product.getImage());
            ps.setString(5, product.getDescription());
            ps.setInt(6, product.getCategory().getId());
            ps.setInt(7, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void deleteProduct(int id) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_PRODUCT)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Product getByIdProduct(int id) {
        Product product = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_PRODUCT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = getProductFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }

    public List<Product> searchProducts(String keyword, String field, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql;
        if ("category".equals(field)) {
            sql = SEARCH_PRODUCTS_PAGED_BY_CATEGORY;
        } else {
            sql = SEARCH_PRODUCTS_PAGED_BY_NAME;
        }

        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(getProductFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public int getTotalSearchCount(String keyword, String field) {
        String sql;
        if ("category".equals(field)) {
            sql = COUNT_SEARCH_PRODUCTS_BY_CATEGORY;
        } else {
            sql = COUNT_SEARCH_PRODUCTS_BY_NAME;
        }
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
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

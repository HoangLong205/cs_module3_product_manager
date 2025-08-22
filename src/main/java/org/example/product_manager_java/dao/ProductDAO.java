package org.example.product_manager_java.dao;

import org.example.product_manager_java.model.Category;
import org.example.product_manager_java.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.product_manager_java.utils.DBConnection.getConnection;

public class ProductDAO {

    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
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
                list.add(p);
            }
            System.out.println("[DAO] getAll() => " + list.size() + " sản phẩm");
        } catch (SQLException e) {
            printSQLException(e);
        }
        return list;
    }

    public void insertProduct(Product p) {
        String sql = "INSERT INTO products (name,price,quantity,image,description,category_id) VALUES(?,?,?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ?, image = ?, description = ?, category_id = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Product getByIdProduct(int id) {
        Product product = null;
        String sql = "SELECT p.*, c.name as category_name FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setImage(rs.getString("image"));
                    product.setDescription(rs.getString("description"));
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    product.setCategory(category);
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }


    public List<Product> searchProducts(String keyword, String field, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "WHERE ";

        if ("category".equals(field)) {
            sql += "c.name LIKE ? ";
        } else {
            sql += "p.name LIKE ? ";
        }
        sql += "LIMIT ? OFFSET ?";

        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
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

                list.add(p);
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

    public List<Product> getProductsByPage(int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.*, c.name AS category_name FROM products p " +
                "LEFT JOIN categories c ON p.category_id = c.id " +
                "LIMIT ? OFFSET ?";

        int offset = (page - 1) * pageSize;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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

                list.add(p);
            }

            System.out.println("[DAO] getProductsByPage() => " + list.size() + " sản phẩm");
        } catch (SQLException e) {
            printSQLException(e);
        }

        return list;
    }

    // Lấy tổng số sản phẩm để tính tổng số trang
    public int getTotalProductCount() {
        String sql = "SELECT COUNT(*) FROM products";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return 0;
    }

    public int getTotalSearchCount(String keyword, String field) {
        String sql = "SELECT COUNT(*) FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE ";
        if ("category".equals(field)) {
            sql += "c.name LIKE ?";
        } else {
            sql += "p.name LIKE ?";
        }

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

    // Đếm số sản phẩm
    public int countProducts() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM products";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


}

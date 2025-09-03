package org.example.product_manager_java.model;

import java.sql.Timestamp;
import java.util.List;


public class Order {
    private int id;
    private User user;
    private int userId;
    private Timestamp orderDate;
    private double total;
    private OrderStatus status;

    public enum OrderStatus {
        PENDING("Chờ xử lý", "warning"),
        CONFIRMED("Đã xác nhận", "info"),
        COMPLETED("Hoàn thành", "success"),
        CANCELLED("Đã hủy", "danger"),
        DELETED("Đã xóa", "secondary");

        private final String viText;
        private final String badgeClass;

        OrderStatus(String viText, String badgeClass) {
            this.viText = viText;
            this.badgeClass = badgeClass;
        }

        public String getViText() {
            return viText;
        }

        public String getBadgeClass() {
            return badgeClass;
        }
    }

    private List<OrderItem> items; // Quan hệ 1-nhiều

    public Order() {
    }

    public Order(User user, Timestamp orderDate, double total, OrderStatus status, List<OrderItem> items) {
        this.user = user;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.items = items;
    }


    public Order(int id, User user, Timestamp orderDate, double total, OrderStatus status, List<OrderItem> items) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.items = items;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}

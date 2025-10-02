package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.UserDAO;
import org.example.product_manager_java.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserController", urlPatterns = "/users")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> users = userDAO.selectAllUsers();

        // Gửi danh sách user sang JSP
        request.setAttribute("users", users);

        // Forward tới JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/user-list.jsp");
        dispatcher.forward(request, response);
    }
}

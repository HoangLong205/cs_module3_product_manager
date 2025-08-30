package org.example.user_view2.servlets;

import org.example.user_view2.dao.UserDAO;
import org.example.user_view2.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

//    xu ly yeu cau Get
//    nguoi dung chua dang nhap se duoc chuyen huog ve login


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/product/product-list");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User authUser = userDAO.findByUsernameAndPassword(username, password);

        if (authUser != null) {
            request.getSession().setAttribute("user", authUser);
            System.out.println(">>> LoginController: Đăng nhập thành công cho user: "
                    + authUser.getUsername() + " với vai trò: " + authUser.getRole());

            if ("ADMIN".equalsIgnoreCase(authUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/product/product-list");
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
        } else {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }
}

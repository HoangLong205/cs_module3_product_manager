package org.example.product_manager_java.filter;

import org.example.product_manager_java.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // -- DEBUG --
        System.out.println(">>> AuthFilter: Đang xử lý đường dẫn: " + path);

        // ... (phần code cho phép /login, /css, /js giữ nguyên)
        if (path.startsWith("/login") || path.startsWith("/register") || path.startsWith("/css/") || path.startsWith("/js/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // -- DEBUG --
            System.out.println(">>> AuthFilter: CHƯA ĐĂNG NHẬP. Chuyển hướng về /l" +
                    "ogin...");
            ((HttpServletResponse) servletResponse).sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            // -- DEBUG --
            System.out.println(">>> AuthFilter: Đã đăng nhập với vai trò: " + user.getRole());
            if (path.startsWith("/admin") && !"ADMIN".equals(user.getRole())) {
                // -- DEBUG --
                System.out.println(">>> AuthFilter: LỖI PHÂN QUYỀN! User thường truy cập trang Admin.");
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                // -- DEBUG --
                System.out.println(">>> AuthFilter: Hợp lệ. Cho phép đi tiếp...");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
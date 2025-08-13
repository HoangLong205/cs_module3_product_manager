package org.example.product_manager_java.controller;

import org.example.product_manager_java.dao.CategoryDAO;
import org.example.product_manager_java.model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/categories")
public class CategoryController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CategoryController.class.getName());

    private CategoryDAO dao;

    @Override
    public void init() {
        dao = new CategoryDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        logger.info("Action: " + action);
        if (action == null) action = "list";
        System.out.println("🛠 [GET] Action = " + action);
        switch (action) {
            case "new":
                System.out.println("➡ Mở form thêm mới category");
                request.getRequestDispatcher("/WEB-INF/category-form.jsp").forward(request, response);
                break;




            default:
                System.out.println("📋 Lấy danh sách category");
                request.setAttribute("list", dao.getAll());
                request.getRequestDispatcher("/WEB-INF/category-list.jsp").forward(request, response);
                break;
        }
    }


}

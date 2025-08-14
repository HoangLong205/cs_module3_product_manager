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
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                System.out.println("✏ Chỉnh sửa category ID = " + idEdit);
                request.setAttribute("category", dao.getByIdCategory(idEdit));
                request.getRequestDispatcher("/WEB-INF/category-form.jsp").forward(request, response);
                break;



            default:
                System.out.println("📋 Lấy danh sách category");
                request.setAttribute("list", dao.getAllCategory());
                request.getRequestDispatcher("/WEB-INF/category-list.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");

        if (id == null || id.isEmpty()) {
            System.out.println("➕ Thêm mới category: " + name);
            dao.insertCategory(new Category(name));
        } else {
            System.out.println("🔄 Cập nhật category ID = " + id + ", Name = " + name);
            dao.updateCategory(new Category(Integer.parseInt(id), name));
        }

        response.sendRedirect("categories");
    }
}

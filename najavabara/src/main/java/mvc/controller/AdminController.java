package mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.dao.UserDAO;
import mvc.dto.UserDTO;

@WebServlet("*.ad")
public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); // 한글처리

        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/"));
        
        if (action.equals("/adminPage.ad")) {
            HttpSession session = request.getSession();
            UserDTO currentUser = (UserDTO) session.getAttribute("user");

            if (currentUser != null && currentUser.getRole().equals("관리자")) {
                // 현재 사용자가 로그인되어 있고, 역할이 관리자인 경우에만 관리자 페이지로 이동
                request.getRequestDispatcher("/admin/adminPage.jsp").forward(request, response);
            } else {
            	response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
            }
        } else if(action.equals("/UserList.ad")) {
            // 사용자 목록 가져오기
            UserDAO userDAO = new UserDAO();
            List<UserDTO> userList = userDAO.getUsers();
            
            // request에 사용자 목록 속성으로 설정
            request.setAttribute("userList", userList);
            
            // 사용자 목록 페이지로 포워딩
            request.getRequestDispatcher("/admin/userList.jsp").forward(request, response);
        }
    }
}


package mvc.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import mvc.dao.BoardDAO;
import mvc.dto.BoardDTO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("*.po")
public class BoardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); // 한글처리

        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/"));
        System.out.println("URI: " + uri);
        System.out.println("Action: " + action);

        if (action.equals("/list.po")) {
            System.out.println("Listing boards");

            String searchField = request.getParameter("searchField");
            String searchWord = request.getParameter("searchWord");

            Map<String, String> map = new HashMap<>();
            map.put("searchField", searchField);
            map.put("searchWord", searchWord);

            BoardDAO dao = new BoardDAO();
            List<BoardDTO> boardLists = dao.selectList(map);
            int totalCount = dao.selectCount(map);

            request.setAttribute("boardLists", boardLists);
            request.setAttribute("totalCount", totalCount);

            String path = "/post/list.jsp";
            request.getRequestDispatcher(path).forward(request, response);

        } else if (action.equals("/write.po")) {
            String path = "/post/write.jsp";
            request.getRequestDispatcher(path).forward(request, response);

        } else if (action.equals("/submitPost.po")) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String id = request.getParameter("id");
            if (title != null && !title.isEmpty() && content != null && !content.isEmpty() && id != null && !id.isEmpty()) {
                BoardDAO dao = new BoardDAO();
                dao.insertPost(new BoardDTO(title, content, id));
            }
            response.sendRedirect("list.po");

        } else if (action.equals("/view.po")) {
            int num = Integer.parseInt(request.getParameter("num"));
            HttpSession session = request.getSession();
            BoardDAO dao = new BoardDAO();
            BoardDTO post = dao.selectById(num);
            
            // 방문자 수 증가
            HashMap<Integer, Date> visitedPosts = (HashMap)session.getAttribute("visitedPosts");
            if (visitedPosts == null) {
                visitedPosts = new HashMap<>();
            }

            Date lastVisited = visitedPosts.get(num);
            Date today = new Date();
            
            // 날짜 비교 로직
            boolean updateVisitCount = true;
            if (lastVisited != null) {
                // 같은 날짜인지 확인
                if (isSameDay(lastVisited, today)) {
                	updateVisitCount = false;
                }
            }
            if (updateVisitCount) {
                if (dao.updateVisitCount(post) > 0) {
                    System.out.println("Visit count updated successfully.");
                    // 최신 방문 날짜 업데이트
                    visitedPosts.put(num, today);
                    session.setAttribute("visitedPosts", visitedPosts);
                } else {
                    System.out.println("Failed to update visit count.");
                }
            }

            //게시물정보
            request.setAttribute("post", post);
            String path = "/post/view.jsp";
            request.getRequestDispatcher(path).forward(request, response);

        } else if (action.equals("/edit.po")) {
            int num = Integer.parseInt(request.getParameter("num"));
            BoardDAO dao = new BoardDAO();
            BoardDTO post = dao.selectById(num);
            request.setAttribute("post", post);
            String path = "/post/edit.jsp";
            request.getRequestDispatcher(path).forward(request, response);

        } else if (action.equals("/updatePost.po")) {
            int num = Integer.parseInt(request.getParameter("num"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String id = request.getParameter("id");
            if (title != null && !title.isEmpty() && content != null && !content.isEmpty() && id != null && !id.isEmpty()) {
                BoardDAO dao = new BoardDAO();
                dao.updatePost(new BoardDTO(num, title, content, id));
            }
            response.sendRedirect("list.po");

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

	private boolean isSameDay(Date date1, Date date2) {
		// TODO Auto-generated method stub
		 SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
	        return fmt.format(date1).equals(fmt.format(date2));
	}
}
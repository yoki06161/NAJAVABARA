package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.friendCommentDAO;
import dao.friendReplyDAO;
import dto.friendCommentDTO;
import dto.friendReplyDTO;
import dto.UserDTO;

@WebServlet("*.re")
public class friendReplyController extends HttpServlet {
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

		if (action.equals("/writeReply.re")) {
		    HttpSession session = request.getSession();
		    UserDTO user = (UserDTO) session.getAttribute("user");
		    int postNum = Integer.parseInt(request.getParameter("postNum"));

		    // 로그인되어 있지 않으면 로그인 페이지로 이동
		    if (user == null) {
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		        return;
		    }

		    // 답글 작성 처리
		    String replyContent = request.getParameter("reply");
		    int commentNum = Integer.parseInt(request.getParameter("commentNum"));

		    // 답글 객체 생성
		    friendReplyDTO newReply = new friendReplyDTO();
		    newReply.setCommentNum(commentNum);
		    newReply.setReply(replyContent);
		    newReply.setWriter(user.getId()); // 작성자 정보 설정

		    // 답글 추가하는 DAO 호출
		    friendReplyDAO replyDAO = new friendReplyDAO();
		    boolean result = replyDAO.insertReply(newReply);

		    if (result) {
		        // 답글 작성 성공 시, 원래 페이지로 포워드 또는 필요한 처리를 진행
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/friendBoard/viewPost.fri?num=" + postNum);
		        dispatcher.forward(request, response);
		    }
		} else if (action.equals("/deleteReply.re")) {
            int replyNum = Integer.parseInt(request.getParameter("replyNum"));
            int postNum = Integer.parseInt(request.getParameter("postNum"));

            // 답글 삭제 처리
            friendReplyDAO replyDAO = new friendReplyDAO();
            boolean result = replyDAO.deleteReply(replyNum);

            if (result) {
                // 답글 삭제 성공 시, 다시 게시물 상세보기 페이지로 이동
                response.sendRedirect(request.getContextPath() + "/friendBoard/viewPost.fri?num=" + postNum);
            }
        }
	}

}

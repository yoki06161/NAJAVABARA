package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ReviewCommentDAO;
import dto.ReviewCommentDTO;

@WebServlet("*.rev_co")
public class ReviewCommentController extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProc(req, resp);
	}
	private void doProc(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("댓글 컨트롤러 연결");
		
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"));
		System.out.println(uri);
		
// ########################### 댓글 작성 액션
		if(action.equals("/WriteReviewComment.rev_co")) {
			System.out.println("댓글쓰기 액션 연결");
			
			req.setCharacterEncoding("utf-8");
			String inum = req.getParameter("input_conum");
			String comment = req.getParameter("input_comment");
			int num = Integer.parseInt(inum);
			
			HttpSession session = req.getSession();
			String id = (String)session.getAttribute("id");
			
			System.out.println("댓글 번호" + num);
			System.out.println("댓글 내용" + comment);
			System.out.println("댓글 아이디" + id);
			
			ReviewCommentDAO cao = new ReviewCommentDAO();
			ReviewCommentDTO cto = new ReviewCommentDTO(num, comment, id);
			
			cao.WriteComments(cto);
			
			String path = req.getContextPath() + "/Review_board/review_content.jsp?cnum="+num;
			resp.sendRedirect(path);
			
		}
// ########################### 댓글 수정 액션
		else if(action.equals("/UpdateComment.rev_co")) {
			System.out.println("댓글 업데이트 연결");
			
			req.setCharacterEncoding("utf-8");
			
			String snum = req.getParameter("input_conum");
			String date = req.getParameter("input_time");
			String com = req.getParameter("up_comment");
			int num = Integer.parseInt(snum);
			
			ReviewCommentDAO cao = new ReviewCommentDAO();
			ReviewCommentDTO cto = new ReviewCommentDTO();
			cto.setComment(com);
			cto.setTdate(date);
			cao.UpdateComments(cto);
			
			String path = req.getContextPath() + "/Review_board/review_content.jsp?cnum="+num;
			resp.sendRedirect(path);
		}
// ########################### 댓글 삭제 액션
		else if(action.equals("/DeleteComment.rev_co")) {
			System.out.println("댓글 삭제 연결");
			
			req.setCharacterEncoding("utf-8");
			// 이게 콘텐트로 넘길떄 쓸 cnum
			String sdnum = req.getParameter("dnum");
			int cnum = Integer.parseInt(sdnum);
			String ddate = req.getParameter("ddate");
			
			ReviewCommentDAO cao = new ReviewCommentDAO();
			ReviewCommentDTO cto = new ReviewCommentDTO();
			cto.setTdate(ddate);

			cao.DeleteComments(cto);
			
			String path = req.getContextPath() + "/Review_board/review_content.jsp?cnum="+cnum;
			resp.sendRedirect(path);
		}
		
	}
}

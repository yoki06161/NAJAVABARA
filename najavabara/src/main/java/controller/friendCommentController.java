package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.friendCommentDAO;
import dto.friendCommentDTO;

@WebServlet("*.co")
public class friendCommentController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8"); // 한글 처리

        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/"));

        if (action.equals("/writeComment.co")) {
            // 댓글 작성 처리
            String commentContent = request.getParameter("comment");
            int postNum = Integer.parseInt(request.getParameter("postNum"));

            // 현재 로그인한 사용자 정보 가져오기
            HttpSession session = request.getSession();
            String user = (String) session.getAttribute("id");

            if (user != null) {
                String writer = (String) session.getAttribute("name"); // 댓글 작성자는 현재 로그인한 사용자

                // 댓글 객체 생성
                friendCommentDTO newComment = new friendCommentDTO(postNum, commentContent, writer);

                // 댓글 추가하는 DAO 호출
                friendCommentDAO commentDao = new friendCommentDAO();
                boolean result = commentDao.insertComment(newComment);

                if (result) {
                    // 댓글 작성 성공 시, 댓글 목록을 다시 불러와서 설정
                    List<friendCommentDTO> updatedCommentList = commentDao.getCommentsByPostNum(postNum, "asc");
                    request.setAttribute("commentList", updatedCommentList);
                    
                    // 게시물 상세보기 페이지로 리다이렉트
                    response.sendRedirect(request.getContextPath() + "/friendBoard/viewPost.fri?num=" + postNum);
                }
            } else {
                // 로그인되지 않은 사용자는 로그인 페이지로 이동
                response.sendRedirect(request.getContextPath() + "/user/login.usr");
            } 
        } else if (action.equals("/deleteComment.co")) {
            // 삭제할 댓글의 ID와 게시물 번호를 파라미터에서 가져옴
            int commentNum = Integer.parseInt(request.getParameter("commentNum"));
            int postNum = Integer.parseInt(request.getParameter("postNum"));

            // CommentDAO를 사용하여 댓글 삭제 수행
            friendCommentDAO commentDao = new friendCommentDAO();
            boolean deleted = commentDao.deleteComment(commentNum);

            if (deleted) {
                // 삭제 성공 시, 삭제된 댓글을 제외한 댓글 목록을 다시 가져와 설정
                List<friendCommentDTO> updatedCommentList = commentDao.getCommentsByPostNum(postNum, "asc");
                request.setAttribute("commentList", updatedCommentList);
                
                // 게시물 상세보기 페이지로 리다이렉트
                response.sendRedirect(request.getContextPath() + "/friendBoard/viewPost.fri?num=" + postNum);
            }
        } 
    }
}

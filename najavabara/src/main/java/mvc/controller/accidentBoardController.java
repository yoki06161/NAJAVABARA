package mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.dao.accidentBoardDAO;
import mvc.dao.accidentCommentDAO;
import mvc.dto.accidentBoardDTO;
import mvc.dto.accidentCommentDTO;

@WebServlet("*.acc")
public class accidentBoardController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // 한글처리

		String postNumStr2 = request.getParameter("postNum");
		System.out.println(postNumStr2); // postNum 값 출력 (디버그 용도)

		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"));
		System.out.println("URI: " + uri);
		System.out.println("Action: " + action);

		if (action.equals("/list.acc")) {
			System.out.println("Listing boards");

			String searchField = request.getParameter("searchField");
			String searchWord = request.getParameter("searchWord");

			if (searchField == null || searchField.equals("null")) {
				searchField = "";
			}
			if (searchWord == null || searchWord.equals("null")) {
				searchWord = "";
			}

			// 기본 페이지 번호를 1로 설정
			int page = 1;
			String pageStr = request.getParameter("page");
			if (pageStr != null && !pageStr.isEmpty()) {
				page = Integer.parseInt(pageStr);
			}

			int limit = 10; // 한 페이지에 보여줄 게시글 수
			int offset = (page - 1) * limit;

			Map<String, Object> map = new HashMap<>();
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
			map.put("offset", offset);
			map.put("limit", limit);

			accidentBoardDAO dao = new accidentBoardDAO();
			List<accidentBoardDTO> boardLists = dao.selectList(map);
			int totalCount = dao.selectCount(map);

			int totalPages = (int) Math.ceil((double) totalCount / limit);

			request.setAttribute("boardLists", boardLists);
			request.setAttribute("totalCount", totalCount);
			request.setAttribute("page", page); // 여기서 설정한 속성이 JSP에서 currentpage로 사용됩니다.
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("searchField", searchField);
			request.setAttribute("searchWord", searchWord);

			String path = "/accidentPost/list.jsp";
			request.getRequestDispatcher(path).forward(request, response);
		} else if (action.equals("/write.acc")) {
			String path = "/accidentPost/write.jsp";
			request.getRequestDispatcher(path).forward(request, response);

		} else if (action.equals("/submitPost.acc")) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String id = request.getParameter("id");
			if (title != null && !title.isEmpty() && content != null && !content.isEmpty() && id != null
					&& !id.isEmpty()) {
				accidentBoardDAO dao = new accidentBoardDAO();
				dao.insertPost(new accidentBoardDTO(title, content, id));
			}
			response.sendRedirect("list.acc");

		} else if (action.equals("/view.acc")) {
			String numStr = request.getParameter("num");
			if (numStr == null) {
				response.sendRedirect("list.acc"); // num 파라미터가 없는 경우 목록 페이지로 리다이렉트
				return;
			}

			int num = Integer.parseInt(numStr);
			HttpSession session = request.getSession();
			accidentBoardDAO dao = new accidentBoardDAO();
			accidentBoardDTO post = dao.selectById(num);

			// 방문자 수 증가
			@SuppressWarnings("unchecked")
			HashMap<Integer, Date> visitedPosts = (HashMap<Integer, Date>) session.getAttribute("visitedPosts");
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

			// 게시물 정보와 댓글 목록 설정
			if (post != null) {
				accidentCommentDAO commentDAO = new accidentCommentDAO();
				List<accidentCommentDTO> comments = commentDAO.getComments(num);
				request.setAttribute("post", post);
				request.setAttribute("comments", comments);
			}
			String path = "/accidentPost/view.jsp";
			request.getRequestDispatcher(path).forward(request, response);

		} else if (action.equals("/edit.acc")) {
			int num = Integer.parseInt(request.getParameter("num"));
			accidentBoardDAO dao = new accidentBoardDAO();
			accidentBoardDTO post = dao.selectById(num);
			request.setAttribute("post", post);
			String path = "/accidentPost/edit.jsp";
			request.getRequestDispatcher(path).forward(request, response);

		} else if (action.equals("/updatePost.acc")) {
			int num = Integer.parseInt(request.getParameter("num"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String id = request.getParameter("id");
			if (title != null && !title.isEmpty() && content != null && !content.isEmpty() && id != null
					&& !id.isEmpty()) {
				accidentBoardDAO dao = new accidentBoardDAO();
				dao.updatePost(new accidentBoardDTO(num, title, content, id));
			}
			response.sendRedirect("list.acc");

		} // 좋아요 싫어요
		else if (action.equals("/likeDislike.acc")) {
			int postId = Integer.parseInt(request.getParameter("postId"));
			String actionType = request.getParameter("action");
			accidentBoardDAO dao = new accidentBoardDAO();
			boolean isLike = "like".equals(actionType);

			dao.updateLikes(postId, isLike);

			// JSON 응답 생성
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print("{");
			out.printf("\"likes\": %d, \"dislikes\": %d", dao.getLikes(postId), dao.getDislikes(postId));
			out.print("}");
			out.flush();
			return;
		} else if (action.equals("/searchResults.acc")) {
			String searchWord = request.getParameter("searchWord");
			accidentBoardDAO dao = new accidentBoardDAO();
			List<accidentBoardDTO> results;

			if (searchWord != null && !searchWord.isEmpty()) {
				results = dao.selectList(Map.of("searchField", "title", "searchWord", searchWord));
			} else {
				results = dao.selectAll(); // 검색어가 없으면 모든 게시물을 가져옴
			}

			request.setAttribute("results", results); // 검색 결과를 요청 속성에 저장
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list.acc"); // 결과 페이지로 포워드
			dispatcher.forward(request, response);
		} else if (action.equals("/addComment.acc")) {
			System.out.println(">>>>>>>>>>>>>>>>>>");
			// 댓글 작성 폼에서 전달된 postNum 파라미터 값을 문자열로 받아옴
			String postNumStr = request.getParameter("postNum");
			String comment2 = request.getParameter("comment");
			System.out.println(comment2); // postNum 값 출력 (디버그 용도)

			// postNum 값이 null이거나 비어있는지 확인
			if (postNumStr == null || postNumStr.trim().isEmpty()) {
				response.sendRedirect("list.acc");
				return;
			}

			// postNum 값을 정수로 변환
			int postNum = Integer.parseInt(postNumStr);

			// 댓글 작성자와 댓글 내용을 받아옴
			String writer = request.getParameter("writer");
			String commentText = request.getParameter("comment");

			// 디버그 로그 추가: postNum, writer, commentText 값 출력
			System.out.println("postNum: " + postNum);
			System.out.println("writer: " + writer);
			System.out.println("commentText: " + commentText);

			// 게시물 존재 여부 확인을 위해 DAO를 사용하여 게시물 조회
			accidentBoardDAO boardDAO = new accidentBoardDAO();
			accidentBoardDTO post = boardDAO.selectById(postNum);
			if (post == null) {
				// 게시물이 없으면 목록 페이지로 리디렉션
				response.sendRedirect("list.acc");
				return;
			}

			// 새로운 댓글 객체 생성 및 데이터 설정
			accidentCommentDTO comment = new accidentCommentDTO();
			comment.setPostNum(postNum);
			comment.setWriter(writer);
			comment.setComment(commentText);

			// DAO를 사용하여 댓글 추가
			accidentCommentDAO commentDAO = new accidentCommentDAO();
			boolean success = commentDAO.addComment(comment);

			// 댓글 추가 성공 여부에 따라 페이지 이동
			if (success) {
				// 성공 시 게시물 보기 페이지로 리디렉션
				response.sendRedirect("view.acc?num=" + postNum);
			} else {
				// 실패 시 경고 메시지 출력 후 이전 페이지로 돌아감
				PrintWriter out = response.getWriter();
				out.println("<script>alert('Failed to add comment.'); history.back();</script>");
			}
		}
	}

	private boolean isSameDay(Date date1, Date date2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(date1).equals(fmt.format(date2));
	}
}

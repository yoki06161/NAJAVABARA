package mvc.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.dao.CommentDAO;
import mvc.dao.friendBoardDAO;
import mvc.dao.UserDAO;
import mvc.dto.CommentDTO;
import mvc.dto.friendBoardDTO;
import mvc.dto.UserDTO;

@WebServlet("*.do")
public class UserController extends HttpServlet {
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

		if (action.equals("/loginForm.do")) {
			// 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		} else if (action.equals("/registerForm.do")) {
			// 회원가입 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/user/registerForm.jsp");
		} else if (action.equals("/index.do")) {
			// 메인 페이지로 이동
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else if (action.equals("/register.do")) {
			// 회원가입 처리
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String role = request.getParameter("role");

			// 값 확인
			// System.out.printf("%s,%s,%s,%s",id,password,name,role);

			// 회원 가입 시간을 현재 시간으로 설정
			Date utilDate = new Date();
			Timestamp sqlTime = new Timestamp(utilDate.getTime());

			// UserDTO 객체 생성
			UserDTO user = new UserDTO(id, password, name, role, sqlTime.toString());

			// UserDAO 객체 생성
			UserDAO userDao = new UserDAO();

			// 회원 추가 결과 확인
			int rs = userDao.insertUser(user);

			if (rs == 1) {
				// 회원 가입 성공 시 로그인 페이지로 이동
				response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
			} else {
				// 회원 가입 실패 시 회원가입 페이지로 이동
				response.sendRedirect(request.getContextPath() + "/user/registerForm.jsp");
			}
		} else if (action.equals("/login.do")) {
			// 로그인 처리
			String id = request.getParameter("id");
			String password = request.getParameter("password");

			// UserDAO 객체 생성
			UserDAO userDao = new UserDAO();

			// 입력된 아이디로 사용자 정보를 조회합니다.
			UserDTO user = new UserDTO();
			user.setId(id);
			UserDTO dbUser = userDao.getUser(user);

			if (dbUser != null) {
				// 데이터베이스에서 조회된 사용자 정보가 있을 경우
				if (dbUser.getPw().equals(password)) {
					// 비밀번호가 일치하는 경우 로그인 성공
					// 세션에 사용자 정보 저장
					HttpSession session = request.getSession();
					session.setAttribute("user", dbUser);

					// 로그인 성공 시 메인 페이지로 이동
					response.sendRedirect(request.getContextPath() + "/index.jsp");
				} else {
					// 비밀번호가 일치하지 않는 경우
					// 로그인 페이지로 다시 이동
					response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=password");
				}
			} else {
				// 해당 아이디로 사용자 정보가 조회되지 않는 경우
				// 로그인 페이지로 다시 이동
				response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp?error=id");
			}
		} else if (action.equals("/logout.do")) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else if (action.equals("/myPage.do")) {
			// 세션에서 현재 로그인한 사용자 정보 가져오기
		    HttpSession session = request.getSession();
		    UserDTO currentUser = (UserDTO) session.getAttribute("user");

		    if (currentUser != null) {
		        // 사용자가 로그인한 상태라면 내 정보 페이지로 이동
		        response.sendRedirect(request.getContextPath() + "/user/myPage.jsp");
		    } else {
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		    
		} else if (action.equals("/editUserInfo.do")) {
			// 세션에서 현재 로그인한 사용자 정보 가져오기
		    HttpSession session = request.getSession();
		    UserDTO currentUser = (UserDTO) session.getAttribute("user");

		    if (currentUser != null) {
		    	request.setAttribute("user", currentUser);
		        request.getRequestDispatcher("/user/editUserInfo.jsp").forward(request, response);
		    } else {
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		} else if (action.equals("/updateUserInfo.do")) {
		    // 세션에서 현재 로그인한 사용자 정보 가져오기
		    HttpSession session = request.getSession();
		    UserDTO currentUser = (UserDTO) session.getAttribute("user");

		    if (currentUser != null) {
		        // 사용자가 로그인한 상태라면 정보 수정
		        String currentPassword = request.getParameter("currentPassword");
		        String newPassword = request.getParameter("newPassword");

		        // 현재 비밀번호 확인
		        if (currentUser.getPw().equals(currentPassword)) {
		            // 현재 비밀번호가 일치하는 경우, 새로운 비밀번호로 사용자 정보 업데이트
		            currentUser.setName(request.getParameter("name"));
		            currentUser.setPw(newPassword); // 새로운 비밀번호 설정

		            UserDAO userDao = new UserDAO();
		            userDao.updateUser(currentUser);

		            // 사용자 정보 수정 완료 후 메인 페이지로 이동
		            response.sendRedirect(request.getContextPath() + "/user/myPage.jsp");
		        } else {
		            // 현재 비밀번호가 일치하지 않는 경우
		            // 사용자 정보 수정 페이지로 다시 이동
		            response.sendRedirect(request.getContextPath() + "/user/editUserInfo.jsp?error=password");
		        }
		    } else {
		        // 로그인되어 있지 않으면 로그인 페이지로 이동
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		} else if (action.equals("/myPost.do")) {
		    // 세션에서 현재 로그인한 사용자 정보 가져오기
		    HttpSession session = request.getSession();
		    UserDTO currentUser = (UserDTO) session.getAttribute("user");

		    if (currentUser != null) {
		        // 사용자가 로그인한 상태일 때
		        // 현재 사용자의 게시물을 가져오는 코드 작성
		        friendBoardDAO postDao = new friendBoardDAO();
		        List<friendBoardDTO> postList = postDao.getPostByUserId(currentUser.getId());

		        // 총 게시물 수 조회
		        int totalCount = postList.size();

		        // 현재 페이지 번호 가져오기
		        int pageNum;
		        String pageNumStr = request.getParameter("pageNum");
		        if (pageNumStr != null) {
		            pageNum = Integer.parseInt(pageNumStr);
		        } else {
		            pageNum = 1; // 기본 페이지 번호를 1로 설정
		        }
		        
		        CommentDAO commentDAO = new CommentDAO();
			    for (friendBoardDTO post : postList) {
			        List<CommentDTO> comments = commentDAO.getCommentsByPostNum(post.getNum());
			        int commentCount = comments != null ? comments.size() : 0;
			        post.setCommentCount(commentCount);
			    }

		        // 가져온 게시물과 총 게시물 수, 페이지 번호를 request에 저장
		        request.setAttribute("postList", postList);
		        request.setAttribute("totalCount", totalCount);
		        request.setAttribute("pageNum", pageNum);

		        // myPosts.jsp로 포워딩
		        request.getRequestDispatcher("/user/myPost.jsp").forward(request, response);
		    } else {
		        // 로그인되어 있지 않은 경우 로그인 페이지로 이동
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		} else if (action.equals("/myComments.do")) {
		    // 세션에서 현재 로그인한 사용자 정보 가져오기
		    HttpSession session = request.getSession();
		    UserDTO currentUser = (UserDTO) session.getAttribute("user");

		    if (currentUser != null) {
		        // 사용자가 로그인한 상태일 때
		        // 현재 사용자가 작성한 댓글을 가져오는 코드 작성
		        CommentDAO commentDao = new CommentDAO();
		        List<CommentDTO> commentList = commentDao.getCommentsByUserId(currentUser.getId());

		        // 가져온 댓글 목록을 request에 저장
		        request.setAttribute("commentList", commentList);

		        // 포스트를 가져오는 코드 추가
		        friendBoardDAO postDao = new friendBoardDAO();
		        List<friendBoardDTO> postList = postDao.getPostByUserId(currentUser.getId());
		        request.setAttribute("postList", postList);

		        // myComments.jsp로 포워딩
		        request.getRequestDispatcher("/user/myComments.jsp").forward(request, response);
		    } else {
		        // 로그인되어 있지 않은 경우 로그인 페이지로 이동
		        response.sendRedirect(request.getContextPath() + "/user/loginForm.jsp");
		    }
		}
	}

}

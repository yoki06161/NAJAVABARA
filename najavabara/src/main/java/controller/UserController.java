package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.UserDTO;

@WebServlet("*.usr")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/// join.jsp? login.jsp? joinProc?
		System.out.println("doProcess");
		request.setCharacterEncoding("utf-8"); // 한글처리

		String uri = request.getRequestURI();
		String action = uri.substring(uri.lastIndexOf("/"));
		System.out.println(uri);
		if (action.equals("/index.usr")) {
			String path = request.getContextPath() + "/index.jsp";
			response.sendRedirect(path);
		// 유저 메인 페이지
		}else if(action.equals("/userMain.usr")) {
			String path = request.getContextPath() + "/user/userMain.jsp";
			response.sendRedirect(path);
		// 회원가입 페이지
		}else if(action.equals("/join.usr")) {
			String path = request.getContextPath() + "/user/join.jsp";
			response.sendRedirect(path);
		}else if(action.equals("/joinProc.usr")) {
			// 값을 받아서 찍어보기
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String area = request.getParameter("area");
			//System.out.printf("%s,%s,%s,%s",id,pw,name,area);
			
			// db 처리
			UserDAO dao = new UserDAO();
			UserDTO dto = new UserDTO(id, pw, name, area);
			dao.insertUser(dto);
			
			// move
			String path = request.getContextPath() +"/user/login.usr";
			response.sendRedirect(path);	
		// 로그인 페이지
		}else if(action.equals("/login.usr")) {
			String path = request.getContextPath() + "/user/login.jsp";
			response.sendRedirect(path);
		// 로그인 처리
		}else if(action.equals("/loginProc.usr")) {
			// 값을 받고 찍어보기
			request.setCharacterEncoding("utf-8"); // 한글 처리
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			int idx = 0;
			String name="";
			String role="";
			boolean isLogin = false;
			
			// db 처리
			UserDAO dao = new UserDAO();
			UserDTO dto = new UserDTO();
			
			// 사용자 정보(UserDTO가 DB에 있는지 확인)
			dto.setId(id);
			dto = dao.getUser(dto);
			if(dto != null) {
				if(pw.equals(dto.getPw())) {
					isLogin = true;
				}
			} else if (dto == null) {
				//한글변환 후 알림창 표시+이전 화면으로 이동
			    response.setContentType("text/html; charset=utf-8");
				PrintWriter w = response.getWriter();
		        w.println("<script>alert('계정이 존재하지 않습니다.');history.go(-1);</script>");
		        w.flush();
		        w.close();	
			} 
			
			String jspFile = "";
			if (isLogin){ // 로그인 성공 시 사용자 정보 반환 
				response.setContentType("text/html; charset=utf-8");
				HttpSession session =  request.getSession();
				session.setAttribute("idx", dto.getIdx());
				session.setAttribute("id", id);
				session.setAttribute("name", dto.getName());
				session.setAttribute("area", dto.getArea());
				session.setAttribute("role", dto.getRole());
				jspFile = "/userMain.usr";
				System.out.printf("%d %s %s %s",idx, id, name, role);
			}else {  // 로그인 실패 시 
				//한글변환 후 알림창 표시+이전 화면으로 이동
			    response.setContentType("text/html; charset=utf-8");
				PrintWriter w = response.getWriter();
		        w.write("<script>alert('비밀번호가 다릅니다.');history.go(-1);</script>");
		        w.flush();
		        w.close();			
				jspFile = "/login.usr";
			}
			// 3. move
			String path = request.getContextPath() + jspFile;
			response.sendRedirect(path);
		// 회원정보 수정 창
		}else if(action.equals("/update.usr")) {
			// 값 - dto - id 값
			HttpSession session = request.getSession();
			String id = (String)session.getAttribute("id");

			UserDTO dto = new UserDTO();
			dto.setId(id);

			// service : dao
			UserDAO dao = new UserDAO();
			UserDTO user =  dao.getUser(dto);

			request.setAttribute("user", user);
			// move. get, 2. forward - reqeust.setAttribute("v","o")

			String path =  "./update.jsp"; // 1
			request.getRequestDispatcher(path).forward(request, response);
		// 회원정보 수정 처리
		}else if(action.equals("/updateProc.usr")) {
			// 값을 받고 찍어보기
			HttpSession session = request.getSession();
			
			request.setCharacterEncoding("utf-8"); // 한글 처리
			String id = (String)session.getAttribute("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String area = request.getParameter("area");
			
			// db처리
			UserDAO dao = new UserDAO();
			UserDTO dto = new UserDTO(id, pw, name, area);
			int rs = dao.updateUser(dto);
			
			//session.setAttribute("name",name);
			String path = request.getContextPath() +"/user/update.usr";
			response.sendRedirect(path);
		// 회원 탈퇴 페이지
		}else if(action.equals("/delete.usr")) {
			String path = request.getContextPath() +"/user/delete.jsp";
			response.sendRedirect(path);
		// 회원 탈퇴 처리
		}else if(action.equals("/deleteProc.usr")) {
			// 값을 받고 찍어보기
			HttpSession session = request.getSession();
			
			request.setCharacterEncoding("utf-8"); // 한글 처리
			String id = (String)session.getAttribute("id");
			String pw = request.getParameter("pw");
			int rs = 0;
			boolean isCheck = false;
			//System.out.println(id);
			
			// db처리
			UserDAO dao = new UserDAO();
			UserDTO dto = new UserDTO();
			dto.setId(id);
			dto= dao.getUser(dto);
			
			if(dto != null) {
				//비밀번호 확인
				if(pw.equals(dto.getPw())) {
					rs = dao.deleteUser(dto);
					isCheck = true;
				}
			}
			
			//한글변환 후 알림창 표시
		    response.setContentType("text/html; charset=utf-8");
		    PrintWriter w = response.getWriter();
			String path = "";
			if(isCheck) {
				//이건 그냥 화면에 글자출력임...
				//response.getWriter().write("그동안 이용해주셔서 감사합니다.");
				w.write("<script>alert('그동안 이용해주셔서 감사합니다.');location.href='/myproject/user/userMain.usr';</script>");
				w.flush();
				w.close();
				System.out.println(pw);
				session.invalidate();
			}else if(pw != "") {
				System.out.println(pw);
				//알림창 표시후 이전 화면으로 이동
		        w.write("<script>alert('비밀번호가 다릅니다.');history.go(-1);</script>");
		        w.flush();
		        w.close();			
			}
			
		// 로그아웃
		}else if(action.equals("/logout.usr")) {
			HttpSession session = request.getSession();
			session.invalidate();
			String path = request.getContextPath() + "/userMain.usr";
			response.sendRedirect(path);
		}
	}
}

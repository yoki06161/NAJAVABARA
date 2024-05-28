<%@page import="proj.connect.HJDBCConnect"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");
	// login.jsp의 form이 보낸 name = "id"값을 불러온것.
	String id = request.getParameter("id");
	String password = request.getParameter("password");
	String pw_bo = request.getParameter("password_re");
	String name = request.getParameter("name");
	String area = request.getParameter("area");

%>

<%
// id값이랑 pw값 나오나 출력한것.
	System.out.println(id);
	System.out.println(password);
	System.out.println(pw_bo);
	System.out.println(name);
	System.out.println(password.equals(pw_bo));
	System.out.println(area);

%>

<%
// 비밀번호 일치 여부 확인
if(password.equals(pw_bo)) {
	// else의 비번 일치 안할시 나올 문구를 지움
	session.removeAttribute("em");
	
	// conn이랑 동일한 db연결
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		conn = HJDBCConnect.getConnection();
		
		System.out.println("연결됨");
		
		String sql = "insert into user(id, pw, name, area) values(?,?,?,?)";
		// pstmt에 sql쿼리문 담음
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.setString(2, password);
		pstmt.setString(3, name);
		pstmt.setString(4, area);
		
		pstmt.executeUpdate();
		response.sendRedirect("login.jsp");

	} catch(Exception e) {
		e.printStackTrace();
	}finally{
		pstmt.close();
		conn.close();
	}
} else {
	// 에러 메시지를 설정하여 회원가입 페이지로 이동
    // em에 비밀번호를 일치시켜달란 문구를 넣음
    // request는 딱 이페이지내에서만 쓸수 있다. session으로 해야 다른 클래스에 보내짐
    session.setAttribute("em", "비밀번호가 일치하지 않습니다.");
	response.sendRedirect("join.jsp");
	System.out.println("비번 일치안함");

}
%>

    
<%@page import="proj.connect.HJDBCConnect"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	// login.jsp의 form에서 보낸 id값 받은것.
	String id = request.getParameter("id");
	String password = request.getParameter("password");
	
	String name = "";
	String role = "";
	String area = "";
	Date regdate = null;
	boolean islogin = false;
%>

<%
	// id값이랑 pw값 나오나 출력테스트
	// System.out.println(id);
	// System.out.println(password);
%>

<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		conn = HJDBCConnect.getConnection();
		
		String sql = "select * from user where id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		if(rs.next()){
			String dbpw = rs.getString("pw");	// 데이터베이스의 password를 dbpw에 할당
			// pw값 비교후 옳을시 로그인 성공.
			if(dbpw.equals(password)) {
				name = rs.getString("name");
				role = rs.getString("role");
				area = rs.getString("area");
				regdate = rs.getDate("regdate");
				islogin = true;
			}
		}
	} catch(Exception e) {
		e.printStackTrace();
	} finally {
		rs.close();
		pstmt.close();
		conn.close();
	}
%>

<%
if(islogin) {
	session.setAttribute("id", id);
	session.setAttribute("password", password);
	session.setAttribute("name", name);
	session.setAttribute("role", role);
	session.setAttribute("area", area);
	session.setAttribute("regdate", regdate);
	// response= 페이지전송
	response.sendRedirect("../index.jsp");
} else {
	response.sendRedirect("login.jsp");
	// id랑 비번 값 틀렸을때 코드 쓰자
}
%>
<%@page import="proj.dto.HUserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateproc.jsp</title>
</head>
<body>
	<%@ include file="dbconn.jsp" %>
	<%
		request.setCharacterEncoding("UTF-8");
		
	 	String id = (String)session.getAttribute("id"); 
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String area = request.getParameter("area");
		PreparedStatement pstmt = null;
		
		try{
			String sql = "update user set pw=?, name=? where id =? area=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pw);
			pstmt.setString(2, name);
			pstmt.setString(3, id);
			pstmt.setString(4, area);
			
			pstmt.executeUpdate();
			
	        session.setAttribute("pw", pw);
	        session.setAttribute("name", name);
	        session.setAttribute("area", area);
	        
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	%>
	<%
		response.sendRedirect("../index.jsp");
	%>
	<br>
	<a href="delete.jsp">계정 삭제하기</a><br>
	<a href="../index.jsp">메인 화면으로</a>
</body>
</html>
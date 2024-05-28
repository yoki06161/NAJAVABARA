<%@page import="java.io.Console"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginproc.jsp</title>
</head>
<body>
	<%@ include file="dbconn.jsp" %>
	<%
		request.setCharacterEncoding("UTF-8");
		String id = (String) session.getAttribute("id"); 
		String password = (String) session.getAttribute("pw"); 
		int idx = 0;
		String name = "";
		String role = "";
		String regdate = "";
		boolean isLogin = false;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			String sql = "select * from user where id= ? && pw= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			if(rs.next()){ // id 존재
				idx = rs.getInt("idx");
				name = rs.getString("name");
				role = rs.getString("role");
				regdate = rs.getString("regdate");
				isLogin = true;
			}
			
		}catch(SQLException e){
			out.println("SQLException : " + e.getMessage());
		}finally{
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
	%>

	<%	
		if(isLogin){
			response.sendRedirect("update.jsp");		
		}else{
			response.sendRedirect("idcheck.jsp");
		}
	%>
</body>
</html>
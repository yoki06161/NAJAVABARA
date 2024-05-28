<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>connection.jsp</title>
</head>
<body>
	<%
		Connection conn = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://54.253.205.165:3306/teampro?serverTimezone=UTC";
		String user = "hobby";
		String pw = "hobby";
		
		try{
		Class.forName(driver);
		conn = DriverManager.getConnection(url, user, pw);
		out.print("데이터베이스 연결 성공");			
		}catch(Exception e){
			out.print("데이터베이스 연결 실패.<br>");
			out.print(e.getMessage());
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.close();
			}
		}
		
		
	%>
</body>
</html>
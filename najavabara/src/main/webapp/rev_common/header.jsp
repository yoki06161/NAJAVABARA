<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	#header {
		height: 100px;
		border-bottom: 1px solid #ddd; 
		display: flex;
		align-items: center;
		justify-content: flex-end;
		padding: 0 50px;
	}
</style>
</head>
<body>
<header id="header">
<!-- id여부에 따른 로그인 기능 보여주기 -->
<%if(session.getAttribute("id") == null) { %>
<a href = "../login.jsp">로그인</a>
<%}else { %>
<a href = "../logout.jsp">로그아웃</a><br>헤더
<%} %>
</header>
</body>
</html>
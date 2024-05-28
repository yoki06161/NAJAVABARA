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
		justify-content: space-between;
		padding: 0 50px;
		gap: 10px;
		margin-bottom: 50px;
	}
	
	a {
		text-decoration: none;
		color: #0d6efd;
	}
</style>
<!-- 
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
    crossorigin="anonymous">
 -->
</head>
<body>
<header id="header">
<div class="left">
	<a href="#">지역별</a>
	<a href="#">사건사고</a>
	<a href="#">동네친구</a>
	<a href="#">취미</a>
	<a href="#">장소리뷰</a>
	<a href="#">자유게시판 </a>
</div>
<div class="right">
	<%if(session.getAttribute("id") == null) { %>
		<a href = "../login.jsp">로그인</a>
	<%}else { %>
		<%=session.getAttribute("id")%>(<%=session.getAttribute("name")%>)님 환영합니다 |
		<a href="#">내 정보 수정</a> | 
		<a href = "../logout.jsp">로그아웃</a><br>
	<%} %>
</div>
</header>
</body>
</html>
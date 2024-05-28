<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
</head>
<h3>
<ul>
<li>
<a href="../user/userMain.usr">홈</a> |
<% if(session.getAttribute("idx") == null) {%>
<a href="../user/login.usr">로그인</a> 
<%}else{ %>
<%=session.getAttribute("id") %>(<%=session.getAttribute("name") %>)님 환영합니다! |
<a href="../user/update.usr">내 정보 수정</a> | 
<a href="../user/logout.usr">로그아웃</a> 
<%} %>
</li>
<!-- <li>
<a href="/myproject/user/userlist.usr">User List</a>
</li>  -->
<li>
<a href="../list.reg">지역 게시판</a>
<a class="nav-link" href="${pageContext.request.contextPath}/friendBoard/friendBoard.fri">동네 친구 게시판</a>
<a href="list.acc">사건사고 게시글</a> | <a href="write.acc">글쓰기</a>
</li>
</ul>
</h3>
<hr>
<br>
<body>
<style>
body {
	max-width: 1280px;
	margin: 0 auto;
	padding: 50px 0;
}
</style>
</body>
</html>
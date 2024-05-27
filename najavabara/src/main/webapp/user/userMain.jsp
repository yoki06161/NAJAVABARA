<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userMain.jsp</title>
<style>
.box {
	width:600px;
	height:600px;
	vertical-align: center;
	position:relative;
}
.ab {
	position:absolute;
	top:50%;
	transform: translateY(-50%);
	left:0;
}
</style>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<div class="d-flex">
	<div>
	<% if(session.getAttribute("idx") == null) {%>
		<form action="loginProc.usr" name="login">
			<h1>로그인</h1>
			<br>
			<table>
				<tr>
					<td><h4>아이디:</h4></td>
					<td><input type="text" placeholder="아이디를 입력해주세요" name="id"
						class="form-control" required></td>
				</tr>
				<tr>
					<td><h4>비밀번호:</h4></td>
					<td><input type="password" placeholder="비밀번호를 입력해주세요" name="pw"
						class="form-control" required></td>
				</tr>
				<tr>
					<td><input type="submit" value="로그인"
						class="btn btn-primary"></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
			<br>
			<br>
			<h4>아직 회원이 아니신가요?</h4>
			<a href="../user/join.usr" class="btn btn-outline-primary">회원가입</a>
		</form>
	</div>
<div>
	<img src="../user/south-korea-7961769_1280.png" width="700px" height="700px">
</div>
<%}else{ %>
<div class="d-flex">
	<img src="../user/logo.png" width="600px" height="600px">
	<div class="box">
		<div class="ab">
			<h1 class="fw-bold mb-5">지역 기반 커뮤니티<br>파란당근</h1>
			<h5>활발한 교류 활동을 지향하는<br>지역 기반 커뮤니티입니다!</h5>	
		</div>
	</div>
</div>
<%} %>
</div>
</body>
</html>
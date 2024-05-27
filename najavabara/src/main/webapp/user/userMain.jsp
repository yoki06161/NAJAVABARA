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
</head>
<body>
<%@ include file="../common/menu.jsp" %>
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
		<h4>아직 회원이 아니신가요?</h4>
		<a href="../user/join.usr" class="btn btn-outline-primary">회원가입</a>
	</form>
<%}else{ %>
	<img src="../user/logo.png" width="500px" height="500px">
<%} %>
</body>
</html>
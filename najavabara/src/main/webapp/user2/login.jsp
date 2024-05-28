<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
<link rel="stylesheet" href="../css/styles.css">
</head>
<body>
	<div id="form" class="login">
		<h1 class="logo">
			<a href="../index.jsp"><img alt="logo" src="../images/logo.png"></a>
		</h1>
		<div class="inner">
			<form action="loginproc.jsp" method="post">
				<input type="text" name="id" placeholder="ID" required="required"><br> 
				<input type="password" name="password" placeholder="Password" required="required"><br>
				<input type="submit" value="로그인">
			</form>
			<button type="button" onclick="location.href='join.jsp'">회원가입</button>
		</div>
	</div>
</body>
</html>
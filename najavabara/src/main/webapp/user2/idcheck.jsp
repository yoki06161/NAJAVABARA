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
	<div id="form" class="idcheck">
		<h1 class="logo">
			<a href="../index.jsp"><img alt="logo" src="../images/logo.png"></a>
		</h1>
		<div class="inner">
			<form action="idcheckproc.jsp" method="post">
				<input type="text" name="id" placeholder="ID" required="required"><br> 
				<input type="password" name="pw" placeholder="Password" required="required"><br>
				<input type="submit" name="확인" value="확인하기">
			</form>
			<p>본인 확인을 위해 아이디와 비밀번호를 입력해주세요.</p>
		</div>
	</div>
</body>
</html>
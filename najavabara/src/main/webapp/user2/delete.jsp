<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>delete.jsp</title>
</head>
<body>
	<h2>계정 삭제하기</h2>
	<form action="deleteproc.jsp" method="post">
		<p>아이디 : <input type="text" name="id"></p>
		<p>비밀번호 : <input type="password" name="pw"></p>
		<p><input type="submit" name="전송" value="계정 삭제"></p>
	</form>
		<br>
	<a href="select.jsp">회원 리스트 보기</a> &nbsp;&nbsp;&nbsp;
	<a href="update.jsp">회원 정보 수정으로 가기</a> &nbsp;&nbsp;&nbsp;
	<a href="join.jsp">회원 가입창으로 가기</a> &nbsp;&nbsp;&nbsp;
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script>
$(document).ready(function() {
	//헤더 메뉴 글자색바뀌는 것 제거
    localStorage.removeItem('activeLink');
});
</script>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>로그인</h1>
	<br>
	<form action="loginProc.usr" name="login">
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
</body>
</html>
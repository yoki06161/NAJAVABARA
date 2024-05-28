<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<div class="login">
<div class="background">
	<form method="post" action="loginAction.jsp">
		<!-- name = "외부로 내보낼 변수명". name을 안쓰면 서버로 변수를 못내보낸다. 여기서 name으로 이름지은 id가 request.getParameter("id")에서 id로 불린다. -->
		<input type="text" name = "id" class = "text_id" placeholder="아이디를 입력하세요" maxlength="20px"><br>
		<input type="password" name = "password" class = "text_pw" placeholder="비밀번호를 입력하세요" maxlength="20px">
		<input type="submit" class="s_btn" value="로그인">
	</form>
	<a href="join.jsp">회원가입</a>
</div>
</div>
</body>
</html>
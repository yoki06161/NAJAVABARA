<%@page import="proj.dao.HUserDAO"%>
<%@page import="proj.dto.HUserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
HUserDAO dao = new HUserDAO();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>join.jsp</title>
<link rel="stylesheet" href="../css/common.css">
<link rel="stylesheet" href="../css/styles.css">
</head>
<body>
	<div id="form" class="join">
		<h1 class="logo">
			<a href="../index.jsp"><img alt="logo" src="../images/logo.png"></a>
		</h1>
		<div class="inner">
			<h2>회원가입</h2>
			<form action="joinproc.jsp" method="post">
				<div class="input">
					<span>아이디 : </span>
					<input type="text" name="id" required="required"><br>
				</div>
				<div class="input">
					<span>비밀번호 : </span>
					<input type="password" name="password" required="required"><br>
				</div>
				<div class="input">
					<span>비밀번호 재입력 : </span>
					<input type="password" name="password_re" required="required"><br>
				</div>
				<div class="input">
					<span>이름 : </span>
					<input type="text" name="name" required="required"><br>
				</div>
				<div class="input">
					<span>거주지역 : </span>
					<select name="area" required="required">
						<option value="서울특별시">서울특별시</option>
						<option value="부산광역시">부산광역시</option>
						<option value="대구광역시">대구광역시</option>
						<option value="인천광역시">인천광역시</option>
						<option value="광주광역시">광주광역시</option>
						<option value="울산광역시">울산광역시</option>
						<option value="세종특별자치시">세종특별자치시</option>
						<option value="경기도">경기도</option>
						<option value="강원특별자치도">강원특별자치도</option>
						<option value="충청북도">충청북도</option>
						<option value="충청남도">충청남도</option>
						<option value="전북특별자치도">전북특별자치도</option>
						<option value="전라남도">전라남도</option>
						<option value="경상북도">경상북도</option>
						<option value="제주특별자치도">제주특별자치도</option>
					</select>
				</div>
				<input type="submit" value="회원가입" style="font-family: 'KCC-Hanbit'; font-size: 18px;">
				<%
				// em의 값이 널일시 아무것도 출력않게 함. 뒤로가거나, bo는 트루일시의 값
				if(session.getAttribute("em") == null) {%>
				<%}else{%>
				<label><%= session.getAttribute("em") %></label>
				<%session.removeAttribute("em"); %>
				<% }%>
			</form>
		</div>
	</div>
</body>
</html>
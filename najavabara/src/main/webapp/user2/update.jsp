<%@page import="proj.dto.HUserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
HUserDTO dto = (HUserDTO)request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>update.jsp</title>
</head>
<body>
 	<%@ include file="dbconn.jsp" %>
	<h2>내정보 수정</h2>
	<form action="updateproc.jsp" method="post">
		<p>아이디 : <%=session.getAttribute("id") %></p>
		<p>비밀번호 : <input type="password" name="pw" value="<%=dto.getPw()%>" required="required"></p>
		<p>이름 : <input type="text" name="name" value="<%=dto.getName() %>" required="required"></p>
		<p>거주지역 : 	<select name="area" required="required">
						<option value="<%=dto.getArea()%>"><%=dto.getArea()%></option>
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
		<p><input type="submit" name="전송" value="수정하기"></p>
		<br><br>
	</form>
	<a href="../index.jsp">메인화면으로</a>
</body>
</html>
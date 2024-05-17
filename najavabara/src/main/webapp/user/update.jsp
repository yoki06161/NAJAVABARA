<%@page import="dao.UserDAO"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<% 
//UserDTO dto = new UserDTO();
UserDTO dto = (UserDTO) request.getAttribute("user");

%>
<meta charset="UTF-8">
<title>회원정보 수정</title>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>회원정보 수정</h1>
	<br>
	<form action="updateProc.usr" method="post" name="update">
		<table>
			<tr>
				<td><h3>아이디:</h3></td>
				<td><%= session.getAttribute("id")%></td>
			</tr>
			<tr>
				<td><h3>비밀번호:</h3></td>
				<td><input type="password" name="pw" class="form-control"
					placeholder="새 비밀번호를 입력해주세요" value="<%= dto.getPw() %>" required></td>
			</tr>
			<tr>
				<td><h3>이름:</h3></td>
				<td><input type="text" name="name" class="form-control"
					placeholder="새 이름을 입력해주세요" value="<%= dto.getName() %>" required></td>
			</tr>
			<tr>
				<td><h3>지역:</h3></td>
				<td>
					<h4>
						<input class="form-control" type="text" name="area"
							placeholder="지역을 입력해주세요" value="<%= dto.getArea() %>" required>
					</h4>
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="회원정보 수정" class="btn btn-primary">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
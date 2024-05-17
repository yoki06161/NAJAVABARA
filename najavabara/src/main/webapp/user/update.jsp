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
					<select class="form-select" name="area">
					  <option selected value="<%= dto.getArea() %>"><%= dto.getArea() %></option>
					  <option value="서울특별시">서울특별시</option>
					  <option value="경기도">경기도</option>
					  <option value="강원도">강원도</option>
					  <option value="대전광역시">대전광역시</option>
					  <option value="대구광역시">대구광역시</option>
					  <option value="부산광역시">부산광역시</option>
					  <option value="인천광역시">인천광역시</option>
					  <option value="광주광역시">광주광역시</option>
					  <option value="울산광역시">울산광역시</option>
					  <option value="세종특별시">세종특별시</option>
					  <option value="충청북도">충청북도</option>
					  <option value="충청남도">충청남도</option>
					  <option value="전라북도">전라북도</option>
					  <option value="전라남도">전라남도</option>
					  <option value="경상북도">경상북도</option>
					  <option value="경상남도">경상남도</option>
					  <option value="제주특별시">제주특별시</option>
					</select>
						<!-- <input class="form-control" type="text" name="area"
							placeholder="지역을 입력해주세요" value="<%= dto.getArea() %>" required>
							-->
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
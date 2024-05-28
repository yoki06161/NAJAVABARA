<%@page import="proj.dao.UserDAO"%>
<%@page import="proj.dto.UserDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserDAO dao = new UserDAO();
	List<UserDTO> userList = dao.getUsers();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>select.jsp</title>
</head>
<body>
	<%@ include file="admin_menu.jsp" %>
	<a href="admin.jsp">admin 전용창으로</a>
	<a href="../index.jsp">메인화면으로</a>
	<table border="1">
		<tr>
			<th width="300" align="center">번호</th>
			<th width="300" align="center">아이디</th>
			<th width="300" align="center">비밀번호</th>
			<th width="300" align="center">이름</th>
			<th width="300" align="center">지역</th>
			<th width="300" align="center">역할</th>
			<th width="300" align="center">생성날짜</th>
		</tr>
		<%
				for(UserDTO dto : userList){
					int idx = dto.getIdx();
					String id = dto.getId();
					String pw = dto.getPw();
					String name = dto.getName();
					String area = dto.getArea();
					String role = dto.getRole();
					String regdate = dto.getRegdate();
		%>
		<tr>
			<td width="300" align="center"><%=idx %></td>
			<td width="300" align="center"><%=id %></td>
			<td width="300" align="center"><%=pw %></td>
			<td width="300" align="center"><%=name %></td>
			<td width="300" align="center"><%=area %></td>
			<td width="300" align="center"><%=role %></td>
			<td width="300" align="center"><%=regdate %></td>
		</tr>
		<%
			}
		%>
	</table>
</body>
</html>
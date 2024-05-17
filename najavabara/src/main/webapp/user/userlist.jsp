<%@page import="dto.UserDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>userlist</title>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>
		User list
		<h4>
		<table class="table">
			<tr>
				<th>IDX</th>
				<th>ID</th>
				<th>PW</th>
				<th>Name</th>
				<th>Region</th>
				<th>Regdate</th>
			</tr>
			<%
			List<UserDTO> userList = (List<UserDTO>)request.getAttribute("userList");
			%>
			<%
			for (UserDTO dto : userList) {
				int idx = dto.getIdx();
				String id = dto.getId();
				String pw = dto.getPw();
				String name = dto.getName();
				String region = dto.getRegion();
				String regdate = dto.getRegDate();
			%>
			<tr>
				<td><%=idx%>
				<td><%=id%></td>
				<td><%=pw%></td>
				<td><%=name%></td>
				<td><%=region%></td>
				<td><%=regdate%></td>
			</tr>
			<%
			}
			%>
		</table>
		</h4>
	</h1>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>

</head>
<h3>
<ul>
<li>
<a href="<%=request.getContextPath()%>">Index</a> |
<% if(session.getAttribute("idx") == null) {%>
<a href="../user/join.usr">Join</a> | 
<a href="../user/login.usr">Login</a> 
<%}else{ %>
<%=session.getAttribute("id") %>(<%=session.getAttribute("name") %>) |
<a href="../user/update.usr">Update(내정보수정)</a> | 
<a href="../user/delete.usr">Delete(회원탈퇴)</a> |
<a href="../user/logout.usr">LogOut</a> 
<%} %>
</li>
<li>
<a href="/myproject/user/userlist.usr">User List</a>
</li>
<li>
<a href="/myproject/region/list.reg">Board List</a> 
</li>
</ul>
</h3>
<hr>
<br>
</html>
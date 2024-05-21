<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<h2>게시물 작성</h2>
<%
//세션에서 filePath 속성을 가져와서 변수에 저장
String filePath = (String) session.getAttribute("filePath");
RegionDTO fdto = (RegionDTO)request.getAttribute("fdto");
%>
	<form name="writeForm" method="post" action="<%=request.getContextPath()%>/region/writeProc.reg" enctype="multipart/form-data">
	<img>
	<table class="table table-bordered">
		<tr><td>제목</td><td><input type="text" name="title" class="form-control" required></td></tr>
		<tr><td>내용</td>
		<td><textarea name="content" style="height:100px"class="form-control" required></textarea></td></tr>
		<tr><td>이미지 첨부</td>
		<td><input type="file" name="file" class="form-control"></td></tr>
	</table>
	<input type="submit" value="게시물 작성" class="btn btn-primary">
</form>

</body>
</html>
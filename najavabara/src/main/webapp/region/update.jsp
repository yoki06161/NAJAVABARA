<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% // param
RegionDTO dto = (RegionDTO)request.getAttribute("reg");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= dto.getTitle()%></title>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<h2>게시물 수정</h2>
<form name="updateForm" method="post" action="updateProc.reg">
<input type="hidden" name="num" value="<%=dto.getNum()%>">
<table class="table table-bordered">
	<tr>
	<td>번호</td><td><%=dto.getNum() %></td>
	<td>아이디(이름)</td><td><%=dto.getId()%>(<%=dto.getName() %>)</td>	
	</tr>
	<tr>
	<td>작성일자</td><td><%=dto.getPostdate() %></td>
	<td>조회</td><td><%=dto.getVisitcount() %></td>	
	</tr>
	<tr><td>제목</td><td colspan="3"><input type="text" name="title" class="form-control" value="<%=dto.getTitle() %>"></td></tr>
	<tr><td>내용</td><td colspan="3"><input type="text" name="content" class="form-control" value="<%=dto.getContent() %>"></td></tr>	
</table>
<input type="submit" value="게시물 수정 완료" class="btn btn-primary"> 
<a href="javascript:del('<%=dto.getNum()%>');" class="btn btn-outline-primary">게시물 수정 취소</a>
</form>
</body>
</html>
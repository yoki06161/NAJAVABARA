<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% // param
RegionDTO dto = (RegionDTO)request.getAttribute("dto");
String saveFolder="Uploads";
String requestFolder=request.getContextPath()+"/"+saveFolder;
String fname = dto.getSfile();
String fullpath = requestFolder+"/"+fname;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= dto.getTitle()%></title>
<script>
	function del(num){
		const input = confirm("정말 삭제 할까요?");
		if(input){
			location.href = "<%=request.getContextPath()%>/region/deleteProc.reg?num="
					+ num;
		} else {
			alert('삭제를 취소 했습니다.');
			return;
		}

	}
</script>
<style>
img {
	width: 50%;
	height: 50%;
}
</style>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>게시물 상세보기</h1>
	<br>
	<table class="table table-borderless">
		<tr>
			<!-- <td>번호</td>
			<td>dto.getNum()</td> -->
			<td><h5><%=dto.getId()%>(<%=dto.getName() %>)</h5>
			<p style="color:gray;"><%= dto.getArea() %>  |  <%=dto.getPostdate() %></p></td>
		</tr>
		<tr>
			<td><h3><%=dto.getTitle() %></h3></td>
		</tr>
		<tr>
			<td><h5><%=dto.getContent() %></h5></td>
		</tr>
		<tr>
				<% if(dto.getOfile() == null) { %>
					<td style="color:gray;">등록한 이미지가 없습니다.</td>
				<% } else { %>
					<td><img src="<%= fullpath %>"></td>
				<%} %>
		</tr>
		<tr>
			<td style="color:gray;">조회 <%=dto.getVisitcount() %></td>
		</tr>
	</table>
	<a href="list.reg" class="btn btn-outline-primary">게시물 목록</a>
	<%if(session.getAttribute("id") != null && session.getAttribute("id").equals(dto.getId())) {%>
	<a href="update.reg?num=<%=dto.getNum()%>"
		class="btn btn-outline-primary">게시물 수정</a>
	<a href="javascript:del('<%=dto.getNum()%>');"
		class="btn btn-outline-primary">게시물 삭제</a>
	<%} %>

</body>
</html>
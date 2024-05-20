<%@page import="dto.RegionDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>지역 게시판</title>
</head>
<body>
<style>
img {
	width: 300px;
	height: 300px;
	object-fit: cover;
}
	
}
</style>
	<%
	List<RegionDTO> regionList = (List<RegionDTO>) request.getAttribute("regionList");
	int totalCount = (int) request.getAttribute("totalCount");
	%>
	<%@ include file="../common/menu.jsp"%>
	<h1>지역 게시판</h1>
	<br>
	<form method="get">
	<table>
		<tr>
			<td>
				<select name="searchField" class="form-select">
					<option value="title">제목</option>
					<option value="content">내용</option>
				</select>
			</td>
			<td>
				<input type="text" name="searchWord"  class="form-control"> 
			</td>
			<td>
				<input type="submit" value="검색" class="btn btn-primary">
			</td>
		</tr>
	</table>
	</form>
	
	<br>
	<h3>전체 : <%=totalCount%></h3>
	<table class="table table-bordered">
		<tr class="table-primary">
			<th>번호</th>
			<th>제목</th>
			<th>첨부 이미지</th>
			<th>아이디</th>
			<th>지역</th>
			<th>작성일</th>
			<th>조회수</th>
		</tr>
		<%
		if (regionList.isEmpty()) {
		%>
		<tr>
			<td colspan="5">등록된 게시물이 없습니다.</td>
		</tr>
		<%
		} else {
		%>
		<%
		for (RegionDTO dtos : regionList) {
			int num = dtos.getNum();
			String title = dtos.getTitle();
			String area = dtos.getArea();
			String id = dtos.getId();
			String postdate = dtos.getPostdate();
			int visitcount = dtos.getVisitcount();
			// param
			String saveFolder="Uploads";
			String requestFolder=request.getContextPath()+"/"+saveFolder;
			String fname = dtos.getSfile();
			String fullpath = requestFolder+"/"+fname;
		%>
		<tr>
			<td><%=num%></td>
			<!-- style="color:black" -->
			<td><a href="view.reg?num=<%=num%>" ><%=title%></a>
			<%if(dtos.getSfile() == null) { %>
				<td><img src="../region/europe-3483539_1280.jpg"></td>
			<%} else {%>
				<td><img src="<%= fullpath %>"></td>
			<%} %>
			</td>
			<td><%=id%></td>
			<td><%= area %></td>
			<td><%=postdate%></td>
			<td><%=visitcount%></td>
		</tr>
		<%
		}
		%>
		<%
		}
		%>
	</table>
	<a class="btn btn-outline-primary" href="write.reg">글쓰기</a>
</body>
</html>
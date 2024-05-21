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
	width: 400px !important;
	height: 400px !important;
	object-fit: cover;
}

a {
	text-decoration: none !important;
}

a:hover {
	text-decoration: underline !important;
}

.card {
	width: 400px !important;
}

.card-title {
	text-overflow: ellipsis !important;
	white-space: nowrap !important;
	overflow: hidden !important;
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
				<td><select name="searchField" class="form-select">
						<option value="title">제목</option>
						<option value="content">내용</option>
				</select></td>
				<td><input type="text" name="searchWord" class="form-control">
				</td>
				<td><input type="submit" value="검색" class="btn btn-primary">
				</td>
			</tr>
		</table>
	</form>

	<br>
	<h3>
		전체 :
		<%=totalCount%></h3>
	번호 제목 첨부 이미지 아이디 지역 작성일 조회수
	<%
		if (regionList.isEmpty()) {
		%>
	<h5>등록된 게시물이 없습니다.</h5>
	<%
		} else {
		%>
	<div class="row row-cols-1 row-cols-md-3 g-4">
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
		<div class="col">
			<div class="card">
					<%if(dtos.getSfile() == null) { %>
					<img src="../region/europe-3483539_1280.jpg" class="card-img-top"
						alt="사진">
					<%} else {%>
					<img src="<%= fullpath %>" class="card-img-top" alt="사진">
					<%} %>
				<div class="card-body">
					<input type="hidden" value="<%=num%>">

					<h4>
						<a href="view.reg?num=<%=num%>" class="card-title"><%=title%></a>
					</h4>
					<h6 class="card-subtitle"><%= area %>/<%=id%></h6>
					<h6 class="card-text text-body-secondary"><%=postdate%></h6>
					<p>조회수 <%=visitcount%></p>
				</div>
			</div>
		</div>

		<%
			}
			%>
	</div>
	<%
		}
		%>
	<div style="display: block;">
		<a class="btn btn-outline-primary" href="write.reg">글쓰기</a>
	</div>
</body>
</html>
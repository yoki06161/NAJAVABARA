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
	width: 100% !important;
	height: 382px !important;
	object-fit: cover;
}

a {
	text-decoration: none !important;
}

a:hover {
	text-decoration: underline !important;
}

.card {
	max-width: 100% !important;
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
						<option value="user.id">글쓴이</option>
				</select></td>
				<td><input type="text" name="searchWord" class="form-control">
				</td>
				<td><input type="submit" value="검색" class="btn btn-primary">
				</td>
				<td></td>
			</tr>
		</table>
	</form>
	<br>
	<form method="get">
		<table>
			<tr>
				<td>
					<select class="form-select" name="area">
					  <option selected value="">전체 지역</option>
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
				</td>
				<td>
					<input type="submit" value="지역별 검색" class="btn btn-primary">
				</td>
			</tr>
		</table>
	</form>

	<br><br>
		<div class="d-flex justify-content-between">		
			<h3 class="text-start">전체 : <%=totalCount%></h3>
			<input type="button" class="btn btn-outline-primary" onclick="location.href='write.reg'" value="글쓰기">
		</div>
	<br>
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
				int likes = dtos.getLikes();
				// param
				String saveFolder="Uploads";
				String requestFolder=request.getContextPath()+"/"+saveFolder;
				String fname = dtos.getSfile();
				String fullpath = requestFolder+"/"+fname;
			%>
		<div class="col">
			<div class="card">
					<%if(dtos.getSfile() == null) { %>
					<img src="../region/no_image.PNG" class="card-img-top"
						alt="사진">
					<%} else {%>
					<img src="<%= fullpath %>" class="card-img-top" alt="사진">
					<%} %>
				<div class="card-body">
					<input type="hidden" value="<%=num%>">

					<h4 class="card-title fw-bold">
						<a href="view.reg?num=<%=num%>" style="color:black;"><%=title%></a>
					</h4>
					<h5><%= area %>/<%=id%></h5><h6 style="color:gray;"><%=postdate%> &nbsp;|&nbsp; 조회수 <%=visitcount%> &nbsp;|&nbsp; 좋아요 <%=likes %></h6>
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
</body>
</html>
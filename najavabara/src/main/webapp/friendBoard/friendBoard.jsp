<%@page import="mvc.dto.friendBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate"%>
<%@ page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%
List<friendBoardDTO> postLists = (List<friendBoardDTO>) request.getAttribute("postLists");
Integer totalCount = (Integer) request.getAttribute("totalCount");
int pageNum = (Integer) request.getAttribute("pageNum"); // 현재 페이지 번호 가져오기
int pageSize = 10; // 페이지당 표시할 글의 수
int totalPage = (int) Math.ceil((double) totalCount / pageSize); // 전체 페이지 수 계산
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>동네 친구 게시판</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<style>
.pagination {
	justify-content: center;
}

.card {
	margin-bottom: 20px;
}
</style>
</head>
<body>
	<%@ include file="../index.jsp"%>
	<div class="container mt-5">
		<h1 class="mb-4">동네 친구 게시판</h1>
		<form method="get">
			<div class="form-row mb-3">
				<div class="col-auto">
					<select class="form-control" name="searchField">
						<option value="title">제목</option>
						<option value="content">내용</option>
					</select>
				</div>
				<div class="col">
					<input type="text" class="form-control" name="searchWord">
				</div>
				<div class="col-auto">
					<button type="submit" class="btn btn-primary">검색</button>
				</div>
			</div>
		</form>
		<div class="row">
			<%
			if (postLists == null || postLists.isEmpty()) {
			%>
			<div class="col-12">
				<p>데이터가 없습니다!</p>
			</div>
			<%
			} else {
			for (int i = 0; i < postLists.size(); i++) {
				friendBoardDTO post = postLists.get(i);
				if (i % 2 == 0 && i != 0) {
			%>
		</div>
		<div class="row">
			<%
			}
			%>
			<div class="col-md-6">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">
							<a href="viewPost.po?num=<%=post.getNum()%>"
								style="color: black;"> <%=post.getTitle()%>
							</a>
							<%
							if (post.getFileName() != null && !post.getFileName().isEmpty()) {
							%>
							<i class="far fa-file"></i>
							<%
							}
							if (post.getCommentCount() > 0) {
							%>
							<a href="viewPost.po?num=<%=post.getNum()%>" style="color: red;">
								[<%=post.getCommentCount()%>]
							</a>
							<%
							}
							%>
						</h5>
						<p class="card-text">
							작성자:
							<%=post.getId()%>
						</p>
						<p class="card-text">
							<%= post.getArea() %>
							·
							<%
							// 게시물의 작성일을 LocalDateTime 객체로 가져옴
							LocalDateTime postDateTime = post.getPostdate().toLocalDateTime(); // Timestamp를 LocalDateTime으로 변환
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							%>
							<%
							if (postDateTime.toLocalDate().isEqual(LocalDate.now())) {
							%>
							<%=postDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))%>
							<%
							} else {
							%>
							<%=postDateTime.format(formatter)%>
							<%
							}
							%>
							·
							조회
							<%=post.getVisitcount()%>
						</p>
					</div>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>
		<div class="text-center">
			<ul class="pagination">
				<li class="page-item <%=pageNum == 1 ? "disabled" : ""%>"><a
					class="page-link" href="friendBoard.po?pageNum=<%=pageNum - 1%>">이전</a>
				</li>
				<%
				for (int i = 1; i <= totalPage; i++) {
				%>
				<li class="page-item <%=pageNum == i ? "active" : ""%>"><a
					class="page-link" href="friendBoard.po?pageNum=<%=i%>"><%=i%></a></li>
				<%
				}
				%>
				<li class="page-item <%=pageNum == totalPage ? "disabled" : ""%>">
					<a class="page-link" href="friendBoard.po?pageNum=<%=pageNum + 1%>">다음</a>
				</li>
			</ul>
		</div>
		<div class="text-right">
			<a href="writeForm.po" class="btn btn-primary">글 작성</a>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
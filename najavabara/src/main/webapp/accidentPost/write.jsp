<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</head>
<body>
	<%@ include file="../common/menu.jsp"%>

	<div class="container mt-5">
		<h1 class="mb-4">글쓰기</h1>
		<form action="submitPost.acc" method="post" class="needs-validation"
			novalidate>
			<div class="form-group">
				<label for="title">Title:</label> <input type="text" id="title"
					name="title" class="form-control" required>
				<div class="invalid-feedback">Please provide a title.</div>
			</div>
			<div class="form-group">
				<label for="content">Content:</label>
				<textarea id="content" name="content" class="form-control" rows="5"
					required></textarea>
				<div class="invalid-feedback">Content is required.</div>
			</div>
			<!-- 글쓴이 부분 displat:none으로 가림  --> 
			<div class="form-group" style="display: none;">
				<label for="id">Writer:</label> <input type="text" id="id" name="id"
					value="<%=session.getAttribute("id")%>" class="form-control"
					readonly required>
				<div class="invalid-feedback">Writer is required.</div>
			</div>
			<button type="submit" class="btn btn-primary">등록</button>
		</form>
		<div class="mt-4">
			<a href="../index.jsp" class="btn btn-secondary">Home</a> <a
				href="list.acc" class="btn btn-info">게시글</a>
		</div>
	</div>
</body>
</html>
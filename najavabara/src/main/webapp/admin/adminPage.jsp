<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<style>
.sidebar {
	position: fixed;
	top: 0;
	left: 0;
	height: 100%;
	width: 250px;
	background-color: #f8f9fa;
	padding-top: 50px; /* 네비게이션 바 높이만큼 여백 */
}

.sidebar ul {
	list-style: none;
	padding-left: 0;
}

.sidebar li {
	padding: 8px 15px;
}

.sidebar a {
	color: #343a40;
	text-decoration: none;
	display: block;
}

.sidebar a:hover {
	background-color: #e9ecef;
}
</style>
</head>
<body>
	<div class="sidebar">
		<ul class="nav flex-column">
			<li class="nav-item"><a class="nav-link" href="index.do">홈</a></li>
			<li class="nav-item"><a class="nav-link active"
				href="UserList.ad">회원 목록</a></li>
		</ul>
	</div>

</body>
</html>
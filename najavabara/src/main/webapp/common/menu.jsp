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
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script>
	//메뉴 클릭 시 이벤트
	$(document).ready(function() {
		// 페이지 로드 시 모든 링크에 기본 클래스 추가
	    $(".click").removeClass("link-primary").addClass("link-dark");
	 	// 로그인 페이지 로드 시 localStorage에서 activeLink 제거
	 	localStorage.removeItem('activeLink');
			
		// 페이지 로드 시 localStorage에서 활성 링크를 가져와 클래스 적용
		var activeLink = localStorage.getItem('activeLink');
		if (activeLink) {
			$('a[href="' + activeLink + '"]').removeClass("link-dark")
				.addClass("link-primary");
		}

		// 클릭 이벤트 핸들러
		$(".click").click(function() {
			// 기존 활성 링크 클래스 제거
			$(".click").removeClass("link-primary").addClass("link-dark");

			// 현재 클릭된 링크에 클래스 추가
			$(this).removeClass("link-dark").addClass("link-primary");

			// 현재 클릭된 링크의 href를 localStorage에 저장
			var href = $(this).attr('href');
			localStorage.setItem('activeLink', href);
		});
	});
</script>
<style>
body {
	max-width: 1200px;
	margin: 0 auto;
	padding: 30px 0;
}
ul {
	padding-left: 0;
}
li {
	list-style: none;
	padding-right: 10px !important;
}
.menu {
	padding-right: 20px !important;
}
.logo_small {
	width:50px !important;
	height:50px !important;
}
</style>
</head>
<body>
<div class="d-flex justify-content-between ">
<div class="d-flex align-items-center">
    <li class="menu">
        <h5>
            <a href="../user/userMain.usr">
				<img src="../user/logo.png" class="logo_small" alt="파란당근">
			</a>
        </h5>
    </li>
    <li class="menu">
        <h5>
            <a href="/najavabara/region/list.reg"
                class="fw-bold click link-dark link-underline-opacity-0 link-opacity-50-hover">지역별</a>
        </h5>
    </li>
    <li class="menu">
        <h5>
            <a href="/najavabara/accidentPost/list.acc"
                class="fw-bold click link-dark link-underline-opacity-0 link-opacity-50-hover">사건사고</a>
        </h5>
    </li>
    <li class="menu">
        <h5>
            <a href="${pageContext.request.contextPath}/friendBoard/friendBoard.fri"
                class="fw-bold click link-dark link-underline-opacity-0 link-opacity-50-hover">동네친구</a>
        </h5>
    </li>
    <li class="menu">
        <h5>
            <a href="/najavabara/hobbyboard/list.hob"
                class="fw-bold click link-dark link-underline-opacity-0 link-opacity-50-hover">취미</a>
        </h5>
    </li>
    <li class="menu">
        <h5>
            <a href="/najavabara/Review_board/review_list.jsp"
                class="fw-bold click link-dark link-underline-opacity-0 link-opacity-50-hover">장소리뷰</a>
        </h5>
    </li>
    <li class="menu">
        <h5>
            <a href="/najavabara/free/list.free"
                class="fw-bold click link-dark link-underline-opacity-0 link-opacity-50-hover">자유게시판</a>
        </h5>
    </li>
</div>


<div class="d-flex align-items-end">
<%
if (session.getAttribute("idx") == null) {
%>
<ul class="d-flex">
    <li><a href="../user/login.usr"
        class="link-dark link-underline-opacity-0 link-opacity-50-hover">로그인</a>
    </li>
</ul>

<%
} else {
%>
<ul class="d-flex">
    <li><%=session.getAttribute("id")%>(<%=session.getAttribute("name")%>)님
        환영합니다! | </li>
    <li><a href="../user/update.usr"
        class="link-dark link-underline-opacity-0 link-opacity-50-hover">내
            정보 수정</a> | </li>
    <li><a href="../user/logout.usr"
        class="link-dark link-underline-opacity-0 link-opacity-50-hover">로그아웃</a>
    </li>
</ul>
<% } %>
</div>
</div>
<hr>
<br>
</body>
</html>
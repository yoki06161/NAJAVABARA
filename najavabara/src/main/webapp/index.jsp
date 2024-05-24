<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/index.do">메인페이지</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarSupportedContent">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/friendBoard/friendBoard.fri">동네 친구 게시판</a>
            <ul class="navbar-nav">
                <%-- 로그인 상태에 따라 메뉴 변경 --%>
                <% if (session.getAttribute("user") == null) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/loginForm.do">로그인</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/registerForm.do">회원가입</a>
                    </li>
                <% } else { %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/logout.do">로그아웃</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/myPage.do">내 정보</a>
                    </li>
                    <%-- 관리자인 경우에만 보이는 관리자 페이지로 이동하는 버튼 --%>
                    <% if (((mvc.dto.UserDTO) session.getAttribute("user")).getRole().equals("관리자")) { %>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/adminPage.ad">관리자 페이지</a>
                        </li>
                    <% } %>
                <% } %>
            </ul>
        </div>
    </nav>
</body>
</html>
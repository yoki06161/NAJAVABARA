<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="mvc.dto.UserDTO"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 정보</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../index.jsp"%>
    <div class="container mt-5">
        <h1 class="mb-4">내 정보</h1>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">사용자 정보</h5>
                <p class="card-text">
                    <!-- 사용자 정보를 출력하는 부분 -->
                    <% 
                        // 사용자 정보를 가져오는 코드 예시 (UserDTO를 사용한다고 가정)
                        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
                        if (user != null) {
                    %>
                    <strong>이름:</strong> <%= user.getName() %><br>
                    <strong>아이디:</strong> <%= user.getId() %><br>                    
                    <strong>비밀번호 </strong> <%= user.getPw() %> <br>
                    <% } else { %>
                    사용자 정보를 찾을 수 없습니다.
                    <% } %>
                </p>
                <a href="editUserInfo.do" class="btn btn-primary mr-2">정보 수정</a>
                <a href="myPost.do" class="btn btn-secondary">내가 쓴 게시글 보기</a>
                <a href="myComments.do" class="btn btn-secondary">내가 쓴 댓글 보기</a>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
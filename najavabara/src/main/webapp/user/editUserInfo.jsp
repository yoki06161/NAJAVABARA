<%@ page import="mvc.dto.UserDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>사용자 정보 수정</title>
</head>
<body>
    <%@ include file="../index.jsp" %>
    <div class="container mt-5">
        <h1 class="mb-4">사용자 정보 수정</h1>
        <div class="card">
            <div class="card-body">
                <form action="updateUserInfo.do" method="post">
                    <div class="form-group">
                        <label for="name">이름</label>
                        <input type="text" class="form-control" id="name" name="name" value="<%= ((UserDTO) request.getAttribute("user")).getName() %>" required>
                    </div>
                    <div class="form-group">
                        <label for="currentPassword">현재 비밀번호</label>
                        <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">새로운 비밀번호</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                    </div>
                    <button type="submit" class="btn btn-primary">저장</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
</html>
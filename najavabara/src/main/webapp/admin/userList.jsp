<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<%@ include file="./adminPage.jsp"%>
    <div class="container mt-5">
        <h1 class="mb-4">회원 목록</h1>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">아이디</th>
                    <th scope="col">이름</th>
                    <th scope="col">역할</th>
                    <th scope="col">가입일</th>
                </tr>
            </thead>
            <tbody>
                <%@ page import="mvc.dto.UserDTO" %>
                <%@ page import="java.util.List" %>
                <% List<UserDTO> userList = (List<UserDTO>) request.getAttribute("userList"); %>
                <% if(userList != null && !userList.isEmpty()) { %>
                    <% for(UserDTO user : userList) { %>
                        <tr>
                            <td><%= user.getIdx() %></td>
                            <td><%= user.getId() %></td>
                            <td><%= user.getName() %></td>
                            <td><%= user.getRole() %></td>
                            <td><%= user.getRegdate() %></td>
                        </tr>
                    <% } %>
                <% } else { %>
                    <tr>
                        <td colspan="5">회원이 없습니다.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
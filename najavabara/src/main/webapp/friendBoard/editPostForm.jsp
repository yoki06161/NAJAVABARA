<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정</title>
<link rel="stylesheet"
    href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">게시물 수정</h2>

        <%
        // 게시물 정보가 request 속성에 존재하는지 확인
        if (request.getAttribute("post") != null) {
            // 게시물 정보를 가져옴
            mvc.dto.friendBoardDTO post = (mvc.dto.friendBoardDTO) request.getAttribute("post");
        %>
        <form action="updatePost.fri" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">제목</label>
                <!-- 제목 필드에 기존 제목 값을 설정 -->
                <input type="text" class="form-control" id="title" name="title" value="<%=post.getTitle()%>" required>
            </div>
            <div class="form-group">
                <label for="content">내용</label>
                <!-- 내용 필드에 기존 내용 값을 설정 -->
                <textarea class="form-control" id="content" name="content" rows="5" required><%=post.getContent()%></textarea>
            </div>
            <div class="form-group">
                <label for="file">파일 첨부</label>
                <!-- 파일 첨부 필드 -->
                <input type="file" class="form-control-file" id="file" name="file">
                <%
                // 기존 파일이 있는 경우 파일명을 표시
                List<String> fileNames = post.getFileNames();
                if (fileNames != null && !fileNames.isEmpty()) {
                    // 여러 파일명이 있는 경우 모두 표시
                    for (String fileName : fileNames) {
                %>
                <p>현재 파일: <a href="<%=request.getContextPath()%>/friendBoard/uploads/<%=fileName%>" target="_blank"><%=fileName%></a></p>
                <% } %>
                <!-- 기존 파일명을 전송하기 위한 hidden 필드 -->
                <input type="hidden" name="currentFile" value="<%=fileNames.get(0)%>">
                <% } %>
            </div>
            <!-- 게시물 번호를 전송하기 위한 hidden 필드 -->
            <input type="hidden" name="num" value="<%=post.getNum()%>">
            <button type="submit" class="btn btn-primary">수정 완료</button>
            <a href="viewPost.fri?num=<%=post.getNum()%>" class="btn btn-secondary ml-2">취소</a>
        </form>
        <% } else { %>
        <p>게시물을 찾을 수 없습니다.</p>
        <% } %>
    </div>
</body>
</html>
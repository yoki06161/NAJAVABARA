<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글 작성</title>
</head>
<body>
    <%@ include file="../common/menu.jsp"%>
    <div class="container mt-5">
        <h1>글 작성</h1>
        <form action="write.fri" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="title" class="form-label">제목</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>
            <div class="mb-3">
                <label for="content" class="form-label">내용</label>
                <textarea class="form-control" id="content" name="content" rows="5" required></textarea>
            </div>
            <div class="mb-3">
                <label for="file" class="form-label">파일 첨부</label>
                <input type="file" class="form-control" id="file" name="file" multiple>
            </div>
            <button type="submit" class="btn btn-primary">작성 완료</button>
        </form>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
</body>
</html>

<%@ page import="dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<style>
a {
	text-decoration: none !important;
}
.center {
	text-align: center;
}
table{
	width:100% !important;
}
</style>
<body>
	<%@ include file="../common/menu.jsp"%>
	<form name="writeForm" method="post"
        action="<%=request.getContextPath()%>/free/writeProc.free">
    <div class="d-flex flex-column justify-content-center g-5">
        <h1 class="text-center mb-5">게시글 작성</h1>
            <table class="table table-bordered mb-5">
                <tr>
                    <td class="align-middle fs-4 text-center">제목</td>
                    <td><input type="text" name="title" class="form-control"></td>
                </tr>
                <tr>
                    <td class="align-middle fs-4 text-center">내용</td>
                    <td><textarea name="content" class="form-control" rows="9"></textarea></td>
                </tr>
            </table>
            <div class="d-flex justify-content-center">
                <input type="Submit" value="Submit" onclick="validateForm();" class="btn btn-primary">
            </div>
        </div>
    </form>
</body>
</html>
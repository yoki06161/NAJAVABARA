<%@ page import="dto.BoardDTO"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>
<%
//param
BoardDTO dto = (BoardDTO) request.getAttribute("dto");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>free update</title>
<script>
type="text/javascript">
	function validateForm() {
		const form = document.writeForm;
		console.dir(form); //input
		if (form.title.value === "") {
			alert('title 필수값입니다');
			form.title.focus();
			return;
		}
		if (form.content.value === "") {
			alert('content 필수값입니다');
			form.content.focus();
			return;
		}

		form.submit();
	}
</script>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h2>게시글 수정하기</h2>
	<form name="updateForm" method="post" 
	onsubmit="validateFrom(event);" action="updateProc.free">
		<input type="hidden" name="num" value="<%=dto.getNum()%>">
		<table border="1" width="90%">
			<tr>
				<td>제목</td>
				<td><input type="text" name="title" class="form-control"
					value="<%=dto.getTitle()%>"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><input name="content" value="<%=dto.getContent()%>"></input>
				</td>
			<tr>
				<<td>이미지 첨부</td>
				<td><input type="file" name="file"
					class="form-control">	
				<br>
				<%
				if (dto.getOfile() == null) {
				%>
				<h6 style="color: gray;">등록한 이미지가 없습니다.</h6>
				<%
				} else {
				%>
				<img src="">
				<%
				}
				%>
				</td>
			</tr>

		</table>
		<input type="submit" value="게시물 수정 완료" class="btn btn-primary">
		<a href="javascript:edit('<%=dto.getNum()%>');"
			class="btn btn-outline-primary">게시물 수정 취소</a>
	</form>

</body>
</html>
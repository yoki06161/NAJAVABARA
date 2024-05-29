<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board write</title>
<script type="text/javascript">
function validateForm() {
	const form = document.writeForm;
	console.dir(form); // input
	if(form.title.value === "") {
		alert('title 필수값 입니다');
		form.title.focus();
		return;
	}
	if(form.content.value === "") {
		alert('content 필수값 입니다');
		form.content.focus();
		return;
	}
	
	form.submit();
}
</script>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<h2>글쓰기</h2>
<form name="writeForm" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/writeFileProc.free">
	<table border="1" width="90%">
		<tr><td>제목</td><td><input type="text" name="title" style="width:90%"></td></tr>
		<tr><td>내용</td>
		<td><textarea name="content" style="width:90%; height:100px"></textarea></td></tr>
	</table>
	<input type="Submit" value="Submit" onclick="validateForm();">
</form>

</body>
</html>
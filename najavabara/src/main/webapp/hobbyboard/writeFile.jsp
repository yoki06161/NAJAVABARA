<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/write.jsp</title>
<script type="text/javascript">
function validateFileForm(event) {
	const form = document.writeFileForm;
	console.dir(form); // input

	if (form.input_title.value === "") {
		alert('title 필수값 입니다.');
		form.input_title.focus();
		event.preventDefault(); // 폼 제출 방지
		return false;
	}
	if (form.input_content.value === "") {
		alert('content 필수값 입니다.');
		form.input_content.focus();
		event.preventDefault(); // 폼 제출 방지
		return false;
	}
	const fileInput = form.input_file.value;
	if (fileInput.value === "") {
		alert('file 필수값 입니다.');
		form.input_file.focus();
		event.preventDefault(); // 폼 제출 방지
		return false;
	}
    // 파일 확장자 검사
    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
	if (!allowedExtensions.test(fileInput)) {
		alert('.jpg .png .gif .jpeg 확장자명만 업로드 가능합니다.');
		form.input_file.focus();
		event.preventDefault(); // 폼 제출 방지
		return;
	}
	return true; // 폼 제출 허용
}
</script>

<style>
   	#wrap {
    		max-width: 1200px;
    		margin: 0 auto;
    	}
	form {
		border: 1px solid #cfcfcf;
		padding: 10px 0 20px;
	}
	td {
		padding: 0 10px;
		height: 40px;
		width: 120px;
	}
	textarea{
		width: 1000px;
		min-height: 400px;
		font-size: 15px;
		resize: none;
	}
	input[type=text]{
		height: 25px;
		line-height: 25px;
		font-size: 16px;
		width: 700px;
	}
	input[type=file]{
		height: 25px;
	}
	input[type=submit]{
		margin-left: 154px;
		height: 25px;
		width: 90px;
	}
</style>
</head>
<body>
<div id="wrap">
	<%@ include file="../common/menu.jsp" %>
	<h2>글 작성하기</h2>
	<%String id = (String)session.getAttribute("id"); %>
	<form name="writeFileForm" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/writeFileProc.hob">
		<table>
			<tr><td align="right">ID</td><td><%=id %></td></tr>
			<tr><td align="right">Title</td><td><input type="text" name="input_title" placeholder="제목을 입력해주세요."></td></tr>
			<tr><td align="right">Hobby</td><td>
				<select name="input_hobby" required="required" style="width:10%; height:30px">
					<option value="gardening">원예</option>
					<option value="art">아트</option>
					<option value="puzzle">퍼즐</option>
					<option value="collection">수집</option>
					<option value="reading">독서</option>
					<option value="exercise">운동</option>
					<option value="photo">포토</option>
					<option value="handmade">수공예</option>
					<option value="instrument">악기 연주</option>
					<option value="astronomical">천체 관측</option>
				</select></td></tr>
			<tr><td align="right">Content</td>
			<td><textarea name="input_content" placeholder="내용을 입력해주세요."></textarea></td></tr>
			<tr><td></td><td><input type="file" name="input_file"></td></tr>
		</table>
		<input type="submit" value="작성하기" onclick="validateFileForm(event);">
	</form>
</div>

</body>
</html>
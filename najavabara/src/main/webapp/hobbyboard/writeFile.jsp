<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 작성하기</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
    const fileInput = form.input_file; // 수정: 파일 입력 필드 자체를 가져옴
    if (fileInput.value === "") { // 수정: 파일 입력 필드의 값 검사
        alert('file 필수값 입니다.');
        fileInput.focus();
        event.preventDefault(); // 폼 제출 방지
        return false;
    }
    // 파일 확장자 검사
    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
    if (!allowedExtensions.test(fileInput.value)) { // 수정: 파일 입력 필드의 값을 검사
        alert('.jpg .png .gif .jpeg 확장자명만 업로드 가능합니다.');
        fileInput.focus();
        event.preventDefault(); // 폼 제출 방지
        return false;
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
		padding: 20px;
	}
	.form-group {
		margin-bottom: 1rem;
	}
	.form-control {
		height: calc(2.25rem + 2px);
		font-size: 1rem;
		line-height: 1.5;
		padding: .375rem .75rem;
		border: 1px solid #ced4da;
		border-radius: .25rem;
	}
	.textarea-control {
		min-height: 200px;
	}
	.file-control {
		margin-top: .5rem;
	}
	.submit-btn {
		margin-top: 1rem;
	}
</style>
</head>
<body>
<div id="wrap">
	<%@ include file="../common/menu.jsp" %>
	<h2>글 작성하기</h2>
	<%String id = (String)session.getAttribute("id"); %>
	<form name="writeFileForm" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/writeFileProc.hob">
		<div class="form-group">
			<label for="input_title">Title</label>
			<input type="text" id="input_title" name="input_title" class="form-control" placeholder="제목을 입력해주세요.">
		</div>
		<div class="form-group">
			<label for="input_hobby">Hobby</label>
			<select name="input_hobby" id="input_hobby" class="form-control" required>
				<option value="gardening">원예</option>
				<option value="art">아트</option>
				<option value="cook">요리</option>
				<option value="puzzle">퍼즐</option>
				<option value="collection">수집</option>
				<option value="reading">독서</option>
				<option value="exercise">운동</option>
				<option value="photo">포토</option>
				<option value="handmade">수공예</option>
				<option value="instrument">악기 연주</option>
				<option value="astronomical">천체 관측</option>
			</select>
		</div>
		<div class="form-group">
			<label for="input_content">Content</label>
			<textarea name="input_content" id="input_content" class="form-control textarea-control" style="min-height: 200px;resize:none;" placeholder="내용을 입력해주세요."></textarea>
		</div>
		<div class="form-group">
			<label for="input_file">File</label>
			<input type="file" id="input_file" name="input_file" class="form-control file-control">
		</div>
		<button type="submit" class="btn btn-primary submit-btn" onclick="validateFileForm(event);">작성하기</button>
	</form>
</div>
</body>
</html>

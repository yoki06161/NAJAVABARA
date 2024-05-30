<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/sessionCheck.jsp"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
<script>
	var num = '<%=request.getParameter("num")%>'; 
	// 서버에서 num 값을 받아와서 JavaScript 변수에 할당

	// 페이지가 로드될 때 상태를 추가
	window.onload = function() {
		history.pushState(null, null, location.href);
	}
	// 뒤로가기 클릭 시 경고창
	window.onpopstate = function() {
		const input = confirm("게시물 작성을 취소하고 이전 페이지로 돌아가시겠습니까?");
		if (input) {
			window.history.back();
		} else {
			alert('게시물을 계속 작성합니다.');
			// 상태를 다시 추가하여 popstate 이벤트가 반복적으로 발생하지 않도록 함
			history.pushState(null, null, location.href);
		}
	};
	// 게시물 작성 취소 경고창
	function write(num) {
		const input = confirm("게시물 작성을 취소하고 이전 페이지로 돌아가시겠습니까?");
		if (input) {
			window.history.back();
			history.pushState(null, null, location.href);
		} else {
			alert('게시물을 계속 작성합니다.');
			// 상태를 다시 추가하여 popstate 이벤트가 반복적으로 발생하지 않도록 함
			history.pushState(null, null, location.href);
		}
	};
	// 브라우저 창을 닫으려 할 때 경고 메시지
	window.onbeforeunload = function(event) {
		var message = "페이지를 떠나시겠습니까? 작성 중인 내용이 저장되지 않을 수 있습니다.";
		event = event || window.event;

		// IE와 Firefox에서는 returnValue 속성에 메시지를 설정해야 합니다.
		if (event) {
			event.returnValue = message;
		}

		// 다른 브라우저에서는 함수의 반환 값으로 메시지를 설정합니다.
		return message;
	};
	// 게시물 작성 완료
	function handleFormSubmit(event) {
		// onbeforeunload 이벤트를 일시적으로 제거
		window.onbeforeunload = null;
		formSubmitted = true; // 폼이 제출되었음을 나타내는 변수 설정
		// 폼의 action을 updateProc.reg로 설정하여 해당 주소로 제출
		var form = event.target;
		form.action = "writeProc.reg";
		form.submit();
		// 폼 제출 후에 list.reg으로 이동
		location.href = "list.reg";
		return false; // 폼 제출을 중지하고 기본 동작을 실행하지 않도록 합니다.
	}
</script>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<h1>게시물 작성</h1>
<form name="writeForm" method="post" action="<%=request.getContextPath()%>/region/writeProc.reg" enctype="multipart/form-data">
	<img>
	<table class="table table-bordered">
		<tr class="align-middle"><td>제목</td><td><input type="text" name="title" class="form-control" required></td></tr>
		<tr class="align-middle"><td>내용</td>
		<td><textarea name="content" class="form-control"  rows="10" required></textarea></td></tr>
		<tr class="align-middle"><td>이미지 첨부</td>
		<td><input type="file" name="file" class="form-control"></td></tr>
	</table>
	<input type="submit" value="게시물 작성" class="btn btn-primary">
	<a href="javascript:write();"
			class="btn btn-outline-primary">게시물 작성 취소</a>
</form>

</body>
</html>
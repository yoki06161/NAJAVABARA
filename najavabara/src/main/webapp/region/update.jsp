<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% // param
RegionDTO dto = (RegionDTO)request.getAttribute("reg");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= dto.getTitle()%></title>
<script>
	var getnum = '<%= request.getParameter("num") %>'; // 서버에서 num 값을 받아와서 JavaScript 변수에 할당

	// 페이지가 로드될 때 상태를 추가
	window.onload = function() {
	    history.pushState(null, null, location.href);
	}
	// 뒤로가기 클릭 시 경고창
	window.onpopstate = function() { 
		const input = confirm("게시물 수정을 취소하고 이전 페이지로 돌아가시겠습니까?");
		if(input){
			location.href = "view.reg?num="+getnum;
		} else {
			alert('게시물을 계속 수정합니다.');
			// 상태를 다시 추가하여 popstate 이벤트가 반복적으로 발생하지 않도록 함
            history.pushState(null, null, location.href);
		}
	};
	// 게시물 수정 취소 경고창
	function edit(getnum){
		const input = confirm("게시물 수정을 취소하고 이전 페이지로 돌아가시겠습니까?");
		if(input){
			location.href = "view.reg?num="+getnum;
		} else {
			alert('게시물을 계속 수정합니다.');
			// 상태를 다시 추가하여 popstate 이벤트가 반복적으로 발생하지 않도록 함
            history.pushState(null, null, location.href);
		}
	};
	// 브라우저 창을 닫으려 할 때 경고 메시지
	 window.onbeforeunload = function(event) {
         var message = "페이지를 떠나시겠습니까? 수정 중인 내용이 저장되지 않을 수 있습니다.";
         event = event || window.event;

         // IE와 Firefox에서는 returnValue 속성에 메시지를 설정해야 합니다.
         if (event) {
             event.returnValue = message;
         }

         // 다른 브라우저에서는 함수의 반환 값으로 메시지를 설정합니다.
         return message;
     };
     function handleFormSubmit(event) {
    	 // onbeforeunload 이벤트를 일시적으로 제거
         window.onbeforeunload = null;
         formSubmitted = true; // 폼이 제출되었음을 나타내는 변수 설정
         // 폼의 action을 updateProc.reg로 설정하여 해당 주소로 제출
         var form = event.target;
         form.action = "updateProc.reg";
         form.submit();
         // 폼 제출 후에 view.reg?num=getnum으로 이동
         location.href = "view.reg?num=" + getnum;
         return false; // 폼 제출을 중지하고 기본 동작을 실행하지 않도록 합니다.
     }
 </script>
</script>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<h2>게시물 수정</h2>
<form name="updateForm" method="post" onsubmit="handleFormSubmit(event);">
<input type="hidden" name="num" value="<%=dto.getNum()%>">
<table class="table table-bordered">
	<tr>
	<!-- <td>번호</td><td>dto.getNum()</td> -->
	<td>아이디(이름)</td><td><%=dto.getId()%>(<%=dto.getName() %>)</td>	
	</tr>
	<tr>
	<td>작성일자</td><td><%=dto.getPostdate() %></td>
	<td>조회</td><td><%=dto.getVisitcount() %></td>	
	</tr>
	<tr><td>제목</td><td colspan="3"><input type="text" name="title" class="form-control" value="<%=dto.getTitle() %>"></td></tr>
	<tr><td>내용</td><td colspan="3"><input type="text" name="content" class="form-control" value="<%=dto.getContent() %>"></td></tr>	
</table>
<input type="submit" value="게시물 수정 완료" class="btn btn-primary"> 
<a href="javascript:edit('<%=dto.getNum()%>');" class="btn btn-outline-primary">게시물 수정 취소</a>
</form>
</body>
</html>
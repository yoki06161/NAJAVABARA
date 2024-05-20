<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% // param
RegionDTO dto = (RegionDTO)request.getAttribute("dto");
String saveFolder="Uploads";
String requestFolder=request.getContextPath()+"/"+saveFolder;
String fname = dto.getSfile();
String fullpath = requestFolder+"/"+fname;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= dto.getTitle()%></title>
<script>
	function del(num){
		const input = confirm("정말 삭제 할까요?");
		if(input){
			location.href = "<%=request.getContextPath()%>/region/deleteProc.reg?num="
					+ num;
		} else {
			alert('삭제를 취소 했습니다.');
			return;
		}

	}
</script>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h2>글 상세보기</h2>
	<table class="table table-bordered">
		<tr>
			<td>번호</td>
			<td><%=dto.getNum() %></td>
			<td>아이디(이름)</td>
			<td><%=dto.getId()%>(<%=dto.getName() %>)</td>
			<td>지역</td>
			<td><%= dto.getArea() %></td>
		</tr>
		<tr>
			<td>작성일자</td>
			<td><%=dto.getPostdate() %></td>
			<td>조회</td>
			<td><%=dto.getVisitcount() %></td>
		</tr>
		<tr>
			<td>제목</td>
			<td colspan="3"><%=dto.getTitle() %></td>
		</tr>
		<tr>
			<td>내용</td>
			<td colspan="3"><%=dto.getContent() %></td>
		</tr>
		<tr>
			<td>이미지 첨부</td>
				<% if(dto.getOfile() == null) { %>
					<td colspan="5"><img src="../region/europe-3483539_1280.jpg"></td>
				<% } else { %>
					<td colspan="5"><img src="<%= fullpath %>"></td>
				<%} %>
		</tr>
	</table>
	<a href="list.reg" class="btn btn-outline-primary">게시물 목록</a>
	<%if(session.getAttribute("id") != null && session.getAttribute("id").equals(dto.getId())) {%>
	<a href="update.reg?num=<%=dto.getNum()%>"
		class="btn btn-outline-primary">게시물 수정</a>
	<a href="javascript:del('<%=dto.getNum()%>');"
		class="btn btn-outline-primary">게시물 삭제</a>
	<%} %>

</body>
</html>
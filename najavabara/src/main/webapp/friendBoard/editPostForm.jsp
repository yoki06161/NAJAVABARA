<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정</title>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<div class="container mt-5">
		<h2 class="mb-4">게시물 수정</h2>

		<%
		// 게시물 정보가 request 속성에 존재하는지 확인
		if (request.getAttribute("post") != null) {
			// 게시물 정보를 가져옴
			dto.friendBoardDTO post = (dto.friendBoardDTO) request.getAttribute("post");
		%>
		<form action="updatePost.fri" method="post"
			enctype="multipart/form-data">
			<div class="mb-3">
				<label for="title" class="form-label">제목</label>
				<!-- 제목 필드에 기존 제목 값을 설정 -->
				<input type="text" class="form-control" id="title" name="title"
					value="<%=post.getTitle()%>" required>
			</div>
			<div class="mb-3">
				<label for="content" class="form-label">내용</label>
				<!-- 내용 필드에 기존 내용 값을 설정 -->
				<textarea class="form-control" id="content" name="content" rows="5"
					required><%=post.getContent()%></textarea>
			</div>
			<div class="mb-3">
				<label for="file" class="form-label">파일 첨부</label>
				<!-- 파일 첨부 필드 -->
				<input type="file" class="form-control" id="file" name="file"
					multiple>
				<%
				if (post.getFileNames() != null && !post.getFileNames().isEmpty()) {
					StringBuilder currentFiles = new StringBuilder();
					for (int i = 0; i < post.getFileNames().size(); i++) {
						String fileName = post.getFileNames().get(i);
						String filePath = "uploads/" + fileName;
						if (i > 0) {
					currentFiles.append(", "); // 첫 번째 파일명 이후에 쉼표 추가
						}
				%>
				<!-- 모든 파일에 대해 파일명을 표시 -->
				<%
				String ofileName = post.getOfileNames().get(i);
				currentFiles.append("<a href='" + request.getContextPath() + "/friendBoard/uploads/" + fileName + "' target='_blank' class='link-secondary'>"
						+ ofileName + "</a>"); // 파일명을 링크로 추가
				}
				String currentFilesStr = currentFiles.toString();
				%>
				<p>
					현재 파일:
					<%=currentFilesStr%>
				</p>	
				<%
				}
				%>
				
				<%
				if (post.getFileNames() != null && !post.getFileNames().isEmpty()) {
					String currentFile = post.getFileNames().get(0);
				%>
				<input type="hidden" name="currentFile" value="<%=currentFile%>">
				<%
				}
				%>
			</div>
			<!-- 게시물 번호를 전송하기 위한 hidden 필드 -->
			<input type="hidden" name="num" value="<%=post.getNum()%>">
			<button type="submit" class="btn btn-primary">수정 완료</button>
			<a href="viewPost.fri?num=<%=post.getNum()%>"
				class="btn btn-secondary ms-2">취소</a>
		</form>
		<%
		} else {
		%>
		<p>게시물을 찾을 수 없습니다.</p>
		<%
		}
		%>
	</div>
</body>
</html>

<%@page import="dao.accidentCommentDAO"%>
<%@page import="dto.accidentCommentDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.accidentBoardDTO"%>
<%@page import="dao.accidentBoardDAO"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<%@ include file="../common/menu.jsp"%>

<title>View Post</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
	rel="stylesheet">
</head>
<body>
	<%

		    int num = 0;
		    accidentBoardDTO post = null;
		    List<accidentCommentDTO> comments = null; // comments 변수를 선언
		    accidentBoardDAO dao = new accidentBoardDAO(); // BoardDAO 객체를 try 블록 외부에서 생성
		    try {
		        num = Integer.parseInt(request.getParameter("num"));
		        post = dao.selectById(num);
		        if (post == null) {
		            // 게시물을 찾을 수 없을 때의 처리 로직
		            out.println("<div class='alert alert-danger'>No post found with the specified ID.</div>");
		        } else {
		            // 댓글 목록 가져오기
		            accidentCommentDAO commentDAO = new accidentCommentDAO();
		            comments = commentDAO.getComments(num);
		        }
		    } catch (NumberFormatException e) {
		        out.println("<div class='alert alert-danger'>Invalid post ID format.</div>");
		    } catch (Exception e) {
		        out.println("<div class='alert alert-danger'>An error occurred while retrieving the post.</div>");
		    }

		    if (post != null) {
	%>
	<div class="container mt-5">
		<h1 class="mb-4">게시글</h1>
		<div class="card">
			<div class="card-body">
				<h2 class="card-title"><%=post.getTitle()%></h2>
				<p class="card-text"><%=post.getContent()%></p>
				<ul class="list-group list-group-flush">
					<li class="list-group-item"><strong>Writer:</strong> <%=post.getId()%></li>
					<li class="list-group-item"><strong>Created Date:</strong> <%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(post.getPostdate())%></li>
					<li class="list-group-item"><strong>Visit Count:</strong> <%=post.getVisitcount()%></li>
				</ul>
				<button onclick="updateLikeDislike(<%=post.getNum()%>, 'like')"
					class="btn btn-outline-success">좋아요</button>
				<span id="likesCount<%=post.getNum()%>"><%=post.getLikes()%></span>
				<button onclick="updateLikeDislike(<%=post.getNum()%>, 'dislike')"
					class="btn btn-outline-danger">싫어요</button>
				<span id="dislikesCount<%=post.getNum()%>"><%=post.getDislikes()%></span>
			</div>
		</div>
		<div class="mt-4">
			<a href="edit.acc?num=<%=post.getNum()%>" class="btn btn-primary">수정하기</a>
			<a href="list.acc" class="btn btn-secondary">뒤로 돌아가기</a>
		</div>
		<div class="mt-5">
			<h4>댓글</h4>
			<div id="commentSection">
				<%
				if (comments != null && !comments.isEmpty()) {
				                    for (accidentCommentDTO comment : comments) {
				%>
				<div class="card mt-3">
					<div class="card-body">
						<p class="card-text">
							<strong><%=comment.getWriter()%></strong>
						</p>
						<p class="card-text"><%=comment.getComment()%></p>
						<p class="card-text">
							<small class="text-muted"><%=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(comment.getRegDate())%></small>
						</p>
					</div>
				</div>
				<%
                    }
                } else {
                %>
				<p>등록된 댓글이 없습니다</p>
				<%
                }
                %>
			</div>
			<div class="mt-4">
				<h5>댓글작성</h5>
				<!-- 댓글 작성 폼 -->
				<form action="addComment.acc" method="post" id="commentForm">
					<div class="form-group">
						<label for="writer">이름</label> <input type="text"
							class="form-control" name="writer" id="writer" required>
					</div>
					<div class="form-group">
						<label for="comment">내용</label>
						<textarea class="form-control" name="comment" id="comment"
							rows="3" required></textarea>
					</div>
					<input type="hidden" name="postNum" value="<%=post.getNum()%>">
					<button type="submit" class="btn btn-primary mt-2">Submit</button>
				</form>
			</div>
		</div>
	</div>
	<%
    }
    %>
	<script>
	function updateLikeDislike(postId, actionType) {
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "likeDislike.acc", true);
		xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		xhr.onload = function() {
			if (xhr.status === 200) {
				var res = JSON.parse(xhr.responseText);
				document.getElementById('likesCount' + postId).textContent = res.likes;
				document.getElementById('dislikesCount' + postId).textContent = res.dislikes;
			} else {
				console.error('Request failed.  Returned status of ' + xhr.status);
			}
		};
		xhr.send("postId=" + postId + "&action=" + actionType);
	}

	</script>
</body>
</html>

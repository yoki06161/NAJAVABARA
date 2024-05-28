<%@page import="java.util.List"%>
<%@page import="Review_Comment.ReviewCommentDTO"%>
<%@page import="Review_Comment.ReviewCommentDAO"%>
<%@page import="Review_Board.Review_BoardDTO"%>
<%@page import="Review_Board.Review_BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// review_boardList에서 보낸 content.jsp?cnum=의 값을 받아들임
String snum = request.getParameter("cnum");
int num = Integer.parseInt(snum);

Review_BoardDAO dao = new Review_BoardDAO();
Review_BoardDTO dto = new Review_BoardDTO();

// dto의 num에 전달 받은 num값 넣기
dto.setNum(num);

// ShowContentBynum에서 넣은 dto값(db)을 새 dto에 넣음. 내용 보여주는 메소드
dto = dao.ShowContentBynum(dto);

// 댓글 보여주기
ReviewCommentDAO cao = new ReviewCommentDAO();
// 위의 넘값을 메소드에 넣어서 넘값에 맞춘 리스트 출력후 반환
List<ReviewCommentDTO> clist = cao.ShowCommentbyNum(num);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>내용</title>
<style type="text/css">
	a {
	  text-decoration: none;
	  color: black;
	}
	
 	textarea:focus {
		outline: none;
 	}
	
	.wrap {
		display: flex;
		justify-content: center;
		padding-top: 100px;
		padding-bottom: 300px;
	}
	
	.co_write {
		padding: 100px 0;
	}

	.writeCo_wrap {
		display: flex;
		align-items: center;
		gap: 40px;
	}
	
	.comment_area {
		padding: 30px 40px;
		border-radius: 100px;
		height: 90px;
		width: 700px;
		font-size: 20px;
		resize: none;
	}
	
	.wcbtn {
		color: #fff;
		border: none;
		border-radius: 20px;
		background: #ff6f0f;
		padding: 20px 30px;
		font-size: 15px;
	}
	
	.img_wrap {
		height: 500px;
		border-radius: 50px;
		overflow: hidden;
	}
	
	.img_wrap img {
		width: 100%;
        height: 100%;
		object-fit: cover;
		object-position: center;
	}
	
	.title_wrap {
		display: flex;
		justify-content: space-between;
		align-items: flex-end;
		padding: 20px;
		border-bottom: 2px solid #ddd;
	}
	.title {
		font-size: 50px;
	}
	
	.id {
		font-size: 20px;
	}
	
	.etc_wrap {
		padding: 20px;
		display: flex;
		justify-content: flex-end;
	}
	
	.update_wrap {
		padding: 0 20px;
		display: flex;
		justify-content: flex-end;
		gap: 10px;
		align-items: center;
	}
	
	.update_wrap a {
		border: 1px solid #ddd;
		padding: 5px 10px;
		border-radius: 10px;
	}
	
	.content {
		padding: 20px 20px;
		font-size: 40px;
		padding-bottom: 100px;
		border-bottom: 1px solid #ddd;
	}
	
	.like_wrap {
		display: flex;
		justify-content: center;
		gap: 20px;
		padding: 20px 0;
	}
	
	.co_wrap {
		border: 1px solid #ddd;
		margin-bottom: 50px;
		border-radius: 20px;
	}
	
	.co_title {
		display: flex;
		justify-content: space-between;
		border-bottom: 1px solid #ddd;
		padding: 20px 50px;
	}
	
	.co_etc {
		display: flex;
		gap: 10px;
	}
	
	.co_conmments {
		padding: 50px;
		font-size: 20px;
	}
</style>
<!--  gpt -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	function deleteAction(dnum) {
		const alert = confirm("정말 삭제하시겠습니까?");
		if(alert){
			location.href= "<%=request.getContextPath()%>/DeleteReviewAction.rev_bo?dnum="+dnum;
		}else {
			alert("삭제가 취소되었습니다.");
			return;
		}
	}
	// const alert = confirm("상대에게 불쾌감을 안겨줄수 있는 내용은 신고의 대상이 될 수 있습니다.");
	function submit_comment() {
		const cform = document.getElementById('commentF');
		console.dir(cform);
		
		cform.submit();
	}
	function deleteCo(dnum, ddate) {
		const alert = confirm("정말 삭제하시겠습니까?");
		if(alert){
			// encodeURIComponent은 문자열 보내려고 쓰는거. 콘텐츠 내용으로 구분할 예정이라
			location.href = "<%=request.getContextPath()%>/DeleteComment.rev_co?dnum="+dnum+"&ddate="+encodeURIComponent(ddate);
		}else {
			alert("삭제가 취소되었습니다.");
			return;
		}
	}
	
	function submit_like() {
		$.ajax({
			type: "post",
			//  action="< %request.getContextPath() %>/WriteReviewComment.rev_co"마냥 서블렛에 보내는거.
			url: "<%=request.getContextPath()%>/LikeCount.rev_bo",
			// input_conum의 데이터값을 < %=dto.getNum()%>으로 정의
			// input_conum이란 이름의 데이터 필드 설정. 값은 dto.getnum()으로 정의
			data: { input_conum: <%=dto.getNum()%> },
			// ajax의 if문 같은것. sucees와 error
			success: function(data) {
				// 좋아요 카운트 업뎃
				$('#like_count').text(data);
			},
			error: function() {
				alert("오류발생");
			}
		});
	}
</script>
</head>
<body>
<div class="wrap">
<div class="border">
<%if(dto.getNewFile() != null) { %>
<!--
<img alt="업로드된 파일" src="C:/Users/TJ/git/NAJAVABARA/najavabara/src/main/webapp/SaveUploads/20240522_131137997.png">
식으로 쓰면 자바에선 되는데 크롬에선 안됨. 상대경로로 써야함
dto의 뉴파일명 = <%=dto.getNewFile() %><br>
dto의 원래 파일명 = <%=dto.getOriFile() %><br>
-->
<div class="img_wrap">
	<img alt="<%=dto.getOriFile() %>" src="../SaveUploads/<%=dto.getNewFile()%>">
</div>
<%} %>
<div class="title_wrap">
	<span class="title"><%=dto.getTitle() %></span>
	<span class="id"><%=dto.getId() %> 님</span>
</div>
<div class="etc_wrap">
	<%=dto.getPostdate() %>
	조회수 <%=dto.getVisitcount() %>
</div>
<div class="update_wrap">
	<%if(session.getAttribute("id") != null && session.getAttribute("id").equals(dto.getId())){ %>
		<a href="review_update.jsp?unum=<%=dto.getNum()%>">수정하기</a> | 
		<a href="javascript:deleteAction('<%=dto.getNum()%>');">삭제하기</a>
	<%} %>
</div>
<div class="content">
	<%=dto.getContent() %>
</div>
<div class="like_wrap">
<!-- 빅빅 꼼수 전
 <input type="button" value="좋아용0" onclick="like_count()">
 -->
 <!-- !!!!!!!!!!!!!!!!!!!!!!주석 
 <form id="likeF" name="likeF" method="post" action="<%=request.getContextPath()%>/LikeCount.rev_bo">
	<input name="input_conum" type="hidden" value="<%=dto.getNum()%>">
 	<input type="button" value="좋아용" onclick="submit_like()">
 	<span id='like_count'><%=dto.getLike() %></span>
 </form>
<input type="button" value="싫어용">
 -->
</div>

<!-- 댓글 쓰기 -->
<div class="co_write">
<form id="commentF" name="commentF" method="post" action="<%=request.getContextPath() %>/WriteReviewComment.rev_co">
	<div class="writeCo_wrap">
		<input name="input_conum" type="hidden" value="<%=dto.getNum()%>">
		<textarea name="input_comment" placeholder="청정한 댓글쓰기 문화를 지킵시다." class="comment_area"></textarea>
		<input type="button" value="댓글쓰기" onclick="submit_comment()" class="wcbtn">
	</div>
</form>
</div>
<!-- 댓글 리스트 -->
<%if(clist != null && !clist.isEmpty()) { // clist 존재여부
	for(ReviewCommentDTO cto : clist) {
%>
<div class="co_wrap">
	<div class="co_title">
		<span><%=cto.getId() %>님</span>
		<div class="co_etc">
		<span><%=cto.getDate() %></span>
		<span><%if(session.getAttribute("id") != null && session.getAttribute("id").equals(cto.getId())){ %>
			<a href="review_coUpdate.jsp?cnum=<%=dto.getNum()%>&cdate=<%=cto.getTdate()%>">수정</a> |
			<a href="javascript:deleteCo('<%=cto.getNum() %>', '<%=cto.getTdate()%>');">삭제</a>
		<%} %></span>
		</div>
	</div>
	<div class="co_conmments">
		<%=cto.getComment()%>
	</div>
</div>
<% } } %>
</div>
</div>
</body>
</html>
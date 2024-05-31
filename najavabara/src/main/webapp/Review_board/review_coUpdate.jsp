<%@page import="java.util.List"%>
<%@page import="dao.ReviewCommentDAO"%>
<%@page import="dto.Review_BoardDTO"%>
<%@page import="dao.Review_BoardDAO"%>
<%@page import="dto.ReviewCommentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/menu.jsp" %>
<%
// review_boardList에서 보낸 content.jsp?cnum=의 값을 받아들임
String snum = request.getParameter("cnum");
int num = Integer.parseInt(snum);
String tdate = request.getParameter("cdate");

dao.Review_BoardDAO dao = new dao.Review_BoardDAO();
dto.Review_BoardDTO dto = new dto.Review_BoardDTO();

// dto의 num에 전달 받은 num값 넣기
dto.setNum(num);

// ShowContentBynum에서 넣은 dto값(db)을 새 dto에 넣음. 내용 보여주는 메소드
dto = dao.ShowContentBynum(dto);
// 조회수 카운트 메소드
dao.CountVisitors(dto);

// 댓글 보여주기
ReviewCommentDAO cao = new ReviewCommentDAO();
// 위의 넘값을 메소드에 넣어서 넘값에 맞춘 리스트 출력후 반환
List<ReviewCommentDTO> clist = cao.ShowCommentbyNum(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내용</title>
<style type="text/css">
	a {
	  text-decoration: none;
	  color: black;
	}
	
	.border {
		border: none !important;
	}
	
 	input:focus {
		outline: none;
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
		width: 700px;
		font-size: 20px;
		resize: none;
	}
	
	.wcbtn {
		color: #fff;
		border: none;
		border-radius: 20px;
		background: #0d6efd;
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

	
	.up_com {
		border: none;
		width: 100%;
		font-size: 20px;
		resize: none;
	}
	
	.btn_wrap {
		display: flex;
		justify-content: flex-end;
		gap: 10px;
	}
	
	.up_btn {
		background: none;
		border: none;
		font-size: 15px;
	}
	
	.del_btn {
		font-size: 15px;
	}

</style>
<script type="text/javascript">
	function like_count() {
		let num = 0;
		count++;
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
	
	function submit_comment() {
		const cform = document.getElementById('commentF');
		console.dir(cform);
		
		cform.submit();
	}
	
	function update_comment() {
		const cform = document.getElementById('commentUF');
		console.dir(cform);
		
		cform.submit();
	}
</script>
</head>
<body>
<div class="wrap">
<div class="border">
	<div class="img_wrap">
	<img alt="<%=dto.getOriFile() %>" src="../SaveUploads/<%=dto.getNewFile()%>">
</div>
<div class="title_wrap">
	<span class="title"><%=dto.getTitle() %></span>
	<span class="id"><%=dto.getId() %> 님</span>
</div>
<div class="etc_wrap">
	<%=dto.getPostdate() %>
	조회수 <%=dto.getVisitcount() %>
</div>
<div class="content">
	<%=dto.getContent() %>
</div>
<div class="like_wrap">
<!-- 
 <form id="likeF" name="likeF" method="post" action="<%=request.getContextPath()%>/LikeCount.rev_bo">
	<input name="input_conum" type="hidden" value="<%=dto.getNum()%>">
 	<input type="button" value="좋아용" onclick="submit_like()">
 	<span id='like_count'><%=dto.getLike() %></span>
 </form>
<input type="button" value="싫어용">
 -->
</div>
<div class="co_write">
<form id="commentF" name="commentF" method="post" action="<%=request.getContextPath() %>/WriteReviewComment.rev_co">
	<div class="writeCo_wrap">
		<input name="input_conum" type="hidden" value="<%=dto.getNum()%>">
		<textarea name="input_comment" placeholder="청정한 댓글쓰기 문화를 지킵시다." class="comment_area"></textarea>
		<input type="button" value="댓글쓰기" onclick="submit_comment()" class="wcbtn">
	</div>
</form>
</div>
<%if(clist != null && !clist.isEmpty()) { // clist 존재여부
	for(ReviewCommentDTO cto : clist) {
%>

<!-- 진짜 어째서인지 input이랑 textarea이름을 update_하면 안되고, input으로 해야 js함수가 된다. 왜????  
안됐던 이유. document로 써서...
함수쪽에서
const cform = document.commentUF;
document는 html전체에서 인식해서 찾는거라, id로 설정하고 하는걸 추천한대.
-->
<!-- 댓글 수정코드 -->

<div class="co_wrap">
	<div class="co_title">
		<span><%=cto.getId() %>님</span>
		<div class="co_etc">
		<span><%=cto.getDate() %></span>
		</div>
	</div>
	<div class="co_conmments">
		<!-- 아이디로 본인이 쓴 글이며, 아까 누른글. -->
		<%if(session.getAttribute("id") != null && session.getAttribute("id").equals(cto.getId()) && tdate.equals(cto.getTdate())){ %>
			<form id="commentUF" name="commentUF" method="post" action="<%=request.getContextPath() %>/UpdateComment.rev_co">
				<input name="input_conum" type="hidden" value="<%=dto.getNum()%>">
				<input name="input_time" type="hidden" value="<%=cto.getTdate() %>">
				<textarea name="up_comment" placeholder="청정한 댓글쓰기 문화를 지킵시다." class="up_com"><%=cto.getComment() %></textarea>
				<div class="btn_wrap">
					<input type="button" value="수정 완료" onclick="update_comment()" class="up_btn">
					<a href="javascript:deleteCo('<%=cto.getNum() %>', '<%=cto.getTdate()%>');" class="del_btn">삭제</a>
				</div>
			</form>
		<%} else {%>
			<%=cto.getComment() %>
		<%} %>
	</div>
</div>
<% } } %>
</div>
</div>
</body>
</html>
<%@page import="java.util.List"%>
<%@page import="Review_Comment.ReviewCommentDTO"%>
<%@page import="Review_Comment.ReviewCommentDAO"%>
<%@page import="Review_Board.Review_BoardDTO"%>
<%@page import="Review_Board.Review_BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/menu.jsp" %>
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
// 댓글 로그인 체크
Boolean islogin = session.getAttribute("id") == null;
System.out.println("테스트");

// 좋아요 추가
boolean islike = false;
int like_num = 0;
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
	
	.border {
		border: none !important;
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
		width: 860px;
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
//자바 변수를 js로 전달
var islogin = <%=islogin ? "true" : "false" %>;
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
		if(islogin){
			alert("로그인 후 사용가능한 기능입니다.");
			return;
		}
		// 글쓴 내용 없으면 띄우는거
		const inco = document.getElementById('input_comment');
	    if(inco.value === ""){
	        alert('댓글을 적어주세요');
	        
	        return;
	    }
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
	// 추가한부분
	function click_like() {
		const alert = confirm("인식확인");
		// title속성이 on인 img찾기
		const isHeart = document.querySelector("img[title=on]");
		
		// 버튼 누르기 전까지는 title값이 없으니 html의 if(islike)는 else문을 실행. 누르기전 좋아요를 보여줌
		// 그래서 두번째로 누르면 if의 전좋아용이 실행됨.
		if(isHeart){
			// 이건 걍 이미지 src값을 off좋아요로 바꾸기
			document.getElementById('likeb').setAttribute('src','../SaveUploads/전하트.png');
			// likeb라는 아이디의 title값을 off로 설정하겠단 뜻
			document.getElementById('likeb').setAttribute('title','off');
		}else{
			document.getElementById('likeb').setAttribute('src','../SaveUploads/후하트.png');
			document.getElementById('likeb').setAttribute('title','on');
		}
		
		// 서버에 요청을 보내기 위해 XMLHttpRequest객체 생성
		const xhr = new XMLHttpRequest();
		// onreadystatechange 이벤트는 XMLHttpRequest가 변할때마다 호출됨?
		xhr.onreadystatechange = function() {
			// xhr.readyState는 요청의 현재 상태를 나타냄.
			// XMLHttpRequest.DONE은 요청이 완료됐단 뜻.
			// xhr.status는 HTTP 상태 코드를 나타내며, 200은 성공이란 뜻
			// 요청이 완료되면, 서버로부터 받은 응답(xhr.responseText)을 id가 like_s인 요소의 innerHTML로 설정하여 웹 페이지를 업데이트
			// like_s는 숫자 span
			if(xhr.readyState == XMLHttpRequest.DONE && xhr.status ==200) {
				document.getElementById('like_s').innerHTML = xhr.responseText;
			}
		}
		// xhr.open 메소드를 이용해 get요청 초기화. true는 비동기 요청을 의미. 보내는 코드인듯
		xhr.open('get', 'review_like.jsp?snum=<%=dto.getNum()%>', true);
		// xhr.send(); 초기화된 요청을 서버에 전송함
		xhr.send();
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
<!--추가한부분 
<div class="like_wrap">
	<%if(islike) { %>
		<img id="likeb" alt="누르기후 좋아용" src="../SaveUploads/후하트.png" width="100px" onclick="click_like()">
	<%}else{ %>
		<img id="likeb" alt="누르기전 좋아용" src="../SaveUploads/전하트.png" width="100px" onclick="click_like()">
	<%} %><br>
	게시물 번호 <%=dto.getNum() %><br>
	라이크값 <span id="like_s"><%=dto.getLike()%></span>
</div>
-->

<!-- 댓글 쓰기 -->
<div class="co_write">
<form id="commentF" name="commentF" method="post" action="<%=request.getContextPath() %>/WriteReviewComment.rev_co">
	<div class="writeCo_wrap">
		<input name="input_conum" type="hidden" value="<%=dto.getNum()%>">
		<textarea id="input_comment" name="input_comment" placeholder="청정한 댓글쓰기 문화를 지킵시다." class="comment_area"></textarea>
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
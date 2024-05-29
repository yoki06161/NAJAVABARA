<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="Review_Board.Review_BoardDTO"%>
<%@page import="Review_Board.Review_BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/menu.jsp" %>
<%
//검색 기능
String searching = request.getParameter("search_bar");
System.out.println("검색기능 " + searching);

// 검색어와 검색 조건 맵에 저장
// Map<String, String>은 키값과 값(value) 모두 string타입이란 뜻.
Map<String, String> map = new HashMap<>();

map.put("searching", searching);

// dao에 서치값 넣기
Review_BoardDAO dao = new Review_BoardDAO();
dao.ShowReviewBoard(map);
//dao의 reviewList를 rlist에 담기
List<Review_BoardDTO> rlist = dao.ShowReviewBoard(map);


%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>장소 리뷰 게시판</title>
<style type="text/css">
	a {
	  text-decoration: none;
	  color: black;
	}
	.form_wrap {
		display: flex;
		justify-content: center;
		padding: 20px 0px;
		align-items: center;
		gap: 20px;
	}
	
	.search_bar {
		height: 50px;
		width: 300px;
		font-size: 16px;
		background: #f2f3f6;
		border: none;
		border-radius: 10px;
		padding: 0 10px;
		align-items: center;
	}

 	input:focus {
 		outline: none;
 	}
 	
 	.sbtn {
 		height: 50px;
 		width: 80px;
 		background: #fff;
 		border: 1px solid #d1d3d8;
 		border-radius: 10px;
 		font-weight: bold;
 		margin-left: 10px;
 	}
 	
 	.write_b {
 		color: #fff;
 		height: 50px;
 		width: 80px;
 		border-radius:10px;
 		background: #0d6efd;
 		display: flex;
 		align-items: center;
 		justify-content: center;
 	}
 	
 	.wrap {
 		display: flex;
 		justify-content: center;
 		padding-bottom: 300px;
 	}

	.review_grid {
		width: 1200px;
		display: grid;
		grid-template-columns: repeat(4,1fr);
		gap: 20px;
		padding: 20px;
	}
	
	.review_grid a {
        height: 400px;
        border: 1px solid #ddd;
		border-radius: 20px;
		overflow: hidden;
	}
	
	.review_img {
		width: 100%;
		height: 250px;
		border-radius: 20px;
		overflow: hidden;
	}
	
	.review_img img {
		width: 100%;
        height: 100%;
		object-fit: cover;
		object-position: center;
	}
	
	.review_content {
		padding: 15px;
		text-align: center;
	}
	
	.title {
		font-size: 20px;
		font-weight: bold;
		color: black;
		display: -webkit-box;
	    -webkit-box-orient: vertical;
	    -webkit-line-clamp: 1;
	    overflow: hidden;
	}
	
	.txt_wrap {
		padding-top: 10px;
		color: #787878;
	}
</style>
</head>
<body>
<div class="form_wrap">
<!-- post로하면 영어는 되는데 한글은 깨진다. post가 보여주는거라 그런가? -->
<form method="get">
	<input type="text" name="search_bar" placeholder="게시물을 검색해보아요" class="search_bar">
	<input type="submit" value="검색" class="sbtn">
</form>

<!-- 장소에 사진과 함께 짧은 코멘트를 붙여 게시물을 올리는 게시판 -->
<a href="review_write.jsp" class="write_b">글쓰기</a>
</div>

<div class="wrap">

<div class="review_grid">
<%if(rlist.isEmpty()) { %>
<div cla>
	앗..게시물이 없네요. 우리가 첫글을 남겨보아요!
</div>
<%} else {
	for(Review_BoardDTO dto : rlist) { %>
	<a href="<%=request.getContextPath()%>/Visitors.rev_bo?cnum=<%=dto.getNum()%>">
		<div class="review_img">
		<% if(dto.getOriFile() != null) { %>
			<img alt="<%=dto.getOriFile() %>" src="../SaveUploads/<%=dto.getNewFile()%>">
		<%} else { %>
			<img alt="<%=dto.getOriFile() %>" src="../SaveUploads/logo.png">
		<% }%>
		</div>
		<div class="review_content">
			<!-- <%=dto.getNum()%> -->
			<!-- 서블렛으로 getNum숫자 넘겨주기
			?는 쿼리문자열 시작을 알림. 무조건 key = value형식이라 값 넣어줘야함. 변수 = 내용 느낌-->
			<div class="title">
			<%=dto.getTitle()%>
			</div>
			<div class="txt_wrap">
			<%=dto.getId()%>님<br>
			<%=dto.getPostdate()%><br>
			조회수 <%=dto.getVisitcount()%>
			</div>
		</div>
	</a>
<%} }%>
 </div>
 </div>
</body>
</html>
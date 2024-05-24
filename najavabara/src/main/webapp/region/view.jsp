<%@page import="dao.RegionLikeDAO"%>
<%@page import="dto.RegionDTO"%>
<%@page import="dto.RegionLikeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// param
RegionDTO dto = (RegionDTO) request.getAttribute("dto");
RegionLikeDAO ldao = new RegionLikeDAO();
String id = (String) session.getAttribute("id");
int num = dto.getNum();
RegionLikeDTO ldto = ldao.hasUserLiked(new RegionLikeDTO(id, num));
String buttonClass = (ldto == null) ? "like-btn btn rounded-pill btn-outline-danger" : "like-btn btn rounded-pill btn-danger";


String saveFolder = "Uploads";
String requestFolder = request.getContextPath() + "/" + saveFolder;
String fname = dto.getSfile();
String fullpath = requestFolder + "/" + fname;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=dto.getTitle()%> [게시물 상세보기]</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script>
function del(num) {
    const input = confirm("정말 삭제 할까요?");
    if(input) {
        $(location).attr('href', "<%=request.getContextPath()%>/region/deleteProc.reg?num=" + num);
    } else {
        alert('삭제를 취소 했습니다.');
        return;
    }
}

$(document).ready(function() {
	// 페이지가 로드될 때 실행되는 코드
	const id = '<%=session.getAttribute("id")%>';
	const did = '<%=dto.getId()%>';
	const num = '<%=dto.getNum()%>';
	
	$("button.delete-button").click(function() {
         del(num);
    });
	 
	$(".like-btn").click(function() {
	     //console.log("ID: ", id, "Num: ", num);  // num 값을 로그로 확인
	     //console.log(id, num); // 출력: dto.getNum() 값: num
	     const $button = $(this); // 클릭된 버튼을 변수에 저장

	     // 콘솔에 클릭된 요소 출력
	     //console.log("클릭된 요소:", $button);
	     
	     // 콘솔에 did 출력
	     console.log("dto.getid: ", did);

	     // AJAX 요청 보내기
	     $.ajax({
	     	url: '<%=request.getContextPath()%>/region/likeCheck.jsp',
			contentType : "application/json",
			type : "POST",
			dataType:'json',
			data : JSON.stringify({
				id : id,
				num : num,
				did : did
			}),				
			success : function(data) {
				console.log("data: ", data);
		     	console.log("id: ", id);
		     	console.log("did: ", did);
				console.log("data.rs ", data.rs);
				if (id === did) {
					alert("자신의 게시물에는 좋아요할 수 없습니다");
				} else if (data.rs === 'error') {
					alert("로그인이 필요한 기능입니다");
					window.location.href = "../user/login.jsp";
				} else {
					if (data.rs === 2) {
					} else if (data.rs === 0) {
				    	// 사용자가 해당 게시물에서 좋아요를 누르지 않았을 때 좋아요를 눌렀다면
						console.log("data['rs']: ", data['rs']);
						// btn-outline-danger와 btn-danger 클래스를 토글
	                    $button.removeClass("btn-outline-danger").addClass("btn-danger");
						//$button.find("#likeCount").text(data.likeCount); // 버튼 내부의 likeCount를 업데이트
					} else if (data.rs === 1){
						alert("좋아요는 게시물 당 한 번만 누를 수 있습니다");
					}
					
				} 
			},
			error : function(request, status, error) {
				console.log(request, status, error);
		     	console.log("data,id: ", data,id);
				console.log("data.rs ", data.rs);
			}
		});
	});
});
</script>
<style>
img {
	width: 50%;
	height: 50%;
}
</style>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<h1>게시물 상세보기</h1>
	<br>
	<table class="table table-borderless">
		<tr>
			<!-- <td>번호</td>
			<td>dto.getNum()</td> -->

			<td><input type="hidden" name="num" value="<%=dto.getNum()%>">
			<input type="hidden" name="id" value="<%=dto.getId()%>">
				<h5 class="fw-bold"><%=dto.getId()%>(<%=dto.getName()%>)
				</h5>
				<p style="color: gray;"><%=dto.getArea()%>
					|
					<%=dto.getPostdate()%></p></td>
		</tr>
		<tr>
			<td>
				<h3 class="fw-bold"><%=dto.getTitle()%></h3>
			</td>
		</tr>
		<tr>
			<td><h5><%=dto.getContent()%></h5></td>
		</tr>
		<tr>
			<%
			if (dto.getOfile() == null) {
			%>
			<td style="color: gray;">등록한 이미지가 없습니다.</td>
			<%
			} else {
			%>
			<td><img src="<%=fullpath%>"></td>
			<%
			}
			%>
		</tr>
		<tr>
			<td>
				<!-- 버튼색상: btn-outline-danger, btn-warning -->
				<button type="button"
					class="<%= buttonClass %>"
					value="좋아요 <%=dto.getLikes()%>">
					<span id="likeCount">좋아요 0</span>
				</button>
			</td>
		</tr>
		<tr>
			<td style="color: gray;">조회 <%=dto.getVisitcount()%></td>
		</tr>
	</table>
	<br>
	<a href="list.reg" class="btn btn-outline-primary">게시물 목록</a>
	<%
	if (id != null && id.equals(dto.getId())) {
	%>
	<a href="update.reg?num=<%=dto.getNum()%>"
		class="btn btn-outline-primary">게시물 수정</a>
	<button class="btn btn-outline-primary delete-button"
		data-num="<%=dto.getNum()%>">게시물 삭제</button>
	<%
	}
	%>

</body>
</html>
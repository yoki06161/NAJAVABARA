<%@page import="dto.RegionDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// param
RegionDTO dto = (RegionDTO) request.getAttribute("dto");
String saveFolder = "Uploads";
String requestFolder = request.getContextPath() + "/" + saveFolder;
String fname = dto.getSfile();
String fullpath = requestFolder + "/" + fname;
String id = (String) session.getAttribute("id");
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
	$("button.delete-button").click(function() {
         const num = '<%=dto.getNum()%>';
         del(num);
    });
	 
	$(document).on("click", ".like-btn", function() {
	     const id = '<%= session.getAttribute("id") %>';
	     const num = '<%=dto.getNum()%>';
	     //console.log(id, num); // 출력: dto.getNum() 값: num
	     const $button = $(this); // 클릭된 버튼을 변수에 저장

	     // 콘솔에 클릭된 요소 출력
	     //console.log("클릭된 요소:", $button);

	     // AJAX 요청 보내기
	     $.ajax({
	     	url: '<%=request.getContextPath()%>/region/likeCheck.jsp',
			contentType : "application/json",
			type : "POST",
			data : JSON.stringify({
				id : id,
				num : num
			}),
			success : function(data) {
				console.log("data: ", data);
				//console.log("data['rs']: ", data['rs']);
				// JSON 문자열을 JSON 객체로 파싱
			    var responseData = JSON.parse(data);
			    console.log("responseData['rs']: ", responseData['rs']);
			    
				if (responseData['rs'] === "0") {
					console.log("data['rs']: ", data['rs']);
					// btn-outline-danger와 btn-danger 클래스를 토글
                    $button.removeClass("btn-outline-danger").addClass("btn-danger");
					$button.find("#likeCount").text(data.likeCount); // 버튼 내부의 likeCount를 업데이트
				} else {
					alert("좋아요는 게시물 당 한 번만 누를 수 있습니다");
				}
			},
			error : function(request, status, error) {
				console.log(request, status, error);
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
			
			<td>
			<input type="hidden" name="num" value="<%=dto.getNum() %>">
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
				class="like-btn btn btn-outline-danger 
				 	rounded-pill"
				value="좋아요 <%=dto.getLikes()%>"> <span id="likeCount">좋아요 0</span></button>
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
	<button class="btn btn-outline-primary delete-button" data-num="<%=dto.getNum()%>">게시물 삭제</button>
	<%
	}
	%>

</body>
</html>
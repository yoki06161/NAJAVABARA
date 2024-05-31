<%@page import="com.mysql.cj.log.ProfilerEventImpl"%>
<%@page import="dao.Review_BoardDAO"%>
<%@page import="dto.Review_BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/menu.jsp" %>
<%
	request.setCharacterEncoding("utf-8");

	String rnum = request.getParameter("unum");
	int num = Integer.parseInt(rnum);
	
	dto.Review_BoardDTO dto = new dto.Review_BoardDTO();
	// dto에 저장해서 밑의 input에서 불러오는용. input에선 저 넘값을 UpdateReviewAction로 보내서 dao에 저장한다
	dto.setNum(num);
	
	dao.Review_BoardDAO dao = new dao.Review_BoardDAO();
	// 내용 보여주는 코드
	dto = dao.ShowContentBynum(dto);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내용</title>
<style type="text/css">
 	input:focus {
		outline: none;
 	}
 	
 	textarea:focus {
		outline: none;
 	}
	
	.wrap {
		display: flex;
		justify-content: center;
		padding-bottom: 200px;
	}
	
	#updateF {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	
	.file_btn {
		width: 900px;
		padding: 20px;
		display: flex;
		justify-content: flex-end;
	}
	
	.file_btn .clearbtn {
		border: 1px solid #ddd;
	    padding: 5px 10px;
	    border-radius: 10px;
	    background: none;
	    font-size: 15px;
	}
	
	.file_btn input[type=file]::file-selector-button {
		border: 1px solid #ddd;
	    padding: 5px 10px;
	    border-radius: 10px;
	    background: none;
	    font-size: 15px;
	}
	
	.up_title {
        height: 100px;
        width: 900px;
        font-size: 50px;
        border: none;
        border-bottom: 2px solid #ddd;
        padding: 0 20px;
    }

    .up_cont {
        width: 900px;
        height: 500px;
        font-size: 40px;
        resize: none;
        border: none;
        padding: 31px;
        border-bottom: 2px solid #ddd;
    }
    
	.img_wrap {
		width: 900px;
		height: 500px;
		border-radius: 50px;
		overflow: hidden;
		border: 1px solid #ddd;
	}

	.img_wrap img {
		width: 100%;
        height: 100%;
		object-fit: cover;
		object-position: center;
	}
	
	.btn_wrap {
		width: 900px;
		display: flex;
		justify-content: flex-end;
		padding: 20px;
	}
	
    .ubtn {
    	color: #fff;
		border: none;
		border-radius: 20px;
		background: #0d6efd;
		padding: 20px 30px;
		font-size: 15px;
    }
</style>
<script type="text/javascript">
function sendReview() {
	const uform = document.getElementById('updateF');
	
	console.dir(uform);

	// 타이틀이랑 콘텐트 값 없으면 띄우는거
	if(uform.update_title.value === ""){
		alert('제목을 적어주세요');
		uform.update_title.focus();
		
		return;
	}
	
	if(uform.update_content.value === ""){
		alert('내용을 적어주세요');
		uform.update_content.focus();
		
		return;
	}
	
	uform.submit();
}

// 어째서인지 input이 아닌 다른 이름으로 하면 안된다.
function show_preview(input) {
	// input.files는 선택된 파일 읽는거. [0]은 첫번째 파일을 가리킴.(여러개 올렸을시)
    if (input.files && input.files[0]) {
    	// new FileReader();는 파일 읽을수 있는 코드
        var reader = new FileReader();
     	// onload 파일이 읽혔을떄 발생하는 이벤트 핸들러
        reader.onload = function(e) {
            document.getElementById('preview').src = e.target.result;
        };
	    const delf = document.getElementById('delf');
	    delf.value = "추가";
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById('preview').src = "";
	    delf.value = "";
    }
}

// 파일 지우는 코드
// 바깥에다 delcount설정 안하면, 함수 실행하면서 0으로 리셋됨.
function delfile() {
    const fileInput = document.getElementById('update_file');
    const preview = document.getElementById('preview');
    const delf = document.getElementById('delf');
    
    // 파일 입력 필드 리셋
    fileInput.value = "";
    
    // 미리보기 이미지 지우기
    preview.src = "../SaveUploads/logo.png";
    delf.value = "삭제";
}

</script>
</head>
<body>
<div class="wrap">
<form id="updateF" name="updateF" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/UpdateReviewAction.rev_bo">
<div class="img_wrap">
	<%if(dto.getNewFile() != null){ %>
		<img alt="미리보기 이미지입니다." id="preview" src="../SaveUploads/<%=dto.getNewFile()%>"/><br>
	<%} else { %>
		<img alt="미리보기 이미지입니다." id="preview" src="../SaveUploads/logo.png"/><br>
	<%} %>
</div>
<div class="file_btn">
	<!-- 어째서인지 this가 아닌 다른걸로 하면 안된다. -->
	<input type="file" id="update_file" name="update_file" onchange="show_preview(this);">
	<button type="button" onclick="delfile()" class="clearbtn">파일 지우기</button><br>
</div>
	<input type="hidden" name="update_num" value="<%=dto.getNum()%>">
	<input type="hidden" name="bfOrifile" value="<%=dto.getOriFile()%>">
	<input type="hidden" name="bfTfile" value="<%=dto.getNewFile()%>">
	<input type="hidden" id="delf" name="delf" value="">
	<input type="text" name = "update_title" placeholder="제목을 넣어주세요" value="<%=dto.getTitle()%>" class="up_title"><br>
	<textarea name = "update_content" placeholder="내용을 넣어주세요" class="up_cont"><%=dto.getContent() %></textarea>
	<div class="btn_wrap">
		<input type="button" value = "수정하기" onclick ="sendReview()" class="ubtn">
	</div>
</form>
</div>
</body>
</html>
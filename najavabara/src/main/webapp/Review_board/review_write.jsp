<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../rev_common/loginCheck.jsp" %>
<%@ include file="../common/menu.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<style type="text/css">
	body {
		margin: auto !important;
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
	
	.in_title {
        height: 100px;
        width: 900px;
        font-size: 50px;
        border: none;
        border-bottom: 2px solid #ddd;
        padding: 0 20px;
    }

    .in_cont {
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
	
    .sbtn {
    	color: #fff;
		border: none;
		border-radius: 20px;
		background: #0d6efd;
		padding: 20px 30px;
		font-size: 15px;
    }
</style>
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
    crossorigin="anonymous">
<script type="text/javascript">
function sendReview() {
    const rform = document.writeF;
    // console.log와 비슷하지만 dir은 객체를 출력하는거다
    console.dir(rform);

    // 타이틀이랑 콘텐트 값 없으면 띄우는거
    if(rform.input_title.value === ""){
        alert('제목을 적어주세요');
        rform.input_title.focus();
        
        return;
    }
    
    if(rform.input_content.value === ""){
        alert('내용을 적어주세요');
        rform.input_content.focus();
        
        return;
    }
    
    rform.submit();
}

function show_file(input) {
    // input.files는 선택된 파일 읽는거. [0]은 첫번째 파일을 가리킴.(여러개 올렸을시)
    if (input.files && input.files[0]) {
        // new FileReader();는 파일 읽을수 있는 코드
        var reader = new FileReader();
        // onload 파일이 읽혔을떄 발생하는 이벤트 핸들러
        reader.onload = function(e) {
            document.getElementById('preview').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    } else {
        document.getElementById('preview').src = "";
    }
}

function delfile() {
    const fileInput = document.getElementById('input_file');
    const preview = document.getElementById('preview');
    
    // 파일 입력 필드 리셋
    fileInput.value = "";
    
    // 미리보기 이미지 지우기
    preview.src = "../SaveUploads/logo.png";
}
</script>
</head>
<body>
<div class="wrap">
<!-- enctype이 파일첨가 허용한단 거 -->
	<form name="writeF" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/WriteReviewAction.rev_bo">
	    <div class="img_wrap">
		    <img alt="미리보기 이미지입니다." id="preview" src="../SaveUploads/logo.png"/>
	    </div>
	    <div class="file_btn">
		    <input type="file" id="input_file" name="input_file" onchange="show_file(this);" class="in_file">
		    <button type="button" onclick="delfile()" class="clearbtn">파일 지우기</button><br>
	    </div>
	    <input type="text" name="input_title" placeholder="제목을 적어주세요" class="in_title"><br>
	    <textarea name="input_content" placeholder="내용을 적어주세요" class="in_cont"></textarea>
	    <div class="btn_wrap">
	    	<input type="button" value="글쓰기" onclick="sendReview()" class="sbtn">
	    </div>
	</form>
</div>
</body>
</html>

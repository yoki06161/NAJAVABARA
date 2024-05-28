<%@ page import="proj.dao.HBoardDAO"%>
<%@ page import="proj.dto.HBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");

    String snum = request.getParameter("num");
    int num = Integer.parseInt(snum);

    HBoardDTO dto = new HBoardDTO();
    dto.setNum(num);

    HBoardDAO dao = new HBoardDAO();
    dto = dao.selectView(dto);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정하기</title>
<style type="text/css">
   	#wrap {
   		max-width: 1200px;
   		margin: 0 auto;
   		}
   	form {
   		margin-top: 50px;
   	}
    .up_title {
        height: 30px;
        width: 800px;
        font-size: 17px;
        border: 1px solid #cfcfcf;
   		border-radius: 10px;
   		margin-bottom: 20px;
   		padding: 3px 10px;
    }

    .up_cont {
        width: 1000px;
        height: 250px;
        font-size: 15px;
        resize: none;
        margin-bottom: 20px;
    }
    
    .img_wrap img {
        max-width: 900px;
        max-height: 900px;
        border: 1px solid #cfcfcf;
        object-position: center;
        margin-bottom: 20px;
    }
    
    .ubtn {
        color: #555;
        border: 1px solid #cfcfcf;
        border-radius: 5px;
        padding: 3px 10px;
        font-size: 15px;
    }
</style>
<script type="text/javascript">
    function sendReview() {
        const uform = document.getElementById('updateF');

        if (uform.update_title.value === "") {
            alert('제목을 적어주세요');
            uform.update_title.focus();
            return;
        }

        if (uform.update_content.value === "") {
            alert('내용을 적어주세요');
            uform.update_content.focus();
            return;
        }

        uform.submit();
    }

    function show_preview(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('preview').src = e.target.result;
            };
            reader.readAsDataURL(input.files[0]);
            document.getElementById('delf').value = "추가";
        } else {
            document.getElementById('preview').src = "";
            document.getElementById('delf').value = "";
        }
    }
</script>
</head>
<body>
<div id="wrap">
<form id="updateF" name="updateF" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/updateFileProc.hob">
    <input type="hidden" name="update_num" value="<%=dto.getNum()%>">
    <input type="hidden" name="existing_orifile" value="<%=dto.getOrifile()%>">
    <input type="hidden" name="existing_newfile" value="<%=dto.getNewfile()%>">
    제목 : <input type="text" name="update_title" placeholder="제목을 넣어주세요" value="<%=dto.getTitle()%>" class="up_title"><br>
    <div class="img_wrap">
        <% if (dto.getNewfile() != null) { %>
            <img alt="미리보기 이미지입니다." id="preview" src="../Uploads/images/<%=dto.getNewfile()%>"/><br>
        <% } %>
    </div>
    <textarea name="update_content" placeholder="내용을 넣어주세요" class="up_cont"><%=dto.getContent() %></textarea>
    <input type="file" id="update_file" name="update_file" onchange="show_preview(this);">
    <input type="button" value="수정하기" onclick="sendReview()" class="ubtn">
</form>
</div>
</body>
</html>

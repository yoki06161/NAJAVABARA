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
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
    .up_title {
        font-size: 17px;
        border-radius: 10px;
        margin-bottom: 20px;
    }

    .up_cont {
        font-size: 15px;
        resize: none;
        margin-bottom: 20px;
    }
    
    .img_wrap img {
        max-width: 100%;
        max-height: 400px;
        border: 1px solid #cfcfcf;
        object-position: center;
        margin-bottom: 20px;
    }
    
    .ubtn {
        border-radius: 5px;
        padding: 10px 20px;
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
<div class="container mt-5">
<form id="updateF" name="updateF" enctype="multipart/form-data" method="post" action="<%=request.getContextPath()%>/updateFileProc.hob">
    <input type="hidden" name="update_num" value="<%=dto.getNum()%>">
    <input type="hidden" name="existing_orifile" value="<%=dto.getOrifile()%>">
    <input type="hidden" name="existing_newfile" value="<%=dto.getNewfile()%>">
    <div class="mb-3">
        <label for="update_title" class="form-label">제목</label>
        <input type="text" name="update_title" id="update_title" class="form-control up_title" placeholder="제목을 넣어주세요" value="<%=dto.getTitle()%>">
    </div>
    <div class="img_wrap mb-3">
        <% if (dto.getNewfile() != null) { %>
            <img alt="미리보기 이미지입니다." id="preview" src="../Uploads/images/<%=dto.getNewfile()%>" class="img-fluid"/>
        <% } %>
    </div>
    <div class="mb-3">
        <label for="update_content" class="form-label">내용</label>
        <textarea name="update_content" id="update_content" class="form-control up_cont" placeholder="내용을 넣어주세요" rows="6"><%=dto.getContent() %></textarea>
    </div>
    <div class="mb-3">
        <label for="update_file" class="form-label">이미지 업로드 [현재 파일 : <%=dto.getOrifile()%>]</label>
        <input type="file" id="update_file" name="update_file" class="form-control" onchange="show_preview(this);">
    </div>
    <input type="button" value="수정하기" onclick="sendReview()" class="btn btn-primary ubtn">
</form>
</div>
</body>
</html>

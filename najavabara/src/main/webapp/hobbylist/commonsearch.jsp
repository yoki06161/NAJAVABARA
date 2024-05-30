<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // 서버에서 searchField의 값을 가져옴
    String searchField = request.getParameter("searchField");
%>
<style>
.mb-0{
	display:flex;
}
</style>
<form class="d-flex justify-content-between align-items-center px-3" method="get">
    <div class="form-group search_word mb-0" style="display=:flex;">
        <select name="searchField" class="form-control">
            <option value="title" <%= "title".equals(searchField) ? "selected" : "" %>>제목</option>
            <option value="content" <%= "content".equals(searchField) ? "selected" : "" %>>내용</option>
            <option value="id" <%= "id".equals(searchField) ? "selected" : "" %>>작성자ID</option>
        </select>
        <input type="text" name="searchWord" class="form-control">
        <button type="submit" class="btn btn-primary" style="width:150px;">검색</button>
    </div>
    <div class="my_write_File">
		<%
            if(session.getAttribute("id") != null) {
        %>
        <a href="myFile.hob" class="btn btn-success">내가 작성한 글</a>
        <%} %>
        <a href="writeFile.hob" class="btn btn-success">글 작성하기</a>
    </div>
</form>
<hr>
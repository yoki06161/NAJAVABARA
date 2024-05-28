<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // 서버에서 searchField의 값을 가져옴
    String searchField = request.getParameter("searchField");
%>
<style>
	form {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 20px;
	}
	select {
		height: 30px;
	}
	input[type="text"] {
		width: 230px;
		padding-left: 7px;
		padding-right: 7px;
		margin: 0 10px;
	}
	input[type="submit"] {
		width: 60px;
	}
	.search_word{
		display: flex;
		align-items: center;
	}
	hr {
		margin: 20px 0;
	}
</style>
<form method="get">
	<div class="search_word">
		<select name="searchField">
	    	<option value="title" <%= "title".equals(searchField) ? "selected" : "" %>>제목</option>
	    	<option value="content" <%= "content".equals(searchField) ? "selected" : "" %>>내용</option>
	    	<option value="id" <%= "id".equals(searchField) ? "selected" : "" %>>작성자ID</option>
		</select>
	    <input type="text" name="searchWord">
	    <input type="submit" value="검색">
    </div>
    <div class="writeFile">
		<a href="writeFile.hob"><input type="button" value="글 작성하기"></a>
	</div>
</form>
<hr>
<%@page import="mvc.dto.BoardDTO"%>
<%@page import="mvc.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int num = Integer.parseInt(request.getParameter("num"));
    BoardDAO dao = new BoardDAO();
    BoardDTO post = dao.selectById(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title>Edit Post</title>
</head>
<body>
    <h1>Edit Post</h1>
    <form action="updatePost.po" method="post">
        <input type="hidden" name="num" value="<%= post.getNum() %>">
        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" value="<%= post.getTitle() %>" required><br>
        <label for="content">Content:</label><br>
        <textarea id="content" name="content" rows="5" cols="40" required><%= post.getContent() %></textarea><br>
        <label for="id">Writer:</label><br>
        <input type="text" id="id" name="id" value="<%= post.getId() %>" required><br>
        <input type="submit" value="Update">
    </form>
    <a href="../index.jsp">Home</a> | <a href="list.po">View Posts</a>
</body>
</html>
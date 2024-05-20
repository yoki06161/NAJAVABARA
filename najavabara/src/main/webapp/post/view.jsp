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
   <title>View Post</title>
</head>
<body>
    <h1>View Post</h1>
    <table border="1">
        <tr>
            <th>Title</th>
            <td><%= post.getTitle() %></td>
        </tr>
        <tr>
            <th>Content</th>
            <td><%= post.getContent() %></td>
        </tr>
        <tr>
            <th>Writer</th>
            <td><%= post.getId() %></td>
        </tr>
        <tr>
            <th>Created Date</th>
            <td><%= post.getPostdate() %></td>
        </tr>
        <tr>
            <th>Visit Count</th>
            <td><%= post.getVisitcount() %></td>
        </tr>
    </table>
    <a href="edit.po?num=<%= post.getNum() %>">Edit</a> | 
    <a href="list.po">Back to List</a>
</body>
</html>
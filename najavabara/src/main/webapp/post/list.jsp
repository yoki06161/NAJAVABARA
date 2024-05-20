<%@page import="mvc.dto.BoardDTO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="mvc.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    // 게시글 목록을 조회하여 request에 저장
    BoardDAO dao = new BoardDAO();
    String searchField = request.getParameter("searchField"); 
    String searchWord = request.getParameter("searchWord");
    Map<String, String> map = new HashMap<>();
    map.put("searchField", searchField);
    map.put("searchWord", searchWord);
    List<BoardDTO> boardLists = dao.selectList(map);
    int totalCount = dao.selectCount(map);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Posts</title>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
    }
    table, th, td {
        border: 1px solid black;
    }
    th, td {
        padding: 10px;
        text-align: left;
    }
</style>
</head>
<body>
    <h1>Posts</h1>
    <table border="1">
        <tr>
            <th>Title</th>
            <th>Content</th>
            <th>Writer</th>
            <th>Created Date</th>
            <th>Visit Count</th>
            <th>Actions</th>
        </tr>
        <%
            for (BoardDTO post : boardLists) {
        %>
            <tr>
                <td><a href="view.po?num=<%= post.getNum() %>"><%= post.getTitle() %></a></td>
                <td><%= post.getContent() %></td>
                <td><%= post.getId() %></td>
                <td><%= post.getPostdate() %></td>
                <td><%= post.getVisitcount() %></td>
                <td>
                    <a href="edit.po?num=<%= post.getNum() %>">Edit</a>
                </td>
            </tr>
        <%
            }
        %>
    </table>
    <a href="../index.jsp">Home</a> | <a href="write.po">Write a Post</a>
</body>
</html>
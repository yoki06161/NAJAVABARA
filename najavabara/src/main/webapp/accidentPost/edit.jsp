<%@page import="mvc.dto.accidentBoardDTO"%>
<%@page import="mvc.dao.accidentBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <title>Edit Post</title>
 <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
 
</head>
<body>
<%

    int num = Integer.parseInt(request.getParameter("num"));
    accidentBoardDAO dao = new accidentBoardDAO();
    accidentBoardDTO post = dao.selectById(num);
%>
  <div class="container mt-5">
    <h1 class= "mb-4">글수정</h1>
    <div class = "card">
    	<div class = card-body>
  		 <form action="updatePost.acc" method="post" class="needs-validation" novalidate>
                    <input type="hidden" name="num" value="<%= post.getNum() %>">
                    <div class="form-group">
                        <label for="title">Title:</label>
                        <input type="text" id="title" name="title" class="form-control" value="<%= post.getTitle() %>" required>
                        <div class="invalid-feedback">Title is required.</div>
                    </div>
                    <div class="form-group">
                        <label for="content">Content:</label>
                        <textarea id="content" name="content" class="form-control" rows="5" required><%= post.getContent() %></textarea>
                        <div class="invalid-feedback">Content is required.</div>
                    </div>
                    <div class="form-group">
                        <label for="id">Writer:</label>
                        <input type="text" id="id" name="id" class="form-control" value="<%= post.getId() %>" required>
                        <div class="invalid-feedback">Writer's name is required.</div>
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
  	  </div>
    </div>
    <a href="../index.jsp">Home</a> | <a href="list.acc">View Posts</a>
    </div>
</body>
</html>
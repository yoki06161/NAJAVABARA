<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>Write a Post</title>
</head>
<body>
    <h1>Write a Post</h1>
    <form action="submitPost.po" method="post">
        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" required><br>
        <label for="content">Content:</label><br>
        <textarea id="content" name="content" rows="5" cols="40" required></textarea><br>
        <label for="id">Writer:</label><br>
 		<input type="text" id="id" name="id" value="<%= session.getAttribute("userId") %>" readonly required><br>
        <input type="submit" value="Submit">
    </form>
    <a href="../index.jsp">Home</a> | <a href="list.po">View Posts</a>
</body>
</html>
<%@page import="mvc.dto.friendBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="mvc.dto.CommentDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="mvc.dao.friendBoardDAO" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 댓글</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%@ include file="../index.jsp" %>
    <div class="container mt-5">
        <h1 class="mb-4">내 댓글</h1>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">번호</th>
                    <th scope="col">제목</th>
                    <th scope="col">댓글 내용</th>
                    <th scope="col">작성일</th>
                    <th scope="col">작성자</th>
                    <th scope="col">삭제</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<CommentDTO> commentList = (List<CommentDTO>) request.getAttribute("commentList");
                                                List<friendBoardDTO> postList = (List<friendBoardDTO>) request.getAttribute("postList");
                                                friendBoardDAO postDAO = new friendBoardDAO();
                                                if (commentList == null || commentList.isEmpty()) {
                %>
                <tr>
                    <td colspan="6">작성한 댓글이 없습니다.</td>
                </tr>
                <%
                } else {
                                    for (CommentDTO comment : commentList) {
                                        // 포스트 리스트(postList)에서 해당 댓글의 포스트를 찾아서 출력
                                        friendBoardDTO post = postDAO.findPostByComment(comment, postList);
                %>
                <tr>
                    <td><%= comment.getCommentNum() %></td>
                    <td><a href="viewPost.po?num=<%=post.getNum()%>" style="color: black;"><%=post.getTitle()%></a></td>
                    <td><%= comment.getComment() %></td>
                    <td><%= comment.getRegDate() %></td>
                    <td><%= comment.getWriter() %></td>
                    <td>
                        <form method="post" action="deleteComment.do">
                            <input type="hidden" name="commentNum" value="<%= comment.getCommentNum() %>">
                            <button type="submit" class="btn btn-danger">삭제</button>
                        </form>
                    </td>
                </tr>
                <% 
                    }
                }
                %>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
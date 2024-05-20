<%@page import="mvc.dto.friendBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<friendBoardDTO> postList = (List<friendBoardDTO>) request.getAttribute("postList");
int totalCount = (int) request.getAttribute("totalCount");
int pageNum = (int) request.getAttribute("pageNum"); // 현재 페이지 번호 가져오기
int pageSize = 10; // 페이지당 표시할 글의 수
int totalPage = (int) Math.ceil((double) totalCount / pageSize); // 전체 페이지 수 계산
int start = (pageNum - 1) * pageSize;
int end = Math.min(start + pageSize, totalCount);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>내 작성글</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
    .pagination {
        justify-content: center;
    }
</style>
</head>
<body>
    <%@ include file="../index.jsp"%>
    <div class="container mt-5">
        <h1 class="mb-4">내 작성글</h1>
        <form method="get">
            <div class="form-row mb-3">
                <div class="col-auto">
                    <select class="form-control" name="searchField">
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="col">
                    <input type="text" class="form-control" name="searchWord">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">검색</button>
                </div>
            </div>
        </form>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col" style="width: 10%;">번호</th>
                    <th scope="col" style="width: 50%;">제목</th>
                    <th scope="col" style="width: 15%;">작성자</th>
                    <th scope="col" style="width: 10%;">조회수</th>
                    <th scope="col" style="width: 15%;">작성일</th>
                </tr>
            </thead>
            <tbody>
                <%
                if (postList == null || postList.isEmpty()) {
                %>
                <tr>
                    <td colspan="5">데이터가 없습니다!</td>
                </tr>
                <%
                } else {
                                for (int i = start; i < end; i++) {
                                    friendBoardDTO post = postList.get(i);
                %>
                <tr>
                    <td><%=post.getNum()%></td>
                    <td><a href="viewPost.po?num=<%=post.getNum()%>" style="color: black;"><%=post.getTitle()%></a>
                    <a href="viewPost.po?num=<%=post.getNum()%>" style="color: red;">
                        <%
                        if (post.getCommentCount() > 0) {
                        %> [<%=post.getCommentCount()%>] <%
                        }
                        %>
                    </a>
                    </td>
                    <td><%=post.getId()%></td>
                    <td><%=post.getVisitcount()%></td>
                    <td><%=post.getPostdate()%></td>
                </tr>
                <% 
                }
                }
                %>
            </tbody>
        </table>
        <div class="text-center">
            <ul class="pagination">
                <li class="page-item <%=pageNum==1?"disabled":""%>"><a class="page-link" href="myPost.do?pageNum=<%=pageNum-1%>">이전</a></li>
                <% for(int i=1; i<=totalPage; i++) { %>
                <li class="page-item <%=pageNum==i?"active":""%>"><a class="page-link" href="myPost.do?pageNum=<%=i%>"><%=i%></a></li>
                <% } %>
                <li class="page-item <%=pageNum==totalPage?"disabled":""%>"><a class="page-link" href="myPost.do?pageNum=<%=pageNum+1%>">다음</a></li>
            </ul>
        </div>
        <div class="text-right">
            <a href="writeForm.po" class="btn btn-primary">글 작성</a>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
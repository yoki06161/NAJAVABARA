<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="mvc.dto.accidentBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<title>게시판</title>
</head>
<body>
    <div class="container mt-4">
        <h1>사건사고 게시판</h1>
        <div class="d-flex justify-content-end align-items-center mt-3 w-100">
            <form action="list.acc" method="get" class="w-100">
                <!-- 사용자에게 입력 받는 검색어 필드 -->
                <div class="input-group w-100">
                    <input type="text" name="searchWord" class="form-control" placeholder="제목으로 검색" value="<%=request.getParameter("searchWord")%>">
                    <!-- 검색할 필드를 'title'로 고정하는 hidden 필드 추가 -->
                    <input type="hidden" name="searchField" value="title">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-primary">검색</button>
                    </div>
                </div>
            </form>
        </div>

        <%
        List<accidentBoardDTO> boardLists = (List<accidentBoardDTO>) request.getAttribute("boardLists");
                Integer currentPage = (Integer) request.getAttribute("page");  // 변수명 변경 및 null 처리
                Integer totalPages = (Integer) request.getAttribute("totalPages");
                String searchField = (String) request.getAttribute("searchField");
                String searchWord = (String) request.getAttribute("searchWord");
                
                // Null 값을 처리하여 기본값을 설정
                if (currentPage == null) {
                    currentPage = 1;
                }
                if (totalPages == null) {
                    totalPages = 1;
                }
        %>

        <%
        // 게시글 목록 출력
                if (boardLists != null && !boardLists.isEmpty()) {
                    int count = 0;
        %>
        <div class="row mt-3">
            <%
            for (accidentBoardDTO post : boardLists) {
                            if (count > 0 && count % 2 == 0) {
            %>
        </div>
        <div class="row mt-3">
            <%
            }
            %>
            <div class="col-md-6 mb-3">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">
                            <a href="view.acc?num=<%=post.getNum()%>"><%=post.getTitle()%></a>
                        </h5>
                        <p class="card-text">
                            <small class="text-muted">작성자: <%=post.getId()%> / 작성일: <%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(post.getPostdate())%></small>
                        </p>
                        <p class="card-text">
                            <small class="text-muted">조회수: <%=post.getVisitcount()%></small>
                        </p>
                    </div>
                </div>
            </div>
            <%
            count++;
            }
            %>
        </div>
        <%
        } else {
        %>
        <p class="mt-3">검색 결과가 없습니다.</p>
        <%
        }
        %>
        
        <!-- 페이지네이션 -->
        <nav aria-label="Page navigation" class="mt-3">
            <ul class="pagination justify-content-center">
                <% if (currentPage > 1) { %>
                <li class="page-item">
                    <a class="page-link" href="list.acc?page=<%= currentPage - 1 %>&searchField=<%=searchField%>&searchWord=<%=searchWord%>">이전</a>
                </li>
                <% } %>
                <% for (int i = 1; i <= totalPages; i++) { %>
                <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                    <a class="page-link" href="list.acc?page=<%= i %>&searchField=<%=searchField%>&searchWord=<%=searchWord%>"><%= i %></a>
                </li>
                <% } %>
                <% if (currentPage < totalPages) { %>
                <li class="page-item">
                    <a class="page-link" href="list.acc?page=<%= currentPage + 1 %>&searchField=<%=searchField%>&searchWord=<%=searchWord%>">다음</a>
                </li>
                <% } %>
            </ul>
        </nav>

        <div class="mt-3">
            <a href="write.acc" class="btn btn-primary">글 쓰기</a> 
            <a href="index.jsp" class="btn btn-secondary">홈</a>
        </div>
    </div>
</body>
</html>

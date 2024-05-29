<%@page import="dto.PageDTO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="dto.BoardDTO"%>
<%@page import="java.util.List"%>
<%@page import="dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
		pageEncoding="UTF-8" %>
<%
    List<BoardDTO> boardLists = (List<BoardDTO>) request.getAttribute("boardLists");
    PageDTO p = (PageDTO) request.getAttribute("paging");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>자유게시판</title>
</head>
<body>
<%@ include file="../common/menu.jsp" %>
<hr>
<h2>자유게시판</h2>
<form method="get">
    <table border="1" width="90%">
        <tr>
            <td>
                <select name="searchField">
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                </select>
                <input type="text" name="searchWord">
                <input type="submit" value="Search">
            </td>
        </tr>
    </table>
</form>
<table border="1" width="90%">
    <tr>
        <td colspan="5">&nbsp;<b>전체 : <%= p.getTotal() %> / 현재 페이지 : <%= p.getPageNum() %></b></td>
    </tr>
    <tr>
        <th width="10%">번호</th>
        <th width="50%">제목</th>
        <th width="10%">작성자</th>
        <th width="10%">조회수</th>
        <th width="20%">작성날짜</th>
    </tr>
    <% if (boardLists == null || boardLists.isEmpty()) { %>
        <tr><td colspan="5">&nbsp;<b>Data Not Found!!</b></td></tr>
    <% } else { %>
        <% for (BoardDTO bbs : boardLists) { %>
            <tr align="center">
                <td><%= bbs.getNum() %></td>
                <td align="left">&nbsp;
                    <a href="view.bo?num=<%= bbs.getNum() %>"><%= bbs.getTitle() %></a>
                </td>
                <td><%= bbs.getId() %></td>
                <td><%= bbs.getVisitcount() %></td>
                <td><%= bbs.getPostdate() %></td>
            </tr>
        <% } %>
    <% } %>
    <tr>
        <td colspan="5">
            <% if (p.isPrev()) { %><a href="listPage.bo?pageNum=<%= p.getStartPage() - 1 %>">[Prev]</a><% } %>
            <% for (int i = p.getStartPage(); i <= p.getEndPage(); i++) { %>
                <% if (i == p.getPageNum()) { %>
                    <b>[<%= i %>]</b>
                <% } else { %>
                    <a href="listPage.bo?pageNum=<%= i %>">[<%= i %>]</a>
                <% } %>
            <% } %>
            <% if (p.isNext()) { %><a href="listPage.bo?pageNum=<%= p.getEndPage() + 1 %>">[Next]</a><% } %>
        </td>
    </tr>
    <tr>
        <td colspan="5" align="right">
            <a href="write.bo">[글 작성]</a> |
            <a href="writeFile.bo">[Write File]</a>&nbsp;&nbsp;&nbsp;
        </td>
    </tr>
</table>
</body>
</html>

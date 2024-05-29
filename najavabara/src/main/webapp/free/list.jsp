<%@ page import="dto.BoardDTO"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
List<BoardDTO> boardLists = (List<BoardDTO>) request.getAttribute("boardLists");
int totalCount = (int) request.getAttribute("totalCount");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유게시판</title>

<style type="text/css">
    a {
        text-decoration: none;
        color: black;
    }
    .search_bar {
        height: 30px;
        width: 300px;
        font-size: 20px;
        padding: 0 10px;
    }
    table {
        width: 90%;
        border-collapse: collapse;
        margin: 0 auto; /* 테이블을 수평으로 가운데 정렬 */
    }
    table, th, td {
        border: 1px solid #ddd; /* 테두리 추가 */
        text-align: center; /* 모든 셀의 텍스트를 가운데 정렬 */
    }
    th, td {
        padding: 8px;
    }
    th {
        background-color: #f2f2f2;
        border-bottom: 2px solid black; /* 테이블 헤더 하단에 검은색 선 추가 */
    }
    td {
        border-bottom: 1px solid black; /* 테이블 데이터 셀 하단에 검은색 선 추가 */
    }
    .action-links {
        text-align: right;
        margin: 20px 5%;
    }
    h2 {
        text-align: center;
    }
    .search-bar-container {
        text-align: center;
        margin-bottom: 20px;
    }
</style>
</head>

<body>
    <%@ include file="../common/menu.jsp"%>
    <h2>자유게시판</h2>
    <div class="search-bar-container">
        <form method="get">
            <select name="searchField">
                <option value="title">제목</option>
                <option value="content">내용</option>
            </select>
            <input type="text" name="searchWord">
            <input type="submit" value="Search">
        </form>
    </div>
    <table>
        <tr>
            <td colspan="5">&nbsp;<b>전체 : <%=totalCount%></b></td>
        </tr>
        <tr>
            <th width="10%">번호</th>
            <th width="50%">제목</th>
            <th width="15%">작성자</th>
            <th width="10%">조회수</th>
            <th width="15%">작성일자</th>
        </tr>
        <%
        if (boardLists.isEmpty()) {
        %>
        <tr>
            <td colspan="5">&nbsp;<b>Data Not Found!!</b></td>
        </tr>
        <%
        } else {
        %>
        <%
        for (BoardDTO bbs : boardLists) {
        %>
        <tr>
            <td><%=bbs.getNum()%></td>
            <td align="center">&nbsp; <a href="view.free?num=<%=bbs.getNum()%>"><%=bbs.getTitle()%></a></td>
            <td><%=bbs.getId()%></td>
            <td><%=bbs.getVisitcount()%></td>
            <td><%=bbs.getPostdate()%></td>
        </tr>
        <%
        }
        %>
        <%
        }
        %>
    </table>
    <div class="action-links">
        <a href="write.free">[글작성]</a> | 
        <a href="writeFile.free">[Write File]</a>&nbsp;&nbsp;&nbsp;
    </div>
</body>
</html>

<%@ page import="dao.BoardDAO"%>
<%@ page import="dto.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
    // param
    BoardDTO dto = (BoardDTO) request.getAttribute("dto");
    String id = (String) request.getSession().getAttribute("id"); // 세션에 'id'가 저장되어 있다고 가정
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board View</title>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
        margin: 0 auto; /* 테이블을 수평으로 가운데 정렬 */
    }
    .table td, .table th {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: center; /* 테이블 내용 가운데 정렬 */
    }
    .table th {
        padding-top: 12px;
        padding-bottom: 12px;
        background-color: #f2f2f2;
    }
    h2 {
        text-align: center; /* 제목 가운데 정렬 */
    }
    .left-align {
        text-align: left; /* 왼쪽 정렬 */
        margin-bottom: 10px;
    }
    .center-align {
        text-align: center; /* 가운데 정렬 */
    }
</style>
<script>
    function del(num) {
        const input = confirm("정말 삭제하시겠습니까?");
        if (input) {
            location.href = "<%= request.getContextPath() %>/board/deleteProc.free?num=" + num;
        } else {
            alert('삭제를 취소했습니다');
            return;
        }
    }
</script>
</head>
<body>
    <%@ include file="../common/menu.jsp"%>
    <form name="viewForm" method="post" action="<%=request.getContextPath() %>/free/viewProc.free">
        <h2>자유게시물 상세보기</h2>
        <table class="table table-bordered mb-5">
            <tr>
                <td>번호</td>
                <td><%= dto.getNum() %></td>
                <td>아이디</td>
                <td><%= dto.getId() %>(<%= dto.getName() %>)</td>
            </tr>
            <tr>
                <td>작성일자</td>
                <td><%= dto.getPostdate() %></td>
                <td>조회수</td>
                <td><%= dto.getVisitcount() %></td>
            </tr>
            <tr>
                <td>제목</td>
                <td colspan="3"><%= dto.getTitle() %></td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="3"><%= dto.getContent() %></td>
            </tr>
        </table>
    </form>
    <br>
    <div class="left-align">
        <a href="<%= request.getContextPath() %>/free/list.free">[게시물 목록]</a>
    </div>
    <div class="center-align">
        <% 
        if (id != null && id.equals(dto.getId())) {
        %>
        <a href="<%= request.getContextPath() %>/free/update.free?num=<%=dto.getNum() %>" class="btn btn-primary">게시물 수정</a>
        <button class="btn btn-outline-primary" onclick="del(<%=dto.getNum() %>)">게시물 삭제</button>
        <%     
        }
        %>
    </div>
</body>
</html>

<%@page import="java.util.List"%>
<%@page import="proj.cto.HBoardCTO"%>
<%@page import="proj.dao.HBoardDAO"%>
<%@page import="proj.dto.HBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hobbyboard/view.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    function del(num) {
        const input = confirm("게시물을 정말 삭제 할까요?");
        if (input) {
            $.ajax({
                url: "<%=request.getContextPath()%>/hobbyboard/deleteProc.hob",
                type: "GET",
                data: { num: num },
                success: function(response) {
                    alert('게시물이 삭제되었습니다.');
                    window.location.href = "<%=request.getContextPath()%>/hobbyboard/listFile.hob";
                },
                error: function(xhr, status, error) {
                    alert('게시물 삭제에 실패했습니다.');
                }
            });
            $.ajax({
                url: "<%=request.getContextPath()%>/hobbyboard/deleteAllCommentsProc.hob",
                type: "GET",
                data: { num: num }
                });
        } else {
            alert('게시물 삭제를 취소 했습니다.');
        }
    }
    
    function delComment(numx) {
        console.log("numx: " + numx); // 이 값이 제대로 출력되는지 확인
        const input = confirm("댓글을 정말 삭제 할까요?");
        if (input) {
            $.ajax({
                url: "<%=request.getContextPath()%>/hobbyboard/deleteCommentsProc.hob",
                type: "GET",
                data: { numx: numx },
                success: function(response) {
                    alert('댓글이 삭제되었습니다.');
                    location.reload(); // 댓글 삭제 후 페이지를 새로고침하여 변경 사항을 반영합니다.
                },
                error: function(xhr, status, error) {
                    alert('댓글 삭제에 실패했습니다.');
                }
            });
        } else {
            alert('댓글 삭제를 취소 했습니다.');
        }
    }

</script>
<script type="text/javascript">
    function validateForm() {
        const form = document.writeForm;
        console.dir(form); // input
        if (form.content.value === "") {
            alert('content 필수값 입니다.');
            form.content.focus();
            return false;
        }
        form.submit();
    }
</script>

<style>
   	#wrap {
    		max-width: 1200px;
    		margin: 0 auto;
    	}
    h2 {
    	padding-left: 20px;
    }
    p {
    	padding-left: 20px;
    }
    table {
    	width: 1200px;
    }
    table:nth-child(1){
    	padding: 20px;
    }
    img {
    	max-width: 900px;
    	max-height: 900px;
    }
    .content {
    	padding: 30px 50px;
    	min-height: 130px;
    	height: 130px;
    	border: 1px solid #cfcfcf;
    }
    td {
    	height: 30px;
    	padding: 10px 20px;
    }
    th {
    	padding: 20px 0;
    }
    button {
    	height: 29px;
    	line-height: 15px;
    	padding: 5px 10px;
    	color: #555;
    	border: 1px solid #cfcfcf;
    	border-radius: 4px;
    	font-weight: 700;
    	font-size: 15px;
    }
    textarea {
    	resize: none;
    	width: 800px;
    	min-height: 130px;
    	height: 130px;
    	border: 1px solid #cfcfcf;
    	border-radius: 20px;
    	padding: 20px;
    }
    input[type=submit]{
    	height: 29px;
    	line-height: 15px;
    	padding: 3px 10px;
    	color: #555;
    	border: 1px solid #cfcfcf;
    	border-radius: 4px;
    	margin-left: 20px;
    	
    }
    #comments td {
    	padding: 5px 0;
    }
    #comments .com_content {
    	border: 1px solid #cfcfcf;
    	border-radius: 10px;
    	height: 70px;
    	min-height: 50px;
    	padding: 10px 20px;
    	width: 800px;
    }
</style>
</head>
<body>
<div id="wrap">
<%@ include file="../common/menu.jsp" %>
<%
HBoardDTO dto = (HBoardDTO)session.getAttribute("dto");
    if (dto != null) {
%>    
<h2><%=dto.getTitle()%></h2>
<table>
    <tr>
        <td align="left">작성자 ID : <%=dto.getId()%></td>
        <td align="right"><%=dto.getPostdate()%></td>
    </tr>
        
    <tr><td></td><td align="right">조회수 <%=dto.getVisitcount()%></td></tr>
    <tr>
    <%if(dto.getOrifile() != null){ %>
		<th colspan="2">
		<img alt="<%=dto.getOrifile()%>" src="../Uploads/images/<%=dto.getNewfile()%>">
		</th>
		<%}%>
	</tr>
    <tr><td colspan="2" class="content"><%=dto.getContent()%></td></tr>
    <tr>
        <td>
            <button type="button" onclick="location.href='listFile.hob';">전체 게시물 보기</button>
            	        <%
	            			String hobby = "";
		            		switch (dto.getHobby()) {
		            	    case "gardening":
		            	        hobby = "원예";
		            	        break;
		            	    case "art":
		            	    	hobby = "아트";
		            	        break;
		            	    case "puzzle":
		            	    	hobby = "퍼즐";
		            	        break;
		            	    case "collection":
		            	    	hobby = "수집";
		            	        break;
		            	    case "reading":
		            	    	hobby = "독서";
		            	        break;
		            	    case "exercise":
		            	    	hobby = "운동";
		            	        break;
		            	    case "photo":
		            	    	hobby = "포토";
		            	        break;
		            	    case "handmade":
		            	    	hobby = "수공예";
		            	        break;
		            	    case "instrument":
		            	    	hobby = "악기연주";
		            	        break;
		            	    case "astronomical":
		            	    	hobby = "천체관측";
		            	        break;
		            	    default:
		            	    	hobby = "취미";
		            		}
	            		%>
            <button type="button" onclick="location.href='<%=dto.getHobby()%>.hob';"><%=hobby%> 게시물 보기</button>
        </td>
            <%
            if(session.getAttribute("id") != null && session.getAttribute("id").equals(dto.getId())) {
            %>
            	<td align="right">
	            <button type="button" onclick="location.href='updateFile.jsp?num=<%=dto.getNum()%>';">게시물 수정하기</button>
	            <button type="button" onclick="del('<%=dto.getNum()%>');">게시물 삭제하기</button>
				</td>
			<%
			}
			%>
    </tr>
</table>


<br><br>
<h2>댓글</h2>
<%
String id = (String)session.getAttribute("id");
%>
<form name="writeForm" method="post" action="<%=request.getContextPath()%>/writeCommentProc.hob">
    <input type="hidden" id="num" name="num" value="<%=dto.getNum()%>">
    <table>
        <tr>
            <td>ID : <%=id%></td>
        </tr>
        <tr>
            <td><textarea id="content" name="content" placeholder="한 번 작성한 댓글은 수정이 불가합니다."></textarea></td>
        </tr>
    </table>
    <input type="submit" value="작성하기" onclick="validateFileForm();">
</form>


<br><br>
<h2>댓글 리스트</h2>
    <div id="comments">
        <%
        	List<HBoardCTO> commentLists = (List<HBoardCTO>)session.getAttribute("commentLists");
            if (commentLists != null && !commentLists.isEmpty()) {
        %>
        <table>
            <tr><td>&nbsp;<b>전체 : <%=commentLists.size()%></b></td></tr>
            <%
            for (HBoardCTO cbs : commentLists) {
            %>
            <tr>
                <td>ID : <%= cbs.getId() %></td>
            </tr>
            <tr>
                <td class="com_content" width="80%"><%= cbs.getContent() %></td>
                <td align="center"><%= cbs.getPostdate() %></td>
            </tr>
                <tr>
                    <td>
                        <% if(session.getAttribute("id") != null && session.getAttribute("id").equals(cbs.getId())) { %>
                           <button type="button" onclick="delComment('<%= cbs.getNumx() %>');">댓글 삭제</button>
                        <% } %>
                    </td>
                </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>댓글이 없습니다.</p>
        <% } %>
    </div>
<% } else { %>
    <p>게시물을 찾을 수 없습니다.</p>
<% } %>
</div>

</body>
</html>

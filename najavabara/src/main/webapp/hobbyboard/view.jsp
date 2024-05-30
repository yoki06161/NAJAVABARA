<%@page import="java.util.List"%>
<%@page import="dto.HobbyCommentDTO"%>
<%@page import="dao.HobbyBoardDAO"%>
<%@page import="dto.HobbyBoardDTO"%>
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

</style>
</head>
<body>
<div class="container">
    <%@ include file="../common/menu.jsp" %>
    <%
    HobbyBoardDTO dto = (HobbyBoardDTO)session.getAttribute("dto");
                if (dto != null) {
    %>    
    <div class="row mt-4">
        <div class="col-md-12">
            <h2 class="text-center"><%=dto.getTitle()%></h2>
            <div class="row text-center">
                <div class="col-md-6">작성자 ID : <%=dto.getId()%></div>
                <div class="col-md-6 text-right"><%=dto.getPostdate()%></div>
            </div>
            <div class="row mt-2 text-center">
                <div class="col-md-6"></div>
                <div class="col-md-6 text-right">조회수 <%=dto.getVisitcount()%></div>
            </div>
            <div class="row mt-2 text-center">
                <div class="col-md-12 ">
                    <%
                    if(dto.getOrifile() != null){
                    %>
                    <img class="img-fluid" alt="<%=dto.getOrifile()%>" src="../Uploads/images/<%=dto.getNewfile()%>">
                    <%
                    }
                    %>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-md-12">
                    <p class="border rounded p-3" style="height: 150px;"><%=dto.getContent()%></p>
                </div>
            </div>
            <div class="row mt-4">
                <div class="col-md-6">
                    <button type="button" class="btn btn-primary" onclick="location.href='listFile.hob';">전체 게시물 보기</button>
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
                    <button type="button" class="btn btn-primary" onclick="location.href='<%=dto.getHobby()%>.hob';"><%=hobby%> 게시물 보기</button>
                </div>
                <div class="col-md-6 text-right">
                    <%
                    if(session.getAttribute("id") != null && session.getAttribute("id").equals(dto.getId())) {
                    %>
                    <button type="button" class="btn btn-info" onclick="location.href='updateFile.jsp?num=<%=dto.getNum()%>';">게시물 수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="del('<%=dto.getNum()%>');">게시물 삭제하기</button>
                    <%
                    }
                    %>
                </div>
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-12">
            <h2>댓글</h2>
            <%
            if(session.getAttribute("id") != null) {
            %>
			    <form name="writeForm" method="post" action="<%=request.getContextPath()%>/writeCommentProc.hob">
			        <input type="hidden" id="num" name="num" value="<%=dto.getNum()%>">
			        <div class="form-group">
			            <textarea class="form-control" id="content" style="height: 100px;margin-bottom: 20px;resize=none;" name="content" placeholder="한 번 작성한 댓글은 수정이 불가합니다." required></textarea>
			        </div>
			        <button type="submit" class="btn btn-primary">작성하기</button>
			    </form>
			<%
			} else {
			%>
			    <div class="form-group">
			        <textarea class="form-control" id="content" style="height: 100px;margin-bottom: 20px" name="content" placeholder="로그인 후에 댓글을 작성할 수 있습니다." disabled></textarea>
			    </div>
			    <button type="button" class="btn btn-primary" onclick="loginAlert()">작성하기</button>
			    <script type="text/javascript">
			        function loginAlert() {
			            alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
			            window.location.href = "<%=request.getContextPath()%>/user/login.jsp"; // 로그인 페이지 URL로 수정
			        }
			    </script>
			<%
			}
			%>

        </div>
    </div>

    <div class="row mt-4">
        <div class="col-md-12">
            <h2>댓글 리스트</h2>
            <div id="comments">
                <%
                List<HobbyCommentDTO> commentLists = (List<HobbyCommentDTO>)session.getAttribute("commentLists");
                                                if (commentLists != null && !commentLists.isEmpty()) {
                %>
                <table class="table">
                    <tbody>
                        <tr><td>&nbsp;<b>전체 : <%=commentLists.size()%></b></td></tr>
                        <%
                        for (HobbyCommentDTO cbs : commentLists) {
                        %>
                        <tr>
                            <td>ID : <%= cbs.getId() %></td>
                            <td align="right"><%= cbs.getPostdate() %></td>
                        </tr>
                        <tr>
                            <td class="com_content border rounded" style="height:100px;margin-bottom: 10px;"><%= cbs.getContent() %></td>
                        </tr>
                        <% if(session.getAttribute("id") != null && session.getAttribute("id").equals(cbs.getId())) { %>
                        <tr>
                            <td>
                                <button type="button" class="btn btn-danger" onclick="delComment('<%= cbs.getNumx() %>');">댓글 삭제</button>
                            </td>
                        </tr>
                        <% } %>
                        <% } %>
                    </tbody>
                </table>
                <% } else { %>
                <p>댓글이 없습니다.</p>
                <% } %>
            </div>
        </div>
    </div>
    <% } else { %>
    <p>게시물을 찾을 수 없습니다.</p>
    <% } %>
</div>

</body>
</html>

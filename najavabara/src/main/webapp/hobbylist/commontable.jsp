<%@page import="proj.dto.HBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<HBoardDTO> boardLists = (List<HBoardDTO>)request.getAttribute("boardLists");

//visitcount를 위한 session
HttpSession ses = request.getSession();
HBoardDTO updatedDto = (HBoardDTO)ses.getAttribute("dto");

if (updatedDto != null) {
 for (HBoardDTO board : boardLists) {
     if (board.getNum() == updatedDto.getNum()) {
         board.setVisitcount(updatedDto.getVisitcount());
         session.removeAttribute("dto");
         break;
     }
 }
}
%>  
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>hobbyboard/listFile.jsp</title>
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
        crossorigin="anonymous">
    <style>
    	.container {
    		max-width: 1200px;
    	}
    	.grid-item {
            background-color: #dfdfdf;
            border: 1px solid #ccc;
            padding: 10px 20px;
            text-align: center;
            margin-bottom: 30px;
            width: 100%;
            border-radius: 20px;
        }
        .grid-item img {
        	max-height: 320px;
        	max-width: 100%;
        }
        .grid_item_span {
        	display: flex;
        	justify-content: space-between;
        	align-items: center;
        	margin-top: 10px;  
        }
		h2{
			font-size: 18px;
			font-weight: 700;
			margin-bottom: 10px;
		    overflow: hidden;
		    display: -webkit-box;
		    -webkit-line-clamp: 1;
		    -webkit-box-orient: vertical;
		}
		a {
			text-decoration: none;
			color: black;
		}
    </style>
</head>
<body>
	<%if(boardLists == null || boardLists.isEmpty()) {%>
	<br><br>
		<b>게시물이 없습니다.</b>
	<%} %>
	
    <div class="container mt-4">
        <div class="row">
            <% for (HBoardDTO bbs : boardLists) { %>
            	<div class="col-md-4">
            		<div class="grid-item">
                        <h2><a href="view.hob?num=<%= bbs.getNum() %>"><%= bbs.getTitle() %></a></h2>
                        <div class="grid-item_inner">
							<a href="view.hob?num=<%= bbs.getNum() %>"><img alt="<%=bbs.getOrifile() %>" src="../Uploads/images/<%=bbs.getNewfile() %>"></a>
						</div>
	                    <div class="grid_item_span">
	                    	<span><%= bbs.getId() %></span><span><%= bbs.getPostdate() %></span>
	                    </div>
                    </div>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>

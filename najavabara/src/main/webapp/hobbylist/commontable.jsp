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
    <script type="text/javascript">
    function redirectToPage(url) {
        window.location.href = url;
    }
	</script>
    <style>

     	#wrap {
     		max-width: 1200px;
     		margin: 0 auto;
     	}
    	.row {
    		flex-direction: row;
    		justify-content: space-around;
    	}
    	.row span{
    		display: flex;
    		align-items: center;
    		justify-content: flex-end;
    	}
        .grid-item {
            background-color: #efefef;
            border: 1px solid #ccc;
            padding: 10px 20px;
            text-align: center;
            margin-bottom: 30px;
            width: 380px;
            height: 410px;
            border-radius: 20px 20px 30px 30px;
        }
        .grid-item img {
        	max-height: 320px;
        	max-width: 378px;
        }
        .grid-item_inner {
            background-color: #fdfdfd;
            height: 320px;
            display: flex;
            justify-content: center;
          	align-items: center;
          	width: 378px;
          	margin-left: -20px;
          	border-top: 1px solid #ccc;
          	border-bottom: 1px solid #ccc;
        }
        .grid_item_span {
        	display: flex;
        	justify-content: space-between;
        	align-items: center;
        	margin-top: 10px;  
        }
        .grid_top_span {
        	display: flex;
        	justify-content: space-between;
        	align-items: center;
        	margin: 0 20px 5px;
        	font-size: 14px;
        }
        span {
        	font-size: 14px;
        	color: #3f3f3f;
        	
        }
        .grid_top_span span:first-child{
        	font-weight: 700;
        }
		
		h2{
			font-size: 20px;
			margin-bottom: 10px;
		    overflow: hidden;
		    /* display: -webkit-box; */
		    -webkit-line-clamp: 1;
		    -webkit-box-orient: vertical;
		}
		a {
			text-decoration: none;
			color: black;
		}
		
		b {
			padding-left: 20px;
		}
		.grid-item {
    		cursor: pointer;
		}
    .title {
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        max-width: 360px; /* 최대 너비 지정 */
    }
    </style>
</head>
<body>
<div id="wrap">
	<%if(boardLists == null || boardLists.isEmpty()) {%>
	<br><br>
		<b>게시물이 없습니다.</b>
	<%} %>
    <div class="container mt-4">
	        <div class="row">
	            <% for (HBoardDTO bbs : boardLists) { %>
	            	<div class="col-md-4">
	            		<div class="grid_top_span">
	            		<span>조회수&nbsp; <%= bbs.getVisitcount() %></span>
	            		</div>
		                <div class="grid-item" onclick="redirectToPage('<%=request.getContextPath()%>/hobbyboard/view.hob?num=<%= bbs.getNum() %>')">
		                   <h2 class="title" style="font-size:20px"><%= bbs.getTitle() %></h2>

		                    <div class="grid-item_inner">
								<img alt="<%=bbs.getOrifile() %>" src="../Uploads/images/<%=bbs.getNewfile() %>">
							</div>
		                    <div class="grid_item_span">
		                    	<span>작성자&nbsp; <%= bbs.getId() %></span><span><%= bbs.getPostdate() %></span>
		                    </div>
		                </div>
	                </div>
	            <% } %>
	        </div>
	    </div>
   	</div>
</body>
</html>

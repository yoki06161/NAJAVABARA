<%@page import="mvc.dto.friendBoardDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate"%>
<%@ page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%
List<friendBoardDTO> postLists = (List<friendBoardDTO>) request.getAttribute("postLists");
Integer totalCount = (Integer) request.getAttribute("totalCount");
int pageNum = (Integer) request.getAttribute("pageNum"); // 현재 페이지 번호 가져오기
int pageSize = 10; // 페이지당 표시할 글의 수
int totalPage = (int) Math.ceil((double) totalCount / pageSize); // 전체 페이지 수 계산
String selectedArea = (String) request.getParameter("searchArea");
if (selectedArea == null) {
	selectedArea = (String) session.getAttribute("selectedArea");
} else {
	session.setAttribute("selectedArea", selectedArea);
}
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>동네 친구 게시판</title>
<link
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
    rel="stylesheet">
<style>
.pagination {
    justify-content: center;
}

.card {
    margin-bottom: 20px;
    min-height: 220px;
}

.card-title {
    margin-bottom: 0.75rem;
}

.card-text {
    color: #6c757d;
}

.card-footer {
    background-color: #f8f9fa;
    border-top: none;
}

.card-footer a {
    color: #007bff;
}

.card-footer a:hover {
    text-decoration: none;
}

.img-square-container {
    position: relative;
    width: 100%;
    height: 100%;
    padding-bottom: 100%; /* 1:1 Aspect Ratio */
    background-color: #f8f9fa;
    overflow: hidden;
    border-radius: 15px;
}

.img-square-container img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: fill;
}

.card-title a {
    text-decoration: none;
}

.card-title a:hover {
    text-decoration: underline;
}
</style>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"
    integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
    crossorigin="anonymous"></script>
<script
    src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
    integrity="sha384-Q6E9RHvbIyZFfIAAcYDRz+2wwVrU8iVXFsTrUUsYtkMXrA+I3Eql7BRJxN4EUksd"
    crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        // 이전에 선택된 지역이 있는지 확인하고 선택된 값으로 설정
        var selectedArea = sessionStorage.getItem('selectedArea');
        if (selectedArea) {
            $('#searchArea').val(selectedArea);
            $('#boardTitle').text(selectedArea + " 동네 친구 게시판");
        } else {
            $('#boardTitle').text("동네 친구 게시판");
        }

        // 지역 선택 시 검색 폼 자동 제출
        $('#searchArea').change(function() {
            sessionStorage.setItem('selectedArea', $(this).val()); // 선택한 지역을 저장
            $('#searchForm').submit();
        });

        // 검색 폼 제출 시 세션 저장소 초기화
        $('#searchForm').submit(function() {
            sessionStorage.setItem('selectedArea', $('#searchArea').val());
        });

    });
</script>
</head>

<body>
    <%@ include file="../index.jsp"%>
    <div class="container mt-5">
        <h1 class="mb-4" id="boardTitle"><%=selectedArea != null && !selectedArea.isEmpty() ? selectedArea + " " : ""%>동네
            친구 게시판
        </h1>
        <form id="searchForm" action="friendBoard.fri" method="get">
            <div class="row mb-3">
                <div class="col-auto">
                    <select class="form-select" name="searchField">
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
            <div class="row mb-3">
                <div class="col-auto">
                    <select class="form-select" name="searchArea" id="searchArea">
                        <option value="">전체</option>
                        <option value="서울특별시">서울특별시</option>
                        <option value="대구광역시">대구광역시</option>
                        <!-- 필요한 만큼 지역 옵션을 추가 -->
                    </select>
                </div>
            </div>
        </form>
        <div class="row">
            <%
            if (postLists == null || postLists.isEmpty()) {
            %>
            <div class="col-12">
                <p>데이터가 없습니다!</p>
            </div>
            <%
            } else {
            for (int i = 0; i < postLists.size(); i++) {
                friendBoardDTO post = postLists.get(i);
                List<String> fileNames = post.getFileNames(); // 파일명 리스트 가져오기
                boolean hasFiles = fileNames != null && !fileNames.isEmpty(); // 파일이 있는지 여부 확인
                boolean isImage = false;
                if (hasFiles) {
                    for (String fileName : fileNames) {
                String[] imageExtensions = { ".jpg", ".jpeg", ".png", ".gif" };
                for (String ext : imageExtensions) {
                    if (fileName.toLowerCase().endsWith(ext)) {
                        isImage = true;
                        break;
                    }
                }
                    }
                }
                if (i % 2 == 0 && i != 0) {
            %>
        </div>
        <div class="row">
            <%
            }
            %>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-8">
                                <h5 class="card-title">
                                    <a href="viewPost.fri?num=<%=post.getNum()%>"
                                        style="color: black;" title="<%=post.getTitle()%>"> <%
                                        String title = post.getTitle();
                                        if (title.length() > 15) {
                                            title = title.substring(0, 35) + "..."; // 15자 이상이면 말줄임 처리
                                            }
                                        out.println(title);
                                        %>
                                    </a>
                                    <%
                                    if (hasFiles && !isImage) {
                                    %>
                                    <i class="far fa-file"></i>
                                    <%
                                    }
                                    %>
                                    <%
                                    if (post.getCommentCount() > 0) {
                                    %>
                                    <a href="viewPost.fri?num=<%=post.getNum()%>"
                                        style="color: red;"> [<%=post.getCommentCount()%>]
                                    </a>
                                    <%
                                    }
                                    %>
                                </h5>
                                <p class="card-text">
                                    <%=post.getId()%>
                                </p>
                                <p class="card-text">
                                    <%=post.getArea()%>
                                    ·
                                    <%
                                    LocalDateTime postDateTime = post.getPostdate().toLocalDateTime();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                    if (postDateTime.toLocalDate().isEqual(LocalDate.now())) {
                                    %>
                                    <%=postDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))%>
                                    <%
                                    } else {
                                    %>
                                    <%=postDateTime.format(formatter)%>
                                    <%
                                    }
                                    %>
                                    · 조회
                                    <%=post.getVisitcount()%>
                                </p>
                            </div>
                            <div class="col-md-4">
                                <%
                                if (isImage) {
                                %>
                                <div class="text-center img-square-container">
                                    <img src="uploads/<%=fileNames.get(0)%>"
                                        alt="<%=post.getTitle()%>" class="img-fluid">
                                </div>
                                <%
                                }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%
            }
            }
            %>
        </div>
        <div class="text-center">
            <ul class="pagination">
                <li class="page-item <%=pageNum == 1 ? "disabled" : ""%>"><a
                    class="page-link" href="friendBoard.fri?pageNum=<%=pageNum - 1%>">이전</a>
                </li>
                <%
                for (int i = 1; i <= totalPage; i++) {
                %>
                <li class="page-item <%=pageNum == i ? "active" : ""%>"><a
                    class="page-link" href="friendBoard.fri?pageNum=<%=i%>"><%=i%></a>
                </li>
                <%
                }
                %>
                <li class="page-item <%=pageNum == totalPage ? "disabled" : ""%>">
                    <a class="page-link"
                    href="friendBoard.fri?pageNum=<%=pageNum + 1%>">다음</a>
                </li>
            </ul>
        </div>
        <div class="text-end">
            <a href="writeForm.fri" class="btn btn-primary">글 작성</a>
        </div>
    </div>
</body>
</html>
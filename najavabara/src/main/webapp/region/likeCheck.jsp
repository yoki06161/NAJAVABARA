<%@page import="java.util.Map"%>
<%@page import="java.io.BufferedReader"%>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@page import="dto.RegionDTO"%>
<%@page import="dao.RegionDAO"%>
<%@page import="dao.RegionLikeDAO"%>
<%@page import="dto.RegionLikeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%
// 클라이언트로부터 전송된 id 값을 가져옴
String id = (String) session.getAttribute("id");
System.out.println(id);

// 요청 본문을 읽기
BufferedReader reader = request.getReader();
StringBuilder jsonBuilder = new StringBuilder();
String line;
while ((line = reader.readLine()) != null) {
    jsonBuilder.append(line);
}

// JSON 파싱
ObjectMapper objectMapper = new ObjectMapper();
Map<String, Object> jsonData = objectMapper.readValue(jsonBuilder.toString(), Map.class);

// "num" 파라미터 값 가져오기
int num = Integer.parseInt((String) jsonData.get("num"));
System.out.println(num);

System.out.println("아이디, 게시물번호: " + id + ", " + num);
// likeCheck
RegionLikeDTO ldto = new RegionLikeDTO();
RegionLikeDAO ldao = new RegionLikeDAO();
RegionDTO dto = (RegionDTO) request.getAttribute("dto");
RegionDAO dao = new RegionDAO();

// 좋아요를 눌렀는지 확인 -  ldto가 있으면 이미 좋아요를 눌렀다는 뜻, 없으면 안눌렀으므로 null
ldto = ldao.hasUserLiked(ldto);
int rs = 0;
if(ldto != null) {
	// 좋아요를 눌렀다면, '좋아요는 한 번만 누를 수 있습니다'
	rs = 1;
} else {
	rs = 0;
	// 좋아요 테이블에 데이터 삽입
	ldto = new RegionLikeDTO(id, num);
	ldao.insertLike(ldto);
	// 게시물 좋아요 수 증가
	//dao.updateLike(dto);
}

%>
{"rs":"<%=rs %>"}

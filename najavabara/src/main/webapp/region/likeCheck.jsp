<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*, org.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="dto.RegionDTO"%>
<%@page import="dao.RegionDAO"%>
<%@page import="dao.RegionLikeDAO"%>
<%@page import="dto.RegionLikeDTO"%>
<%
response.setContentType("application/json; charset=UTF-8");

// JSON 데이터를 읽고 파싱
StringBuilder sb = new StringBuilder();
BufferedReader reader = request.getReader();
String line;
while ((line = reader.readLine()) != null) {
	sb.append(line);
}
JSONObject jsonRequest = new JSONObject(sb.toString());

String id = jsonRequest.optString("id");
int num = jsonRequest.optInt("num");
System.out.println("아이디, 게시물번호: " + id + ", " + num);

// id가 null인 경우, 잘못된 요청으로 처리하고 오류 메시지를 반환
if (id == null) {
	JSONObject json = new JSONObject();
	json.put("id", JSONObject.NULL);
	json.put("rs", "error");
	json.put("message", "로그인이 필요한 기능입니다.");
	out.print(json.toString());
	out.flush();
	RegionLikeDTO ldto = null;
	return;
}

//likeCheck
RegionLikeDAO ldao = new RegionLikeDAO();
RegionDTO dto = new RegionDTO();
RegionDAO dao = new RegionDAO();
//좋아요를 눌렀는지 확인 -  ldto가 있으면 이미 좋아요를 눌렀다는 뜻, 없으면 안눌렀으므로 null(재할당이므로 이렇게 써야함...주의!)
RegionLikeDTO ldto = ldao.hasUserLiked(new RegionLikeDTO(id, num));

//ldto가 null인 경우, 메서드 실행을 중단합니다.
if (ldto == null) {
	JSONObject json = new JSONObject();
	// 좋아요를 추가하는 로직
	ldao.insertLike(ldto);
	// 게시물 좋아요 수 증가
	//dao.updateLike(dto);
	json.put("rs", 0);
	//json.put("likeCount", likeCount);
	return; // 여기서 메서드 실행을 중단합니다.
} else if (ldto != null) {
	JSONObject json = new JSONObject();
	json.put("rs", 1);
	json.put("message", "좋아요는 게시물 당 한 번만 누를 수 있습니다");
} 
%>
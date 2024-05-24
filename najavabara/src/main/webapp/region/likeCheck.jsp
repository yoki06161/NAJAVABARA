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

String id = (String) session.getAttribute("id");
int num = jsonRequest.optInt("num");
//System.out.println("아이디, 게시물번호: " + id + ", " + num);

// id가 null인 경우, 잘못된 요청으로 처리하고 오류 메시지를 반환
if(id == null || id.isEmpty()) {
	System.out.println("null일때");
	JSONObject jsonResponse = new JSONObject();
	jsonResponse.put("rs", "error");
	jsonResponse.put("message", "로그인이 필요한 기능입니다.");
	out.print(jsonResponse.toString());
	out.flush();
} else {
	//아이디가 null이 아닌 경우에만 계속 진행
	//likeCheck
	// System.out.println("null이 아닐때");
	RegionLikeDAO ldao = new RegionLikeDAO();
	RegionDTO dto = new RegionDTO();
	RegionDAO dao = new RegionDAO();
	//좋아요를 눌렀는지 확인 -  ldto가 있으면 이미 좋아요를 눌렀다는 뜻, 없으면 안눌렀으므로 null(재할당이므로 이렇게 써야함...주의!)
	RegionLikeDTO ldto = ldao.hasUserLiked(new RegionLikeDTO(id, num));

	JSONObject jsonResponse = new JSONObject();
	if (ldto != null) {
		jsonResponse.put("rs", 1); // 좋아요를 이미 눌렀음을 나타내는 코드
		jsonResponse.put("message", "좋아요는 게시물 당 한 번만 누를 수 있습니다");
	} else {
		// 좋아요를 아직 누르지 않은 경우
		//System.out.println("아이디, 게시물번호: " + id + ", " + num);
		ldao.insertLike(new RegionLikeDTO(id, num)); // 좋아요테이블에 좋아요 정보 추가
		jsonResponse.put("rs", 0); // 좋아요를 누르지 않았고, 좋아요를 추가했음을 나타내는 코드
	}
	out.print(jsonResponse.toString());
	out.flush();
}
%>
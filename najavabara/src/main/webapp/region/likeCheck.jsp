<%@page import="dto.RegionDTO"%>
<%@page import="dao.RegionDAO"%>
<%@page import="dao.RegionLikeDAO"%>
<%@page import="dto.RegionLikeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 값 받아오기
String id = (String)session.getAttribute("id");

// likeCheck
RegionLikeDTO ldto = new RegionLikeDTO();
RegionLikeDAO ldao = new RegionLikeDAO();
RegionDAO dao = new RegionDAO();
RegionDTO dto = new RegionDTO();

// 좋아요를 눌렀는지 확인 -  ldto가 있으면 이미 좋아요를 눌렀다는 뜻, 없으면 안눌렀으므로 null
ldto = ldao.hasUserLiked(ldto);
int rs = 0;
if(ldto != null) {
	// 좋아요를 눌렀다면, '좋아요는 한 번만 누를 수 있습니다'
	rs = 1;
} else {
	rs = 0;
	// 좋아요 테이블에 데이터 삽입
	//ldao.insertLike(ldto);
	// 게시물 좋아요 수 증가
	//dao.updateLike(dto);
}
%>
{"rs":"<%=rs %>"}
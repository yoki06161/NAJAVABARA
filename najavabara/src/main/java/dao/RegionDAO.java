package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.JDBConnect;
import dto.RegionDTO;

public class RegionDAO {
	// 게시글 목록 가져오기(검색, 필터링 기능 포함)
	public List<RegionDTO> selectList(Map<String, String> map) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// select문에서만 사용

		// 필터링 여부
		boolean isFilter = false;
		if(map.get("area") != null || map.get("area") == "") {
			isFilter = true;
		}
		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}	
		// 리스트 필요없으면 이걸 삭제
		List<RegionDTO> regionList = new ArrayList<>();
		// sql 창 
		String sql = "select num, title, content, regionBoard.id, user.area, ";
		sql += "case when timestampdiff(second, postdate, current_timestamp) <60"
				+ "           then concat(timestampdiff(second, postdate, current_timestamp),'초 전')"
				+ "           when timestampdiff(minute, postdate, current_timestamp) <60"
				+ "           then concat(timestampdiff(minute, postdate, current_timestamp),'분 전')"
				+ "           when timestampdiff(hour, postdate, current_timestamp) <24"
				+ "           then concat(timestampdiff(hour, postdate, current_timestamp),'시간 전')"
				+ "           else concat(datediff(current_timestamp, postdate),'일 전')"
				+ "        end as postdate,visitcount, ofile, sfile, likes ";
		sql += "from user, regionBoard";
		sql += " where user.id = regionBoard.id";
		if(isFilter) {
			sql += " and area like ? ";
		} else if(isSearch) {
			sql += " and " + map.get("searchField") + " like ? ";
		}
//		else if(isFilter && isSearch) {
//			sql += " and area = " + map.get("area");
//			sql += " and " + map.get("searchField") + " like ? ";
//		}
		sql += " order by num desc";
		System.out.println(sql);

		try {
			// connection
			conn = JDBConnect.getConnection();

			pstmt = conn.prepareStatement(sql);
			if(isFilter) {
				pstmt.setString(1, "%" + map.get("area") + "%");
			} else if(isSearch) {
				pstmt.setString(1, "%" + map.get("searchWord") + "%");
			}
//			else if(isFilter && isSearch) {
//				pstmt.setString(1, "%" + map.get("searchWord") + "%");
//				pstmt.setString(2, map.get("area"));
//			} 
			// execute
			rs = pstmt.executeQuery(); 

			// List<DTO명>이면 if만 while로 변경
			while (rs.next()) { // id 존재
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String area = rs.getString("area");
				String postdate = rs.getString("postdate");
				int visitcount = rs.getInt("visitcount");
				String ofile = rs.getString("ofile");
				String sfile = rs.getString("sfile");
				int likes = rs.getInt("likes");
				// 생성자
				RegionDTO dto = new RegionDTO(num, title, content, id, area, postdate, visitcount, ofile, sfile, likes);
				// 리스트 필요없으면 이걸 삭제
				regionList.add(dto);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close(rs, pstmt, conn);
		}
		return regionList;
	}

	// 총 게시물 수(검색, 필터링 기능 포함)
	public int selectCount(Map<String, String> map) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;				

		int totalCount = 0;
		// 필터링 여부
		boolean isFilter = false;
		if(map.get("area") != null || map.get("area") == "") {
			isFilter = true;
		}
		// search 여부
		boolean isSearch = false;
		if(map.get("searchWord") != null && map.get("searchWord").length() != 0) {
			isSearch = true;
		}		

		String sql = "select count(num) as cnt from regionBoard, user";
		sql += " where user.id = regionBoard.id";
		if(isFilter) {
			sql += " and area like ?";
		}
		if(isSearch) {
			sql += " and " + map.get("searchField") + " like ? ";
		}
		System.out.println(sql);
		conn = JDBConnect.getConnection();

		try {
			pstmt = conn.prepareStatement(sql);
			if(isFilter) {
				pstmt.setString(1, "%" + map.get("area") + "%");
			} else if(isSearch) {
				pstmt.setString(1, "%" + map.get("searchWord") + "%");
			} 
//			else if(isFilter && isSearch) {
//				pstmt.setString(1, "%" + map.get("searchWord") + "%");
//				pstmt.setString(2, map.get("area"));
//			}
			rs = pstmt.executeQuery();

			if(rs.next()) {
				totalCount = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close(rs, pstmt, conn);
		}		

		return totalCount;
	}

	// 각 게시물 조회수 증가
	public int updateVisitcount(RegionDTO dto) {
		// 메소드 안 
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;   

		try {
			// conn
			conn = JDBConnect.getConnection();

			// sql + 쿼리창
			String sql = "update regionBoard set visitcount = visitcount + 1 ";
			sql += " where num = ?";
			pstmt = conn.prepareStatement(sql);

			// 세팅
			pstmt.setInt(1, dto.getNum());

			// execute 실행
			rs = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBConnect.close(pstmt, conn);
		}
		return rs;
	}

	// 게시물 상세 보기
	public RegionDTO selectView(RegionDTO dto) {
		// 메소드 안
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		// select문에서만 사용

		try {
			// connection
			conn = JDBConnect.getConnection();

			// sql 창- 리스트면 where ..=?부분 수정
			String sql = "select regionBoard.num, title, content, regionBoard.id, user.area, postdate, visitcount, user.name, ofile, sfile, likes ";
			sql += " from regionBoard, user";
			sql += " where regionBoard.num=? and user.id = regionBoard.id";
			pstmt = conn.prepareStatement(sql);
			// 문자니까 setString, 날짜면 setDate 등등 ...

			pstmt.setInt(1, dto.getNum());
			// execute
			rs = pstmt.executeQuery(); 

			// 있는지 판단 - 리스트면 이걸 수정 
			dto = null;
			// List<DTO명>이면 if를 while로 변경
			if (rs.next()) { // id 존재
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String area = rs.getString("area");
				String postdate = rs.getString("postdate");
				String name = rs.getString("name");
				int visitcount = rs.getInt("visitcount");
				String ofile = rs.getString("ofile");
				String sfile = rs.getString("sfile");
				int likes = rs.getInt("likes");
				// 생성자 필요에 따라 추가(리스트면 dto앞에 DTO명 붙여야함)
				dto = new RegionDTO(num, title, content, id, area, postdate, name, visitcount, ofile, sfile, likes);
				System.out.println(ofile+"과"+sfile);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBConnect.close(rs, pstmt, conn);
		}
		return dto;
	}

	// 게시물 상세 보기(사진)
	//	public RegionDTO selectViewFile(RegionDTO fdto) {
	//		// 메소드 안
	//		Connection conn = null;
	//		PreparedStatement pstmt = null;
	//		ResultSet rs = null;		// select문에서만 사용
	//
	//		try {
	//			// connection
	//			conn = JDBConnect.getConnection();
	//
	//			// sql 창
	//			String sql = "select ofile, sfile ";
	//			sql += " from regionBoard, regionFile ";
	//			sql += " where regionBoard.num=? and regionBoard.num = regionfile.postNum";
	//			pstmt = conn.prepareStatement(sql);
	//			// 문자니까 setString, 날짜면 setDate 등등 ...
	//
	//			pstmt.setInt(1, fdto.getNum());
	//			// execute
	//			rs = pstmt.executeQuery(); 
	//
	//			// 있는지 판단 - 리스트면 이걸 수정 
	//			fdto = null;
	//			// List<DTO명>이면 if를 while로 변경
	//			if (rs.next()) { // id 존재
	//				String ofile = rs.getString("ofile");
	//				String sfile = rs.getString("sfile");
	//				String postNum = rs.getString("postNum");
	//				// 생성자 필요에 따라 추가(리스트면 dto앞에 DTO명 붙여야함)
	//				fdto = new RegionDTO(ofile, sfile, postNum);
	//			} 
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		} finally {
	//			JDBConnect.close(rs, pstmt, conn);
	//		}
	//		return fdto;
	//	}

	// 게시물 등록 - 이건 rs가 필요함!!
	public int insertWrite (RegionDTO dto) {
		// 메소드 안 
		Connection conn = null;
		PreparedStatement pstmt = null;  
		int rs = 0;
		try {
			// conn
			conn = JDBConnect.getConnection();

			// sql + 쿼리창
			String sql = "insert into regionBoard(title, content, id, ofile, sfile) values(?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			// ?에 들어갈 컬럼들 세팅
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getOfile());
			pstmt.setString(5, dto.getSfile());

			// execute 실행
			rs = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBConnect.close(pstmt, conn);
		}
		return rs;
	}
	//	// 게시물 파일 등록
	//	public int insertFile(RegionDTO dto) {
	//		// 메소드 안 
	//		Connection conn = null;
	//		PreparedStatement pstmt = null;  
	//		int rs = 0;
	//		try {
	//			// conn
	//			conn = JDBConnect.getConnection();
	//
	//			// sql + 쿼리창
	//			String sql = "insert into regionFile(ofile, sfile) values(?, ?)";
	//			pstmt = conn.prepareStatement(sql);
	//
	//			// ?에 들어갈 컬럼들 세팅
	//			pstmt.setString(1, dto.getOfile());
	//			pstmt.setString(2, dto.getSfile());
	//
	//			// execute 실행
	//			rs = pstmt.executeUpdate();
	//
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}finally {
	//			JDBConnect.close(pstmt, conn);
	//		}
	//		return rs;
	//	}

	public int updateWrite(RegionDTO dto) {
		// 메소드 안 
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;   

		try {
			// conn
			conn = JDBConnect.getConnection();

			// sql + 쿼리창
			String sql = "update regionBoard set title=?, content=?, ofile=?, sfile=? where num = ?";
			pstmt = conn.prepareStatement(sql);

			// 세팅
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getOfile());
			pstmt.setString(4, dto.getSfile());
			pstmt.setInt(5, dto.getNum());

			// execute 실행
			rs = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBConnect.close(pstmt, conn);
		}
		return rs;
	}

	public int deleteWrite(RegionDTO dto) {
		// 메소드 안 
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;   

		try {
			// conn
			conn = JDBConnect.getConnection();

			// sql + 쿼리창
			String sql = "delete from regionBoard where num = ?";
			pstmt = conn.prepareStatement(sql);

			// 세팅
			pstmt.setInt(1, dto.getNum());

			// execute 실행
			rs = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBConnect.close(pstmt, conn);
		}
		return rs;
	} 

}

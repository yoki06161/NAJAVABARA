package Review_Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import common.JDBCConnect;

public class Review_BoardDAO {

	// 지역리뷰 게시물 보여주는 메소드. review_boardList.jsp에서 쓰임
	public List<Review_BoardDTO> ShowReviewBoard(Map<String, String> map) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		String sql = "select * from reviewBoard";
		List<Review_BoardDTO> reviewList = new ArrayList<Review_BoardDTO>();
		
		// 게시물 검색 여부
		boolean search = false;
		
		System.out.println("검색어 " + map.get("searching"));
		// 검색바가 널이 아니거나 길이가 0줄이 아니면 서치는 true
		if(map.get("searching") != null && map.get("searching").length() != 0) {
			System.out.println("검색 연결됨");
			search = true;
			// 띄어쓰기 중요
			sql += " where title like ?";
		}
		
		System.out.println("sql 최종 명령 " + sql);
		try {
			conn = JDBCConnect.getConnection();
		
			pstmt = conn.prepareStatement(sql);
			
			if(search) {
				// pstmt에 문자열 할당
				pstmt.setString(1, "%" + map.get("searching") + "%");
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				Date date = rs.getDate("postdate");
				int visitcount = rs.getInt("visitcount");
				String id = rs.getString("id");
				String orifile = rs.getString("originalFile");
				String newfile = rs.getString("newFile");
				int like = rs.getInt("love");
				
				Review_BoardDTO dto = new Review_BoardDTO(num, title, content, date, visitcount, id, orifile, newfile, like);
				reviewList.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		// 리스트 순서 뒤집는거
		Collections.reverse(reviewList);
		return reviewList;
	}

	// num값에 따라 내용 보여주는 코드. review_content.jsp, review_update에서 쓰임
	// list에서 누른 게시판 번호에 따라 맞는 내용 보여주는거
	public Review_BoardDTO ShowContentBynum(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from reviewBoard where num = ?";
		
		try {
			conn = JDBCConnect.getConnection();
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			rs = pstmt.executeQuery();
			
			// dto값 초기화
			dto = null;
			
			if(rs.next()) {
				int num = rs.getInt("num");
				String title = rs.getString("title");
				String content = rs.getString("content");
				Date date = rs.getDate("postdate");
				int count = rs.getInt("visitcount");
				String id = rs.getString("id");
				String orifile = rs.getString("originalFile");
				String newfile = rs.getString("newFile");
				int like = rs.getInt("love");
				dto = new Review_BoardDTO(num, title, content, date, count, id, orifile, newfile, like);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(rs, pstmt, conn);
		}
		return dto;
	}
	
	// 글쓰기 메소드. ReviewWriteAction에서 에서 지정된 dto를 받는다.
	public int WriteReview(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		String sql = "insert into reviewBoard(title, content, id, originalFile, newFile) values(?,?,?,?,?)";

		try {
			conn = JDBCConnect.getConnection();
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getOriFile());
			pstmt.setString(5, dto.getNewFile());
			
			rs = pstmt.executeUpdate();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
		return rs;
	}
	
	// 게시물 수정 메소드. ReviewUpdateAction에서 지정된 dto를 받는다.
	public int UpdateReview(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = JDBCConnect.getConnection();
		
			String sql = "update reviewBoard set title = ?, content = ?, originalFile = ?, newFile = ? where num = ?";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getOriFile());
			pstmt.setString(4, dto.getNewFile());
			pstmt.setInt(5, dto.getNum());
			
			rs = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(pstmt, conn);
		}
		
		return rs;
	}

	// 게시물 삭제 메소드.
	public int DeleteReview(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = JDBCConnect.getConnection();
			
			String sql = "delete from reviewBoard where num = ?";
			
			pstmt = conn.prepareStatement(sql);
			System.out.println("삭제 num테스트 " + dto.getNum());
			pstmt.setInt(1, dto.getNum());
			
			rs = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnect.close(pstmt, conn);
		}
		
		
		return rs;
	}
	
	// 조회수 메소드
	public int CountVisitors(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = JDBCConnect.getConnection();
			
			String sql = "update reviewBoard set visitcount = visitcount + 1 where num = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNum());
			
			rs = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
		
		return rs;
	}
	
	// 좋아요 증가 메소드.
	public int CountLikes(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = JDBCConnect.getConnection();
			
			String sql = "update reviewBoard set love = love + 1 where num = ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			
			System.out.println("좋아요 dao연결 넘" + dto.getNum());
			
			rs = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnect.close(pstmt, conn);
		}
		
		return rs;
	}
}

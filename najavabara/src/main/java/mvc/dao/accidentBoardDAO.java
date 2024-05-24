package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mvc.common.JDBCConnect;
import mvc.dto.accidentBoardDTO;

public class accidentBoardDAO {

	public List<accidentBoardDTO> selectAll() {
		List<accidentBoardDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM accidentBoard";
		try (Connection conn = JDBCConnect.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(mapToBoardDTO(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error during fetching all posts: " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	public List<accidentBoardDTO> selectList(Map<String, Object> map) {
		List<accidentBoardDTO> boardLists = new ArrayList<>();
		String searchField = (String) map.get("searchField");
		String searchWord = (String) map.get("searchWord");
		int offset = (int) map.get("offset");
		int limit = (int) map.get("limit");

		String sql = "SELECT * FROM accidentBoard";

		if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
			sql += " WHERE " + searchField + " LIKE ?";
		}

		sql += " ORDER BY postdate DESC LIMIT ? OFFSET ?";

		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			int paramIndex = 1;
			if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + searchWord + "%");
			}
			pstmt.setInt(paramIndex++, limit);
			pstmt.setInt(paramIndex, offset);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					accidentBoardDTO dto = new accidentBoardDTO();
					dto.setNum(rs.getInt("num"));
					dto.setTitle(rs.getString("title"));
					dto.setContent(rs.getString("content"));
					dto.setPostdate(rs.getTimestamp("postdate"));
					dto.setVisitcount(rs.getInt("visitcount"));
					dto.setId(rs.getString("id"));
					boardLists.add(dto);
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL error in selectList: " + e.getMessage());
			e.printStackTrace();
		}
		return boardLists;
	}

	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		String searchField = (String) map.get("searchField");
		String searchWord = (String) map.get("searchWord");

		String sql = "SELECT COUNT(*) FROM accidentBoard";
		if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
			sql += " WHERE " + searchField + " LIKE ?";
		}

		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			if (searchField != null && !searchField.isEmpty() && searchWord != null && !searchWord.isEmpty()) {
				pstmt.setString(1, "%" + searchWord + "%");
			}

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCount;
	}

	public accidentBoardDTO selectById(int num) {
		accidentBoardDTO dto = null;
		String sql = "SELECT * FROM accidentBoard WHERE num = ?";

		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, num);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					dto = new accidentBoardDTO(rs.getInt("num"), rs.getString("title"), rs.getString("content"),
							rs.getString("id"));
					dto.setPostdate(rs.getTimestamp("postdate"));
					dto.setVisitcount(rs.getInt("visitcount"));
					dto.setLikes(rs.getInt("likes"));
					dto.setDislikes(rs.getInt("dislikes"));
					dto.setPostId(rs.getInt("post_id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public void insertPost(accidentBoardDTO dto) {

		String sql = "INSERT INTO accidentBoard (title, content, id, postdate, visitcount) VALUES (?, ?, ?, NOW(), 0)";

		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updatePost(accidentBoardDTO dto) {
		String sql = "UPDATE accidentBoard SET title = ?, content = ?, id = ? WHERE num = ?";

		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId());
			pstmt.setInt(4, dto.getNum());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 조회수증가
	public int updateVisitCount(accidentBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			// 데이터베이스 연결
			conn = JDBCConnect.getConnection();

			// SQL 쿼리 작성
			String sql = "UPDATE accidentBoard SET visitcount = visitcount + 1 WHERE num = ?";
			pstmt = conn.prepareStatement(sql);

			// 쿼리 파라미터 설정
			pstmt.setInt(1, dto.getNum());

			// 쿼리 실행
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 자원 해제
			JDBCConnect.close(pstmt, conn);
		}
		return result;
	}

	// 좋아요 싫어요기능
	public void updateLikes(int postId, boolean isLike) {
		String sql = isLike ? "UPDATE accidentBoard SET likes = likes + 1 WHERE num = ?"
				: "UPDATE accidentBoard SET dislikes = dislikes + 1 WHERE num = ?";

		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postId);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows == 0) {
				System.out.println("No rows affected. Check if the post ID is correct.");
			}
		} catch (SQLException e) {
			System.err.println("Error updating likes/dislikes: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public int getLikes(int postId) {
		String sql = "SELECT likes FROM accidentBoard WHERE num = ?";
		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("likes");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; // 데이터베이스 오류 발생 시 0 반환
	}

	public int getDislikes(int postId) {
		String sql = "SELECT dislikes FROM accidentBoard WHERE num = ?";
		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("dislikes");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0; // 데이터베이스 오류 발생 시 0 반환
	}

	public List<accidentBoardDTO> selectByTitle(String title) {
		List<accidentBoardDTO> list = new ArrayList<>();
		String sql = "SELECT * FROM accidentBoard WHERE title LIKE ?";
		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + title + "%");
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					accidentBoardDTO post = new accidentBoardDTO();
					post.setNum(rs.getInt("num"));
					post.setTitle(rs.getString("title"));
					post.setContent(rs.getString("content"));
					post.setPostdate(rs.getTimestamp("postdate"));
					post.setVisitcount(rs.getInt("visitcount"));
					post.setId(rs.getString("id"));
					list.add(post);
				}
			}
		} catch (SQLException e) {
			System.err.println("SQL error in selectByTitle: " + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	private accidentBoardDTO mapToBoardDTO(ResultSet rs) throws SQLException {
		accidentBoardDTO post = new accidentBoardDTO();
		post.setNum(rs.getInt("num"));
		post.setTitle(rs.getString("title"));
		post.setContent(rs.getString("content"));
		post.setPostdate(rs.getTimestamp("postdate"));
		post.setVisitcount(rs.getInt("visitcount"));
		post.setId(rs.getString("id"));
		return post;
	}

}
package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.common.JDBCConnect;
import mvc.dto.accidentCommentDTO;

public class accidentCommentDAO {

	// 댓글 추가 메서드
	public boolean addComment(accidentCommentDTO comment) {
		String sql = "INSERT INTO accidentBoardComments (postNum, writer, comment, regDate) VALUES (?, ?, ?, NOW())";
		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, comment.getPostNum());
			pstmt.setString(2, comment.getWriter());
			pstmt.setString(3, comment.getComment());

			// 디버그 로그 추가
			System.out.println("Executing SQL: " + pstmt.toString());

			int result = pstmt.executeUpdate();
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 댓글 목록 조회 메서드
	public List<accidentCommentDTO> getComments(int postNum) {
		List<accidentCommentDTO> comments = new ArrayList<>();
		String sql = "SELECT * FROM accidentBoardComments WHERE postNum = ? ORDER BY regDate DESC";
		try (Connection conn = JDBCConnect.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postNum);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					accidentCommentDTO comment = new accidentCommentDTO();
					comment.setCommentNum(rs.getInt("commentNum"));
					comment.setPostNum(rs.getInt("postNum"));
					comment.setWriter(rs.getString("writer"));
					comment.setComment(rs.getString("comment"));
					comment.setRegDate(rs.getTimestamp("regDate"));
					comments.add(comment);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
}

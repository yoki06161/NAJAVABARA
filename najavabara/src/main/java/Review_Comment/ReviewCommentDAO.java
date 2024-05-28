package Review_Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import Review_Board.Review_BoardDTO;
import User.Conn;

public class ReviewCommentDAO {
	
	// 댓글 쓰기 메소드. ReviewCoWrite에서 불림
	public int WriteComments(ReviewCommentDTO cto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		conn = Conn.getConn();
		
		try {
			String sql = "insert into reviewcomments(num, comment, id) values(?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cto.getNum());
			pstmt.setString(2, cto.getComment());
			pstmt.setString(3, cto.getId());;
			
			System.out.println("댓쓰기의 넘" + cto.getNum());
			System.out.println("댓쓰기의 코멘트" + cto.getComment());
			System.out.println("댓쓰기의 아이디" + cto.getId());
			
			rs = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conn.upClose(pstmt, conn);
		}
		
		return rs;
	}

	// num값 따라 보이는 댓글 다르게.(게시판에 따라 보이는 댓글다르게). review_content에서 불림
	public List<ReviewCommentDTO> ShowCommentbyNum(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from reviewcomments where num = ?";
		List<ReviewCommentDTO> commentlist = new ArrayList<ReviewCommentDTO>();
		
		try {
			conn = Conn.getConn();
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			System.out.println("where 넘값 확인용" + num);
			rs = pstmt.executeQuery();
			
			// 값 초기화 시켜주는거지만 이게 없으면 content화면에서 null값이라고 뜨고, 있으면 오류뜨고(댓글이 없는상태에선)
			// cto = null;
			
			while(rs.next()) {
				String comment = rs.getString("comment");
				Date date = rs.getDate("postdate");
				String id = rs.getString("id");
				String tdate = rs.getString("postdate");

				ReviewCommentDTO cto = new ReviewCommentDTO(num, comment, date, id, tdate);
				commentlist.add(cto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conn.allClose(rs, pstmt, conn);
		}
		// 리스트 역순
		Collections.reverse(commentlist);
		return commentlist;
	}

	// 댓글 수정 메소드. reviewCoUpdate에서 불린다.
	public int UpdateComments(ReviewCommentDTO cto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		String sql = "update reviewcomments set comment = ? where postdate = ?";
		
		try {
			conn = Conn.getConn();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cto.getComment());
			pstmt.setString(2, cto.getTdate());
			rs = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conn.upClose(pstmt, conn);
		}
		
		return rs;
	}
	
	// 댓글 삭제 메소드. ReviewCoDelete에서 불림
	public int DeleteComments(ReviewCommentDTO cto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = Conn.getConn();
		
			String sql = "delete from reviewcomments where postdate = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cto.getTdate());
			
			rs = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conn.upClose(pstmt, conn);
		}
		return rs;
	}
	
	// 게시물 삭제시 게시물 댓글도 삭제되게
	public int DeleteAllComments(Review_BoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs = 0;
		
		try {
			conn = Conn.getConn();
			
			String sql = "delete from reviewcomments where num = ?";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNum());
			
			rs = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conn.upClose(pstmt, conn);
		}
		return rs;
	}
}

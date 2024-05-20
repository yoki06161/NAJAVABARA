package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.common.JDBCConnect;
import mvc.dto.CommentDTO;

public class CommentDAO {
    public boolean insertComment(CommentDTO comment) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;
        
        try {
            conn = JDBCConnect.getConnection();
            String sql = "INSERT INTO comments (postNum, comment, writer) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, comment.getPostNum());
            pstmt.setString(2, comment.getComment());
            pstmt.setString(3, comment.getWriter());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }
        
        return result;
    }

    public List<CommentDTO> getCommentsByPostNum(int postNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommentDTO> commentList = new ArrayList<>();

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT * FROM comments WHERE postNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postNum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int commentNum = rs.getInt("commentNum");
                String comment = rs.getString("comment");
                String writer = rs.getString("writer");
                String regDate = rs.getString("regDate");

                CommentDTO commentDTO = new CommentDTO(commentNum, postNum, comment, writer, regDate);
                commentList.add(commentDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return commentList;
    }
    
    public boolean deleteComment(int commentNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean deleted = false;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "DELETE FROM comments WHERE commentNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentNum);

            int result = pstmt.executeUpdate();
            
            // 삭제 성공 시 result 값이 1이 됨
            if (result == 1) {
                deleted = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }

        return deleted;
    }
    
    public List<CommentDTO> getCommentsByUserId(String userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommentDTO> commentList = new ArrayList<>();

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT * FROM comments WHERE writer = ? ORDER BY commentNum DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int commentNum = rs.getInt("commentNum");
                int postNum = rs.getInt("postNum");
                String comment = rs.getString("comment");
                String regDate = rs.getString("regDate");

                CommentDTO commentDTO = new CommentDTO(commentNum, postNum, comment, userId, regDate);
                commentList.add(commentDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(rs, pstmt, conn);
        }

        return commentList;
    }

}
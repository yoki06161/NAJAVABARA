package mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.common.JDBCConnect;
import mvc.dto.CommentDTO;
import mvc.dto.ReplyDTO;

public class ReplyDAO {
    public boolean insertReply(ReplyDTO reply) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "INSERT INTO replies (commentNum, reply, writer) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, reply.getCommentNum());
            pstmt.setString(2, reply.getReply());
            pstmt.setString(3, reply.getWriter());

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
    
    public List<ReplyDTO> getRepliesByCommentNum(int commentNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ReplyDTO> replyList = new ArrayList<>();

        try {
            conn = JDBCConnect.getConnection();
            String sql = "SELECT * FROM replies WHERE commentNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, commentNum);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ReplyDTO reply = new ReplyDTO();
                reply.setReplyNum(rs.getInt("replyNum"));
                reply.setCommentNum(rs.getInt("commentNum"));
                reply.setReply(rs.getString("reply"));
                reply.setWriter(rs.getString("writer"));
                reply.setRegDate(rs.getString("regDate"));
                
                replyList.add(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnect.close(pstmt, conn);
        }

        return replyList;
    }
    
    public boolean deleteReply(int replyNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            conn = JDBCConnect.getConnection();
            String sql = "DELETE FROM replies WHERE replyNum = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, replyNum);

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

}
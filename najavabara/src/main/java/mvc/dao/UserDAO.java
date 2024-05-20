package mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mvc.common.JDBCConnect;
import mvc.dto.UserDTO;

public class UserDAO {

    // Create a new post
    public boolean createPost(UserDTO user) {
        String sql = "INSERT INTO accidentBoard (title, content, id) VALUES (?, ?, ?)";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getTitle());
            pstmt.setString(2, user.getContent());
            pstmt.setString(3, user.getId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read all posts
    public List<UserDTO> getAllPosts() {
        List<UserDTO> posts = new ArrayList<>();
        String sql = "SELECT * FROM accidentBoard";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setNum(rs.getInt("num"));
                user.setTitle(rs.getString("title"));
                user.setContent(rs.getString("content"));
                user.setPostdate(rs.getTimestamp("postdate"));
                user.setVisitcount(rs.getInt("visitcount"));
                user.setId(rs.getString("id"));
                posts.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // Read a single post by num
    public UserDTO getPost(int num) {
        UserDTO user = null;
        String sql = "SELECT * FROM accidentBoard WHERE num = ?";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, num);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserDTO();
                    user.setNum(rs.getInt("num"));
                    user.setTitle(rs.getString("title"));
                    user.setContent(rs.getString("content"));
                    user.setPostdate(rs.getTimestamp("postdate"));
                    user.setVisitcount(rs.getInt("visitcount"));
                    user.setId(rs.getString("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Update a post
    public boolean updatePost(UserDTO user) {
        String sql = "UPDATE accidentBoard SET title = ?, content = ?, visitcount = ? WHERE num = ?";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getTitle());
            pstmt.setString(2, user.getContent());
            pstmt.setInt(3, user.getVisitcount());
            pstmt.setInt(4, user.getNum());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a post
    public boolean deletePost(int num) {
        String sql = "DELETE FROM accidentBoard WHERE num = ?";
        try (Connection conn = JDBCConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, num);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

package mvc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnect {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // JDBC 드라이버 클래스 이름
            String driver = "com.mysql.cj.jdbc.Driver";
            // 데이터베이스 연결 URL
            String url = "jdbc:mysql://54.253.205.165:3306/teampro?serverTimezone=UTC";
            // 데이터베이스 사용자 이름
            String user = "accident";
            // 데이터베이스 비밀번호
            String pw = "accident";

            // JDBC 드라이버 클래스를 메모리에 로드
            Class.forName(driver);

            // 데이터베이스 연결 설정
            conn = DriverManager.getConnection(url, user, pw);

            // 연결 성공 메시지 출력
            System.out.println("Connection successful!");

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn; // Connection 객체를 반환
    }

    // ResultSet, PreparedStatement, Connection 객체를 닫는 메서드
    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close(); // ResultSet 객체 닫기
            if (pstmt != null) pstmt.close(); // PreparedStatement 객체 닫기
            if (conn != null) conn.close(); // Connection 객체 닫기
        } catch (SQLException e) {
            // 예외가 발생한 경우 스택 트레이스를 출력
            e.printStackTrace();
        }
    }

    // PreparedStatement, Connection 객체를 닫는 메서드
    public static void close(PreparedStatement pstmt, Connection conn) {
        try {
            if (pstmt != null) pstmt.close(); // PreparedStatement 객체 닫기
            if (conn != null) conn.close(); // Connection 객체 닫기
        } catch (SQLException e) {
            // 예외가 발생한 경우 스택 트레이스를 출력
            e.printStackTrace();
        }
    }
}
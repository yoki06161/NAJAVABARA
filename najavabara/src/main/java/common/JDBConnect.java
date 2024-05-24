package common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBConnect {

	public static Connection getConnection() {
		Connection conn = null;
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://54.253.205.165:3306/teampro?serverTimezone=UTC";
			String user = "region";
			String pw = "region";

			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			System.out.println("conn ok!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return conn;
	}

	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {

		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt, Connection conn) {

		try {
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

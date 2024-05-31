package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conn {

	// db 연결 메소드
	public static Connection getConn() {
		
		Connection conn = null;
		
		try {
			// 원래 jdbc:mysql://localhost:3306/teampro?serverTimezone=UTC;로만 써왔지만 localhost가아니라 서버 주소(54.253.205.165) 써야함. 뒤에 :3306은 동일해서 넣음
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://54.153.195.68:3306/teampro?serverTimezone=UTC";
			String user = "review";
			String pw = "review";
		
			System.out.println("");
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, pw);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		return conn;
	}
	
	// null이 아닐 경우 다 닫는 메소드?
	public static void allClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
			try {
				if(rs != null)rs.close();
				if(pstmt != null)pstmt.close();
				if(conn != null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	// 쿼리 업데이트 떄 쓰이는 close. executeUpdate는 int rs니까.
		public static void upClose(PreparedStatement pstmt, Connection conn) {
				try {
					if(pstmt != null)pstmt.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	
}

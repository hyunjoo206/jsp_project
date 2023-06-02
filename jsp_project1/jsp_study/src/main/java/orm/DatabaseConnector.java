package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	//싱글톤 방식
	private static DatabaseConnector dbc = new DatabaseConnector();
	//url, user, password
	private Connection conn = null; //db관련 접속 규격
	//jdbcDriver
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	//mysql URL
	private String jdbcUrl = "jdbc:mysql://localhost/javadb";
	
	private DatabaseConnector(){
		try {
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(jdbcUrl, "javauser", "mysql");
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			System.out.println("jdbc driver를 찾을 수 없습니다.");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("연결정보가 정확하지 않습니다.");
			e.printStackTrace();
		}
	}
	
	//싱글톤 방식
	public static DatabaseConnector getInstance() {
		return dbc;
	}
	
	public Connection getConnection() {
		return conn;
	}
	
}

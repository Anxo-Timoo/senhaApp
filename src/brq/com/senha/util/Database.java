package brq.com.senha.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

	public Connection getStrConnection() throws SQLException {
		
	 //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dataapp?useTimezone=true&serverTimezone=UTC&useSSL=false", "admin","JadSuporte@2021");
	 Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/senhaapp?useTimezone=true&serverTimezone=UTC&useSSL=false", "root","root");
		return connection;
	}
	
}

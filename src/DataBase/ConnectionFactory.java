package DataBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javafx.application.Platform;

public class ConnectionFactory {

	private static Logger logger = LogManager.getLogger();

	public static Connection createConnection() throws Exception{
		Connection conn = null;

		try{
			MysqlDataSource ds = new MysqlDataSource();
			ds.setURL("jdbc:mysql://easel2.fulgentcorp.com:3306/dky407");
			ds.setUser("dky407");
			ds.setPassword("change_me");
			
			conn = ds.getConnection();
			
		}
		catch( SQLException e){
			e.printStackTrace();
			logger.fatal("Could not establish connection\n");
			Platform.exit();
		}
		return conn;
		
	}
}

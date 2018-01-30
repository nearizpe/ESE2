package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class AuthorTableGateway {
	public ArrayList<AuthorModel> getAuthors(){
		ArrayList<AuthorModel> authors = new ArrayList<AuthorModel>();
		//creating database variables and entering account info 
		Statement stmt = null;
		ResultSet rs = null;
		MysqlDataSource ds = new MysqlDataSource();
		ds.setURL("jdbc:mysql://easel2.fulgentcorp.com/phpmyadmin");
		ds.setUser("fis637");
		ds.setPassword("BVJcwouxMz0sv48zrttX");
		
		//creating connection
		Connection conn;
		try {
			conn = ds.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//fetch data
		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM authorDB");
		}catch(SQLException e){
			System.out.println("DB QUERY ERROR");
			
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		//closing connection
		/*
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return authors;
	}
}
 
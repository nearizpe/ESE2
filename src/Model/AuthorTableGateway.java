package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class AuthorTableGateway {
	public ArrayList<Author> getAuthors() {
		ArrayList<Author> authors = new ArrayList<Author>();
		// creating database variables and entering account info
		String cur;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		MysqlDataSource ds = new MysqlDataSource();
		//ds.setURL("jdbc:mysql://easel2.fulgentcorp.com/phpmyadmin");
		ds.setURL("jdbc:mysql://easel2.fulgentcorp.com:3306/fis637");
		ds.setUser("fis637");
		ds.setPassword("BVJcwouxMz0sv48zrttX");

		// creating connection
		Connection conn;
		try {
			conn = ds.getConnection();
			// fetch data
			try {
				stmt = conn.prepareStatement("SELECT * FROM AuthorDB");
				rs = stmt.executeQuery();
				
				while (rs.next()) {
					Author temp = new Author();
					temp.setId(rs.getInt("id"));
					temp.setFirstName(rs.getString("first_name"));
					temp.setLastName(rs.getString("last_name"));
					temp.setDateOfBirth(rs.getDate("dob").toLocalDate());
					temp.setGender(rs.getString("gender"));
					temp.setWebSite(rs.getString("last_name"));
					authors.add(temp);
				}
			} catch (SQLException e) {
				System.out.println("DB QUERY ERROR");

			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return authors;
	}
}

package DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import Model.Author;

public class AuthorTableGateway {
	
	private Connection conn;
	
	public AuthorTableGateway(Connection conn){
		this.conn = conn;
	}
	
	public ArrayList<Author> getAuthors() {
		ArrayList<Author> authors = new ArrayList<Author>();
		// creating database variables and entering account info
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From AuthorDB");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Author temp = new Author(this);
				temp.setId(rs.getInt("id"));
				temp.setFirstName(rs.getString("first_name"));
				temp.setLastName(rs.getString("last_name"));
				temp.setDateOfBirth(rs.getDate("dob").toLocalDate());
				temp.setGender(rs.getString("gender"));
				temp.setWebSite(rs.getString("web_site"));
				authors.add(temp);
			}
		} catch (SQLException e) {
			System.out.println("DB QUERY ERROR");
			e.printStackTrace();

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
		return authors;
	}
	
	public void updateAuthor (Author author) throws Exception{
		PreparedStatement st = null;
		java.util.Date rdob = null;
		java.sql.Date newD = null;
		try {
			st = conn.prepareStatement("update AuthorDB set first_name = ?,last_name = ?,dob = ?,gender = ?, Web_site = ? where id = ?");//change database and add more updates for stuff like lastname
			st.setString(1, author.getFirstName().getValue());
			st.setString(2, author.getLastName().getValue());
			rdob = new SimpleDateFormat("yyyy-MM-dd").parse(author.getDateOfBirth().getValue().toString());
			newD = new Date(rdob.getTime());
			st.setDate(3, newD);
			st.setString(4, author.getGender().getValue());
			st.setString(5, author.getWebSite().getValue());
			st.setInt(6,author.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	
}

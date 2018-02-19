package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Book.Book;
import Model.Author;

public class BookTableGateway {
	private final String dateFormat = "yyyy-MM-dd";
	private Connection conn;
	
	public BookTableGateway(Connection conn){
		this.conn = conn;
	}
	
	public ArrayList<Book> getBooks() {
		ArrayList<Book> books = new ArrayList<Book>();
		// creating database variables and entering account info
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From AuthorDB");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Book temp = new Book(this);
				temp.setId(rs.getInt("id"));
				temp.setTitle(rs.getString("tittle"));
				temp.setSummary(rs.getString("summary"));
				temp.setYearPublished(rs.getInt("year_published")); 
				// Not sure how to set publisher yet Maybe have to get id and check what publisher it is and make it that?
				//temp.setPublisher(rs.getInt("publisher_id"));
				temp.setIsbn(rs.getString("isbn"));
				temp.setDateAdded(rs.getDate("date_added").toLocalDate());
				books.add(temp);
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
		return books;
	}
	
}

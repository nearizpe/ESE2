package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Book.Book;
import Model.Author;

public class BookTableGateway {
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
			stmt = conn.prepareStatement("Select * From bookTable");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Book temp = new Book(this);
				temp.setId(rs.getInt("id"));
				temp.setTitle(rs.getString("tittle"));
				temp.setSummary(rs.getString("summary"));
				temp.setYearPublished(rs.getInt("year_published")); 
				int pubId = rs.getInt("publisher_id");
				//System.out.println("Below");
				//System.out.println("HERE "+ new PublisherTableGateway(conn).getPublisherById(pubId));
				temp.setPublisher(new PublisherTableGateway(conn).getPublisherById(pubId));
				temp.setIsbn(rs.getString("isbn"));
				temp.setDateAdded(rs.getDate("date_added").toLocalDate());
				books.add(temp);
				System.out.println(temp.getTitle());
			}
		} catch (SQLException e) {
			System.out.println("DB QUERY ERROR!!");
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

	public void upDateBook(Book book) throws Exception {
		// TODO Auto-generated method stub
		PreparedStatement st = null;

		if (book.getId() == 0) {
			try {
				System.out.println("HERE IT IS "+ book.getPublisher());
				st = conn.prepareStatement(
						"insert into bookTable (tittle, summary, year_published, publisher_id, isbn) " + "values (?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
				st.setString(1, book.getTitle().getValue());
				st.setString(2, book.getSummary().getValue());
				st.setInt(3, book.getYearPublished().getValue());
				st.setInt(4, book.getPublisher().getValue().getId());
				st.setString(5, book.getIsbn().getValue());
				
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				int id = rs.getInt(1);
				book.setId(id);
				System.out.println("Clicked save in add and id of new obj is " + id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				st = conn.prepareStatement(
						"update authorDatabase set tittle = ?,summary = ?,year_published = ?,publisher_id = ?, isbn = ? where id = ?");
				
				st.setString(1, book.getTitle().getValue());
				st.setString(2, book.getSummary().getValue());
				st.setInt(3, book.getPublisher().getValue().getId());
				st.setString(4, book.getIsbn().getValue());
				
				st.executeUpdate();
				ResultSet rs = st.getGeneratedKeys();
				rs.next();
				int id = rs.getInt(1);
				book.setId(id);

			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception(e);
			} finally {
				try {
					if (st != null)
						st.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new Exception(e);
				}
			}
		}
	}
	
}

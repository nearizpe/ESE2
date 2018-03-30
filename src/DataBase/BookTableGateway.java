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
import Book.Publisher;
import Model.AuditTrailModel;
import Model.Author;
import Model.AuthorBook;

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
				temp.setYearPublished(rs.getInt("year_published")); 				// Not sure how to set publisher yet Maybe have to get id and check what publisher it is and make it that?
				Publisher pub = new PublisherTableGateway(conn).getPublisherById(rs.getInt("publisher_id"));
				temp.setPublisher(pub);
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
				st = conn.prepareStatement(
						"insert into bookTable (tittle, summary, year_published, publisher_id, isbn) " + "values (?, ?, ?, ? ,? )",Statement.RETURN_GENERATED_KEYS);
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
				
				//now do audit trail stuff
				st = conn.prepareStatement(
						"insert into book_audit_trail (book_id, entry_msg) " + "values (?, ?)");
				st.setInt(1, book.getId());
				st.setString(2, "Book added" );
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				// Got to compare values to see what changed .This would be a good point for a transactions
				
				st = conn.prepareStatement("Select * From bookTable where id = ?");
				st.setInt(1, book.getId());
				ResultSet rs = st.executeQuery();
				rs.next();
				Book temp = new Book(this);
				temp.setTitle(rs.getString("tittle"));
				temp.setSummary(rs.getString("summary"));
				temp.setYearPublished(rs.getInt("year_published")); 				// Not sure how to set publisher yet Maybe have to get id and check what publisher it is and make it that?
				Publisher pub = new PublisherTableGateway(conn).getPublisherById(rs.getInt("publisher_id"));
				temp.setPublisher(pub);
				temp.setIsbn(rs.getString("isbn"));
				
				if(!temp.getTitle().getValue().equals(book.getTitle().getValue())){
					AuditTrailChanged(book.getId(), "Title changed from " +temp.getTitle().getValue()+"  to "+book.getTitle().getValue());
				}
				if(!temp.getSummary().getValue().equals(book.getSummary().getValue()) ){
					AuditTrailChanged(book.getId(), "Summary changed from " +temp.getSummary().getValue()+"  to "+ book.getSummary().getValue());
				}
				if(temp.getYearPublished().get() != book.getYearPublished().get()  ){
					AuditTrailChanged(book.getId(), "Year published changed from " +temp.getYearPublished().getValue()+"  to "+ book.getYearPublished().getValue());
				}
				if(temp.getPublisher().getValue().getId() != book.getPublisher().getValue().getId()  ){
					AuditTrailChanged(book.getId(), "Publisher changed from " +temp.getPublisher().getValue().getPublisherName().getValue()+"  to "+ book.getPublisher().getValue().getPublisherName().getValue());
				}
				if(!temp.getIsbn().getValue().equals(book.getIsbn().getValue()) ){
					AuditTrailChanged(book.getId(), "ISBN changed from " +temp.getIsbn().getValue()+"  to "+ book.getIsbn().getValue());
				}
				
				//normal stuff
				st = conn.prepareStatement(
						"update bookTable set tittle = ?, summary = ?, year_published = ?, publisher_id = ?, isbn = ? where id = ?");
				st.setString(1, book.getTitle().getValue());
				st.setString(2, book.getSummary().getValue());
				st.setInt(3, book.getYearPublished().getValue());
				st.setInt(4, book.getPublisher().getValue().getId());
				st.setString(5, book.getIsbn().getValue());
				st.setInt(6, book.getId());
				
				st.executeUpdate();


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

	public void updateAuthorBook(AuthorBook aBook){


	}
	
	public void deleteBook (Book book) throws Exception{
		PreparedStatement st = null;
		System.out.println(book.getId());
		try{
		st = conn.prepareStatement("delete from bookTable where id = ?");
		st.setInt(1, book.getId());
		st.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public ArrayList<Book> getSpecificBooks(String title) {
		ArrayList<Book> books = new ArrayList<Book>();
		// creating database variables and entering account info
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From bookTable where tittle like ?");
			stmt.setString(1, title + '%');
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
	
	public ArrayList<AuditTrailModel> getAuditTrail(Book book) throws Exception{ //implement later
		ArrayList<AuditTrailModel> list = new ArrayList<AuditTrailModel>();
		// creating database variables and entering account info
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From book_audit_trail where book_id = ?");
			stmt.setInt(1, book.getId());
			rs = stmt.executeQuery();
			while (rs.next()) {
				AuditTrailModel temp = new AuditTrailModel();
				temp.setId(rs.getInt("id"));
				DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
				//System.out.println(df.format(rs.getDate("date_added")));
				temp.setDateAdded(rs.getTimestamp("date_added"));
				temp.setMsg(rs.getString("entry_msg"));
				list.add(temp);
			}
		} catch (SQLException e) {
			System.out.println("AUDIT TRAIL QUERY ERROR!!");
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
		
		return list;
	}
	
	private void AuditTrailChanged(int bookId, String messege) throws Exception {
		PreparedStatement st = null;
		st = conn.prepareStatement("insert into book_audit_trail (book_id, entry_msg) " + "values (?, ?)");
		st.setInt(1, bookId);
		st.setString(2, messege );
		st.executeUpdate();
	}

	public ArrayList<AuthorBook> getAuthorsForBook(Book book) throws Exception {
		ArrayList<AuthorBook> list = new ArrayList<AuthorBook>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		stmt = conn.prepareStatement("Select * From author_book where book_id = ?");
		stmt.setInt(1, book.getId());
		rs = stmt.executeQuery();
		AuthorTableGateway aGway = new AuthorTableGateway(conn);
		while (rs.next()) {
			AuthorBook temp = new AuthorBook();
			temp.setAuthor(aGway.getAuthor(rs.getInt("author_id")));
			temp.setBook(book);
			temp.setRoyalty(rs.getBigDecimal("royalty"));
			temp.setNewRecord(false);
			list.add(temp);
		}
		return list;
	}
	
	public void addedAuthor(Book book, Author author){// call whenever you add an author
		String authorname = author.getFirstName().getValue() + " "+author.getLastName().getValue();
		String msg ="Added author " +  authorname;
		try {
			AuditTrailChanged(book.getId(),  msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changedAuthorRoyalty(Book book, Author author,double newVal){// call whenever you change an author royalty
		String authorname = author.getFirstName().getValue() + " "+author.getLastName().getValue();
		String msg ="Changed " +  authorname+ " royalty to " + newVal;
		try {
			AuditTrailChanged(book.getId(),  msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changedAuthorRoyalty(Book book, Author author){// call whenever you delete an author
		String authorname = author.getFirstName().getValue() + " "+author.getLastName().getValue();
		String msg ="deleterd " +  authorname + " from this book ";
		try {
			AuditTrailChanged(book.getId(),  msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAuthorBook(AuthorBook authorBook) throws Exception{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("Delete From author_book where author_id = ?");
			stmt.setInt(1, authorBook.getAuthor().getId());
			rs = stmt.executeQuery();
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}

	}

	public void saveAuthorBook(AuthorBook authorBook) throws Exception{
		PreparedStatement stmt = null;

		if(authorBook.isNewRecord()){
			try {
				stmt = conn.prepareStatement("Insert into author_book (author_id, book_id, royalty) " + "values (?, ?, ?)");
				stmt.setInt(1, authorBook.getAuthor().getId());
				stmt.setInt(2,authorBook.getBook().getId());
				stmt.setDouble(3,authorBook.getRoyalty().doubleValue());
				System.out.println("~~~~~~~~~~~~"+authorBook.getRoyalty().doubleValue());
				stmt.execute();
			}catch (Exception e){
				e.printStackTrace();
				throw new Exception(e);
			}
		}else{
			stmt = conn.prepareStatement("Update author_book set royalty = ? Where author_id = ? && book_id = ?");
			stmt.setBigDecimal(1,authorBook.getRoyalty());
			stmt.setInt(2, authorBook.getAuthor().getId());
			stmt.setInt(3,authorBook.getBook().getId());
			System.out.println("!!!!!!!!!!!!!!!!!"+authorBook.getRoyalty().doubleValue());
			stmt.executeUpdate();
		}

	}
	
}

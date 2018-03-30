package DataBase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;


import Model.AuditTrailModel;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import Model.Author;

public class AuthorTableGateway {
	private final String dateFormat = "yyyy-MM-dd";
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
			stmt = conn.prepareStatement("Select * From authorDatabase");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Author temp = new Author(this);
				temp.setId(rs.getInt("id"));
				temp.setFirstName(rs.getString("first_name"));
				temp.setLastName(rs.getString("last_name"));
				temp.setDateOfBirth(rs.getDate("dob").toLocalDate());
				temp.setGender(rs.getString("gender"));
				temp.setWebSite(rs.getString("web_site"));
				temp.setLastModStamp(rs.getTimestamp("last_modified").toLocalDateTime());
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
	
	public void updateAuthor(Author author) throws Exception {
		PreparedStatement st = null;
		java.util.Date rdob = null;
		java.sql.Date newD = null;

		if (author.getId() == 0) {//creating a new author
			st = conn.prepareStatement("insert into authorDatabase (first_name, last_name, dob, gender, web_site) " + "values (?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			st.setString(1, author.getFirstName().getValue());
			st.setString(2, author.getLastName().getValue());
			rdob = new SimpleDateFormat(dateFormat).parse(author.getDateOfBirth().getValue().toString());
			newD = new Date(rdob.getTime());
			st.setDate(3, newD);
			st.setString(4, author.getGender().getValue());
			st.setString(5, author.getWebSite().getValue());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			author.setId(id);
			System.out.println("Clicked save in add and id of new obj is " + id);
		} else { //updating
			try {
				if(!compareTimeStamps(author)) // check timestamp . If the not the same dont do anything else proceed
				{
					System.out.println("The timestamp from the author model object and the timestamp from the database do not match");

					Exception e = new Exception();
					throw e;
				}else{
					st = conn.prepareStatement(
							"update authorDatabase set first_name = ?,last_name = ?,dob = ?,gender = ?, Web_site = ? where id = ?");
					
					st.setString(1, author.getFirstName().getValue());
					st.setString(2, author.getLastName().getValue());
					rdob = new SimpleDateFormat(dateFormat).parse(author.getDateOfBirth().getValue().toString());
					newD = new Date(rdob.getTime());
					st.setDate(3, newD);
					st.setString(4, author.getGender().getValue());
					st.setString(5, author.getWebSite().getValue());
					st.setInt(6, author.getId());
                    //updates author audit trail
                    auditCompare(author.getId(),author);

					st.executeUpdate();
					
					//we must update the local time stamp or else you can only edit once
					author.setLastModStamp(getLocalDateTime(author));
				}

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

	public void deleteAuthor (Author author) throws Exception{
		PreparedStatement st = null;
		System.out.println(author.getId());
		try{
		st = conn.prepareStatement("delete from authorDatabase where id = ?");
		st.setInt(1, author.getId());
		st.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	private boolean compareTimeStamps(Author author) { // returns true is
														// timeStamps are same
														// false otherwise
		PreparedStatement st = null;
		ResultSet rs = null;

		LocalDateTime timeStamp = getLocalDateTime(author);
		if (author.getLastModStamp().equals(timeStamp)) {
			return true;
		} else {
			return false;
		}
	}
	
	private LocalDateTime getLocalDateTime(Author author){
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("Select * From authorDatabase where id = ?");
			st.setInt(1, author.getId());
			rs = st.executeQuery();
			rs.next();
			Timestamp dbStamp = rs.getTimestamp("last_modified");
			LocalDateTime timeStamp = dbStamp.toLocalDateTime();
			System.out.println("Local stamp is "+ author.getLastModStamp() + " DB stamp is "+ timeStamp);
			return timeStamp;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Author getAuthor(int id) { //gets specifi author based of id
		// creating database variables and entering account info
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From authorDatabase where id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
				Author temp = new Author(this);
				temp.setId(rs.getInt("id"));
				temp.setFirstName(rs.getString("first_name"));
				temp.setLastName(rs.getString("last_name"));
				temp.setDateOfBirth(rs.getDate("dob").toLocalDate());
				temp.setGender(rs.getString("gender"));
				temp.setWebSite(rs.getString("web_site"));
				temp.setLastModStamp(rs.getTimestamp("last_modified").toLocalDateTime());
				return temp;
		} catch (SQLException e) {
			System.out.println("DB QUERY ERROR");
			e.printStackTrace();
		}
		return null;
		}


    public ArrayList<AuditTrailModel> getAuditTrail(Author author) throws Exception{ //implement later
        ArrayList<AuditTrailModel> list = new ArrayList<AuditTrailModel>();
        // creating database variables and entering account info
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("Select * From author_audit_trail where author_id = ?");
            stmt.setInt(1, author.getId());
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

    private void AuditTrailChanged(int authorId, String message) throws Exception {
        PreparedStatement st = null;
        st = conn.prepareStatement("insert into author_audit_trail (author_id, entry_msg) " + "values (?, ?)");
        st.setInt(1, authorId);
        st.setString(2, message);
        st.executeUpdate();
    }

    private void auditCompare(int authorId, Author author){
        Author temp = new Author(this);
        temp = getAuthor(authorId);

        if(!temp.getFirstName().getValue().equals(author.getFirstName().getValue())){
            try {
                AuditTrailChanged(authorId, "First name changed from " + temp.getFirstName().getValue() + " to " + author.getFirstName().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(!temp.getLastName().getValue().equals(author.getLastName().getValue())){
            try {
                AuditTrailChanged(authorId, "Last name changed from " + temp.getLastName().getValue() + " to " + author.getLastName().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(!temp.getDateOfBirth().getValue().equals(author.getDateOfBirth().getValue())){
            try {
                AuditTrailChanged(authorId, "Date of Birth changed from " + temp.getDateOfBirth().getValue() + " to " + author.getDateOfBirth().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(!temp.getGender().getValue().equals(author.getGender().getValue())){
            try {
                AuditTrailChanged(authorId, "Gender changed from " + temp.getGender().getValue() + " to " + author.getGender().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(!temp.getWebSite().getValue().equals(author.getWebSite().getValue())){
            try {
                AuditTrailChanged(authorId, "Web Site changed from " + temp.getWebSite().getValue() + " to " + author.getWebSite().getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}




package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Book.Book;
import Book.Publisher;

public class PublisherTableGateway {

	private Connection conn;

	public PublisherTableGateway(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<Publisher> getPublishers() {
		ArrayList<Publisher> list = new ArrayList<Publisher>();
		// gateWay Stuff
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From publisherTable");
			rs = stmt.executeQuery();
			while (rs.next()) {
				Publisher temp = new Publisher(this);
				temp.setId(rs.getInt("id"));
				temp.setPublisherName(rs.getString("publisher_name"));
				list.add(temp);
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
		return list;
	}

	public Publisher getPublisherById(int id) {
		Publisher pub = new Publisher(this);

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement("Select * From publisherTable where id = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			rs.next();
			pub.setId(id);
			//System.out.println("SWL RESULT ID IS" +rs.getInt("id"));
			//System.out.println("SWL RESULT IS" +rs.getString("publisher_name"));
			pub.setPublisherName(rs.getString("publisher_name"));
			System.out.println("CRASH5");
		} catch (SQLException e) {
			System.out.println("DB QUERY ERROR!! Publisher Table GAtewaty");
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
		return pub;
	}
}

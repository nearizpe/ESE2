package DataBase;

import java.sql.Connection;
import java.util.ArrayList;

import Book.Publisher;

public class PublisherTableGateway {

	private Connection conn;
	
	public PublisherTableGateway(Connection conn){
		this.conn = conn;
	}
	
	public ArrayList<Publisher> getPublishers (){
		ArrayList<Publisher> list = new ArrayList<Publisher>();
		
		return list;
	}
	
	public Publisher getPublisherById(int id){
		return null;
	}
}

package Book;

import DataBase.PublisherTableGateway;
import javafx.beans.property.SimpleStringProperty;

public class Publisher {

	private int id;
	private SimpleStringProperty publisherName ;
	private PublisherTableGateway gateway;

	public Publisher(PublisherTableGateway way){
		this.gateway = way;
		publisherName = new SimpleStringProperty();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SimpleStringProperty getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName.setValue(publisherName);
	}
	
	public String toString(){
		return this.getPublisherName().getValue();
	}
	
	
}

package Book;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import DataBase.AuthorTableGateway;
import DataBase.BookTableGateway;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

public class Book {
	
	private final String dateFormat = "yyyy-MM-dd";

	private int id;
	private SimpleStringProperty title ; 
	private SimpleStringProperty summary ;
	private SimpleIntegerProperty yearPublished ;
	private SimpleObjectProperty<Publisher> publisher ;
	private SimpleStringProperty isbn ;
	private SimpleObjectProperty<LocalDate> dateAdded ;
	
	private BookTableGateway gateway;

	public Book(BookTableGateway gway){
		this.gateway = gway;
	}
	
	public Boolean isTittleValid(){
		if(1 <= title.length().intValue() && title.length().intValue() <=255)
		{
			return true;
		}
		return false;
	}
	
	public Boolean isSummaryValid(){
		if(summary.length().intValue() <=65536)
		{
			return true;
		}
		return false;
	}
	
	public boolean isValidDate(){
		try{
			Date newdob;
			Date current = new Date();
			DateFormat df = new SimpleDateFormat(dateFormat);
			try {
				newdob = df.parse(yearPublished.getValue().toString());
				if (newdob.before(current)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		catch(Exception e){
			
		}
		return false;
	}
	
	public Boolean isISBNValid(){
		if(isbn.length().intValue() >13)
		{
			return false;
		}
		return true;
	}
	
	
	
	
	//KEEP IN MIND???
	//date_added should only be assigned by MySQL at the time the Book record is inserted into the db
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SimpleStringProperty getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title.setValue(title); 
	}

	public SimpleStringProperty getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary.setValue(summary);
	}

	public SimpleIntegerProperty getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(int  yearPublished) {
		this.yearPublished.setValue( yearPublished);
	}

	public SimpleObjectProperty<Publisher> getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher.setValue(publisher);
	}

	public SimpleStringProperty getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn.setValue(isbn);
	}

	public SimpleObjectProperty<LocalDate> getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded.setValue(dateAdded);
	}

	public BookTableGateway getGateway() {
		return gateway;
	}

	public void setGateway(BookTableGateway gateway) {
		this.gateway = gateway;
	}

	public String getDateFormat() {
		return dateFormat;
	}
	
}
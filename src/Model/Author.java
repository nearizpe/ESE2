package Model;

import java.util.Date;

import DataBase.AuthorTableGateway;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

public class Author {
	private final String dateFormat = "yyyy-MM-dd";
	private int id;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleObjectProperty<LocalDate> dateOfBirth;
	private SimpleObjectProperty<String> gender;
	private SimpleStringProperty webSite;
	private AuthorTableGateway gateway;
	
	public AuthorTableGateway getGateway() {
		return gateway;
	}

	public void setGateway(AuthorTableGateway gateway) {
		this.gateway = gateway;
	}

	public Author(AuthorTableGateway gateway){
		this.gateway = gateway;
		//initialize
		firstName = new SimpleStringProperty();
	    lastName = new SimpleStringProperty();
		dateOfBirth = new SimpleObjectProperty<LocalDate>();
		gender = new SimpleObjectProperty<String>();
		webSite = new SimpleStringProperty();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SimpleStringProperty getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName.setValue(firstName);
	}
	public SimpleStringProperty getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName.setValue(lastName);
	}
	public SimpleObjectProperty<LocalDate> getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.setValue(dateOfBirth);
	}
	public SimpleObjectProperty<String> getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender.setValue(gender);
	}
	public SimpleStringProperty getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite.setValue(webSite);
	}
	
	public boolean isValidId(int id){
		if(id >= 0){
			return true;	
		}
		return false;
	}
	public boolean isValidFirstName(String firstName){
		try{
			if(firstName.length() > 0 && firstName.length() <= 100){
				return true;	
			}
		}
		catch(Exception e){
			
		}
		return false;
	}
	public boolean isValidLastName(String lastName){
		try{
			if(lastName.length() > 0 && lastName.length() <= 100){
				return true;	
			}
		}
		catch(Exception e){
			
		}

		return false;
	}
	public boolean isValidDate(DatePicker dob){
		try{
			Date newdob;
			Date current = new Date();
			DateFormat df = new SimpleDateFormat(dateFormat);
			try {
				newdob = df.parse(dob.getValue().toString());
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
	public boolean isValidGender(String gender){
		try {
			gender = gender.toLowerCase();
			if(gender.equals("male") || gender.equals("female") || gender.equals("unknown")){
				return true;	
			}
		}
		catch(Exception e){
		}

		return false;
	}
	public boolean isValidWebSite(String url){
		try {
			if(url.length() <= 100){
				return true;	
			}
		}
		catch(Exception e){
		}

		return false;
	}
	
	public Author clone(){
		Author author = new Author(this.getGateway());
		author.setFirstName(this.getFirstName().getValue());
		author.setLastName(this.getLastName().getValue());
		author.setGender(this.getGender().getValue());
		author.setWebSite(this.getWebSite().getValue());
		author.setDateOfBirth(this.getDateOfBirth().getValue());
		author.setId(id);

		return author;
	}
}

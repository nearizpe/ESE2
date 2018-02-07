package Model;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Author {
	private int id;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleObjectProperty<LocalDate> dateOfBirth;
	private SimpleStringProperty gender;
	private SimpleStringProperty webSite;
	
	public Author(){
		//initialize
		firstName = new SimpleStringProperty();
	    lastName = new SimpleStringProperty();
		dateOfBirth = new SimpleObjectProperty<LocalDate>();
		gender = new SimpleStringProperty();
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
	public SimpleStringProperty getGender() {
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
		if(id > 0){
			return true;	
		}
		return false;
	}
	public boolean isValidFirstName(String firstName){
		if(firstName.length() > 0 && firstName.length() <= 100){
			return true;	
		}
		return false;
	}
	public boolean isValidLastName(String lastName){
		if(lastName.length() > 0 && lastName.length() <= 100){
			return true;	
		}
		return false;
	}
	public boolean isValidDate(String dob){
		Date newdob;
		Date current = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			newdob = df.parse(dob);
			if(newdob.before(current)){
				return true;
			}else{
				return false;
			}	
		}catch(ParseException e){
			e.printStackTrace();
		}
		return false;
	}
	public boolean isValidGender(String gender){
		gender.toLowerCase();
		if(gender.equals("male") || gender.equals("female") || gender.equals("unknown")){
			return true;	
		}
		return false;
	}
	public boolean isValidWebSite(String url){
		if(url.length() <= 100){
			return true;	
		}
		return false;
	}
}

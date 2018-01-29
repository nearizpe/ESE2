package Model;

import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class AuthorModel {
	int id;
	SimpleStringProperty firstName;
	SimpleStringProperty lastName;
	SimpleObjectProperty<LocalDate> dateOfBirth;
	SimpleStringProperty gender;
	SimpleStringProperty webSite;
	
	public AuthorModel(){
		//initialize
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
	public void setFirstName(SimpleStringProperty firstName) {
		this.firstName = firstName;
	}
	public SimpleStringProperty getLastName() {
		return lastName;
	}
	public void setLastName(SimpleStringProperty lastName) {
		this.lastName = lastName;
	}
	public SimpleObjectProperty<LocalDate> getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(SimpleObjectProperty<LocalDate> dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public SimpleStringProperty getGender() {
		return gender;
	}
	public void setGender(SimpleStringProperty gender) {
		this.gender = gender;
	}
	public SimpleStringProperty getWebSite() {
		return webSite;
	}
	public void setWebSite(SimpleStringProperty webSite) {
		this.webSite = webSite;
	}
}

package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import assignment1.UsefulFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Model.Author;
import assignment1.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.LocalDateTimeStringConverter;

public class AuthorDetailViewController extends ViewController{
	
	private static Logger logger = LogManager.getLogger(Main.class);
	private final String dateFormat = "yyyy-MM-dd";
	private Author author;

	@FXML
    private TextField FirstName;

    @FXML
    private TextField LastName;

    @FXML
    private DatePicker Birthday;

    @FXML
    private ComboBox<String> Gender;

    @FXML
    private TextField Website;

    @FXML
    void SaveHandler(MouseEvent event) {
    	Alert msg = new Alert(AlertType.INFORMATION);
    	msg.setTitle("Error!");
    	msg.setHeaderText("Format Error");
    	
    	Boolean valid = true;
    	
    	if(!author.isValidFirstName(FirstName.getText())){
    		logger.error("Invalid First Name");
    		msg.setContentText("Invalid First Name");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!author.isValidLastName(LastName.getText())){
    		logger.error("Invalid Last Name");
    		msg.setContentText("Invalid Last Name");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!author.isValidDate(Birthday)){
    		logger.error("Invalid Birthday");
    		msg.setContentText("Invalid Birthday");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!author.isValidGender(Gender.getValue())){
    		logger.error("Invalid Gender");
    		msg.setContentText("Invalid Gender");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!author.isValidWebSite(Website.getText())){
    		logger.error("Invalid Website");
    		msg.setContentText("Invalid WebSite");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!author.isValidId(author.getId())){ // do to binding the program may crash and this not get called because of data type diferientiation. The fact that it makes save not do anthing means we might not need too fix
    		logger.error("Invalid Id");
    		msg.setContentText("Invalid Id");
    		msg.showAndWait();
    		valid = false;
    	}
    	
    	if (valid){
    		logger.info("clicked save");
    		author.setFirstName(this.FirstName.getText());
    		author.setLastName(this.LastName.getText());
    		author.setGender(this.Gender.getValue());
    		author.setWebSite(this.Website.getText());
    		LocalDate date ;
    		logger.info(dateFormat);
    		
    			date = this.Birthday.getValue();
	    		author.setDateOfBirth(date);
	    		logger.info(date);
    		
    		try {
				author.getGateway().updateAuthor(author);
			} catch (Exception e) {
				// TODO Auto-generated catch block
		    	msg.setTitle("save failure error");
		    	msg.setHeaderText("Outdated time stamp Error");
		    	msg.setContentText("go Back to the Author List to fetch a fresh copy of the Author");
	    		msg.showAndWait();
				logger.error("couldnt update database");
				//e.printStackTrace();
			}
    	}
    	
    	
    }

	@FXML
	void AuditButtonHandler(MouseEvent event) {
		try {
			if(author.getId() == 0){
				Alert msg = new Alert(AlertType.INFORMATION);
				msg.setTitle("Error!");
				msg.setHeaderText("Error");

				msg.setContentText("Click Save before going to audit trail");
				msg.showAndWait();
			}
			else{
				UsefulFunctions functions = UsefulFunctions.getInstance();
				functions.SwitchView(functions.AuditTrail, this.author);
			}

		}
		catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public AuthorDetailViewController(Author author){
    	this.author = author.clone();
    }
    
    public void initialize() {
    	
    	//dogName.textProperty().bindBidirectional(dog.dogNameProperty());
    	this.FirstName.textProperty().bindBidirectional(author.getFirstName());
    	this.LastName.textProperty().bindBidirectional(author.getLastName());
    	this.Gender.valueProperty().bindBidirectional(author.getGender());
    	this.Website.textProperty().bindBidirectional(author.getWebSite());
    	this.Birthday.valueProperty().bindBidirectional(author.getDateOfBirth());
    	
    	Gender.getItems().removeAll(Gender.getItems());
    	Gender.getItems().addAll("Female", "Male", "Unknown");
	}
}

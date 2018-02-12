package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Model.Author;
import assignment1.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AuthorDetailViewController extends ViewController{
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	private Author author;

	@FXML
    private TextField FirstName;

    @FXML
    private TextField LastName;

    @FXML
    private TextField Birthday;

    @FXML
    private TextField Gender;

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
    	if(!author.isValidDate(Birthday.getText())){
    		logger.error("Invalid Birthday");
    		msg.setContentText("Invalid Birthday");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!author.isValidGender(Gender.getText())){
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
    	if(!author.isValidId(author.getId())){
    		logger.error("Invalid Id");
    		msg.setContentText("Invalid Id");
    		msg.showAndWait();
    		valid = false;
    	}
    	
    	if (valid){
    		logger.info("clicked save");
    		author.setFirstName(this.FirstName.getText());
    		author.setLastName(this.LastName.getText());
    		author.setGender(this.Gender.getText());
    		author.setWebSite(this.Website.getText());
    		LocalDate date ;
    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		try {
				date =  df.parse(this.Birthday.getText()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    		author.setDateOfBirth(date);
	    		logger.info(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		try {
				author.getGateway().updateAuthor(author);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("couldnt update database");
				e.printStackTrace();
			}
    	}
    	
    	
    }
    
    public AuthorDetailViewController(Author author){
    	this.author = author.clone(author);
    }
    
    public void initialize() {
    	//dogName.textProperty().bindBidirectional(dog.dogNameProperty());
    	this.FirstName.textProperty().bindBidirectional(author.getFirstName());
    	this.LastName.textProperty().bindBidirectional(author.getLastName());
    	this.Gender.textProperty().bindBidirectional(author.getGender());
    	this.Website.textProperty().bindBidirectional(author.getWebSite());

    	//bind later
    	//this.Birthday.textProperty().bin
    	this.Birthday.setText(author.getDateOfBirth().getValue().toString());
	}
}

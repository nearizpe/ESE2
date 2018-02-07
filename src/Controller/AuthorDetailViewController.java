package Controller;

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
    	
    	if(!author.isValidFirstName(FirstName.getText())){
    		logger.error("Invalid First Name");
    		msg.setContentText("Invalid First Name");
    		msg.showAndWait();
    	}
    	if(!author.isValidLastName(LastName.getText())){
    		logger.error("Invalid Last Name");
    		msg.setContentText("Invalid Last Name");
    		msg.showAndWait();
    	}
    	if(!author.isValidDate(Birthday.getText())){
    		logger.error("Invalid Birthday");
    		msg.setContentText("Invalid Birthday");
    		msg.showAndWait();
    	}
    	if(!author.isValidGender(Gender.getText())){
    		logger.error("Invalid Gender");
    		msg.setContentText("Invalid Gender");
    		msg.showAndWait();
    	}
    	if(!author.isValidWebSite(Website.getText())){
    		logger.error("Invalid Website");
    		msg.setContentText("Invalid WebSite");
    		msg.showAndWait();
    	}
    	if(!author.isValidId(author.getId())){
    		logger.error("Invalid Id");
    		msg.setContentText("Invalid Id");
    		msg.showAndWait();
    	}
    	
    	logger.info("clicked save");
    	
    }
    
    public AuthorDetailViewController(Author author){
    	this.author = author;
    }
    
    public void initialize() {
    	this.FirstName.setText(author.getFirstName().getValue());
    	this.LastName.setText(author.getLastName().getValue());
    	this.Birthday.setText(author.getDateOfBirth().getValue().toString());
    	this.Gender.setText(author.getGender().getValue());
    	this.Website.setText(author.getWebSite().getValue());
	}
}

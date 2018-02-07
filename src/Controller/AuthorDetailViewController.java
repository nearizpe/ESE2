package Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Model.Author;
import assignment1.Main;
import javafx.fxml.FXML;
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

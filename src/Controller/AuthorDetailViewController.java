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
    	author = new Author();
    	
    }
}

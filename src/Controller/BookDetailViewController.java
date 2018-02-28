package Controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Book.Book;
import Book.Publisher;
import DataBase.PublisherTableGateway;
import assignment1.Main;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;

public class BookDetailViewController extends ViewController{
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	private Book book;
	@FXML
    private TextField tittleTextField;

    @FXML
    private TextField ISBNField;

    @FXML
    private ComboBox<Publisher> publisherComboBox;

    @FXML
    private TextArea summaryTextArea;

    @FXML
    private TextField yearPubTF;
    
    private ArrayList<Publisher> publishers;
    
    public BookDetailViewController(Book book, ArrayList<Publisher> gway ){
    	this.publishers = gway;
    	this.book = book;
    }
    
    @FXML
    void SaveHandler(MouseEvent event) {
    	Alert msg = new Alert(AlertType.INFORMATION);
    	msg.setTitle("Error!");
    	msg.setHeaderText("Format Error");
    	
    	Boolean valid = true;
    	
    	if(!book.isTittleValid()){
    		logger.error("Invalid Tittle");
    		msg.setContentText("Invalid First Name");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!book.isSummaryValid()){
    		logger.error("Invalid Summary");
    		msg.setContentText("Invalid Last Name");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!book.isValidYear(this.yearPubTF.getText())){
    		logger.error("Invalid year Published");
    		msg.setContentText("Invalid year Published");
    		msg.showAndWait();
    		valid = false;
    	}
    	if(!book.isISBNValid()){
    		logger.error("Invalid ISBN");
    		msg.setContentText("Invalid ISBN");
    		msg.showAndWait();
    		valid = false;
    	}
    	
    	if (valid){
    		logger.info("clicked save");
    		/*book.setFirstName(this.FirstName.getText());
    		author.setLastName(this.LastName.getText());
    		author.setGender(this.Gender.getValue());
    		author.setWebSite(this.Website.getText());
    		LocalDate date ;
    		logger.info(dateFormat);
    		
    			date = this.Birthday.getValue();
	    		author.setDateOfBirth(date);
	    		logger.info(date);
    		*/
    		try {
				book.getGateway().upDateBook(book);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("couldnt update database");
				e.printStackTrace();
			}
    	}
    }
    
    public void initialize() {
    	this.tittleTextField.textProperty().bindBidirectional(book.getTitle());
    	this.ISBNField.textProperty().bindBidirectional(book.getIsbn());
    	this.publisherComboBox.valueProperty().bindBidirectional(book.getPublisher());
    	this.summaryTextArea.textProperty().bindBidirectional(book.getSummary());
    	this.yearPubTF.textProperty().bindBidirectional(book.getYearPublished(), new NumberStringConverter());
    	
    	//System.out.println(publisherTableGateway + "What is it?");
    	publisherComboBox.getItems().removeAll();
    	publisherComboBox.getItems().addAll(publishers);
    }
}

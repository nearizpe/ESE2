package Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import DataBase.AuthorTableGateway;
import Model.Author;
import Model.AuthorBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Book.Book;
import Book.Publisher;
import assignment1.Main;
import assignment1.UsefulFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;

public class BookDetailViewController extends ViewController{
	
	private static Logger logger = LogManager.getLogger(Main.class);
	
	private Book book;
	AuthorBook SelectedAuthor;


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
    
    @FXML
    private Button AuditTrailButton;

	@FXML
	private ListView<AuthorBook> AuthorListView;

	@FXML
	private Button AddAuthorButton;

	@FXML
	private Button DeleteAuthorButton;

	@FXML
	private ComboBox<Author> authorComboBox;

	@FXML
	private TextField royaltyInput;
    
    private ArrayList<Publisher> publishers;
	private ArrayList<Author> authors;
    private ArrayList<AuthorBook> authorBooks;
	private ObservableList<AuthorBook> listItems = FXCollections.observableArrayList();

    
    public BookDetailViewController(Book book, ArrayList<Publisher>  publishers){
    	this.publishers = publishers;
    	this.book = book;
    	try {
			this.authorBooks = book.getAuthors(this.book);
		}catch (Exception e){
			logger.error("Could not get Book Authors");
			e.printStackTrace();
		}
		UsefulFunctions uf =  UsefulFunctions.getInstance();
		AuthorTableGateway authorgw = new AuthorTableGateway(uf.getConn());
		authors = authorgw.getAuthors();
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

    		try {
				book.getGateway().upDateBook(book);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("couldnt update database");
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void AuiditButtonHandler(ActionEvent event) {
    	try {
    		if(book.getId() == 0){
    			Alert msg = new Alert(AlertType.INFORMATION);
    	    	msg.setTitle("Error!");
    	    	msg.setHeaderText("Error");
    	    	
        		msg.setContentText("Click Save before going to audit trail");
        		msg.showAndWait();
    		}
    		else{
    			UsefulFunctions functions = UsefulFunctions.getInstance();
    			functions.SwitchView(functions.AuditTrail, this.book);		
    		}
			
    	}
    	catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    }

	@FXML
	void AddAuthorHandler(ActionEvent event) {
		Alert msg = new Alert(AlertType.INFORMATION);
		msg.setTitle("Error!");
		msg.setHeaderText("Format Error");

		Boolean valid = true;
		AuthorBook newAuthor = new AuthorBook();
		newAuthor.setAuthor(authorComboBox.getSelectionModel().getSelectedItem());
		newAuthor.setBook(this.book);
		newAuthor.setRoyalty(new BigDecimal(royaltyInput.getText()));

		if(!newAuthor.isValidRoyalty()){
			logger.error("Invalid Royalty");
			msg.setContentText("Royalty needs to be in between 0 and 1");
			msg.showAndWait();
			valid = false;
		}

		if (valid) {
			logger.info("clicked Add author");
			//call update authorbook
			try {
				book.getGateway().saveAuthorBook(newAuthor);
			}catch (Exception e){
				logger.error("Error adding author");
				e.printStackTrace();
			}
		}
	}

	@FXML
	void AuthorListClick(MouseEvent event) {
		Alert msg = new Alert(AlertType.INFORMATION);
		msg.setTitle("Error!");
		msg.setHeaderText("Invalid Input");

		if(event.getClickCount() == 1){
			SelectedAuthor = AuthorListView.getSelectionModel().getSelectedItem();
		}else if(event.getClickCount() >1 ){
			TextInputDialog dialog = new TextInputDialog(""+ SelectedAuthor.getRoyalty());
			dialog.setTitle("Edit Royalty");
			dialog.setHeaderText("Royalty for "+ SelectedAuthor.getAuthor().getFirstName().getValue() + " " + SelectedAuthor.getAuthor().getLastName().getValue());
			dialog.setContentText("Please Enter a new royalty:");
			Optional<String> result = dialog.showAndWait();
			if(result.isPresent()){
				System.out.println("User entered "+ result.get());
				SelectedAuthor.setRoyalty(new BigDecimal(result.get()));
				if(SelectedAuthor.isValidRoyalty()){
					//call update authorbook
					try {
						book.getGateway().saveAuthorBook(SelectedAuthor);
					}catch (Exception e){
						logger.error("Error adding author");
						e.printStackTrace();
					}
				}else{
					logger.error("Invalid Royalty");
					msg.setContentText("Royalty needs to be in between 0 and 1");
					msg.showAndWait();
				}
			}
		}

	}

	@FXML
	void DeleteAuthorHandler(ActionEvent event) {

		if (SelectedAuthor != null){
			try {
			//call delete authorBook
				book.getGateway().deleteAuthorBook(SelectedAuthor);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("Could not delete author");
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
		authorComboBox.getItems().removeAll();
		authorComboBox.getItems().addAll(authors);
    	publisherComboBox.getItems().removeAll();
    	publisherComboBox.getItems().addAll(publishers);
		listItems.setAll(authorBooks);
		AuthorListView.setItems(listItems);

    }
}

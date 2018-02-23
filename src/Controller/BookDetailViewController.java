package Controller;

import java.time.LocalDate;

import Book.Book;
import Book.Publisher;
import DataBase.PublisherTableGateway;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;

public class BookDetailViewController extends ViewController{
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
    
    private PublisherTableGateway publisherTableGateway;
    //public BookDetailViewController(PublisherTableGateway gway){
    public BookDetailViewController(Book book){
    	//this.publisherTableGateway = gway;
    	this.book = book;
    }
    
    @FXML
    void SaveHandler(MouseEvent event) {

    }
    
    public void initialize() {
    	this.tittleTextField.textProperty().bindBidirectional(book.getTitle());
    	this.ISBNField.textProperty().bindBidirectional(book.getIsbn());
    	this.publisherComboBox.valueProperty().bindBidirectional(book.getPublisher());
    	this.summaryTextArea.textProperty().bindBidirectional(book.getSummary());
    	this.yearPubTF.textProperty().bindBidirectional(book.getYearPublished(), new NumberStringConverter());
    	
    }
}

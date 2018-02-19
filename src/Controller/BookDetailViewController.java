package Controller;

import DataBase.PublisherTableGateway;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BookDetailViewController extends ViewController{
	
	@FXML
    private TextField tittleTextField;

    @FXML
    private TextField ISBNField;

    @FXML
    private ComboBox<?> publisherComboBox;

    @FXML
    private TextArea summaryTextArea;

    @FXML
    private DatePicker yearPublishedDatePicker;

    private PublisherTableGateway publisherTableGateway;
    public BookDetailViewController(PublisherTableGateway gway){
    	this.publisherTableGateway = gway;
    }
    
    @FXML
    void SaveHandler(MouseEvent event) {

    }
}

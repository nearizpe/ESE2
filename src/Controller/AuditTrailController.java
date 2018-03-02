package Controller;

import java.util.ArrayList;

import Book.Book;
import Model.AuditTrailModel;
import assignment1.UsefulFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AuditTrailController extends ViewController {

	ArrayList<AuditTrailModel> models;
	
	private Book book;
	
    @FXML
    private Label AuditLabel;

    @FXML
    private Button BackButton;

    @FXML
    void BackHandler(ActionEvent event) {
    	UsefulFunctions functions = UsefulFunctions.getInstance();
		try {
			functions.SwitchView(functions.BookDetail, book);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public AuditTrailController(Book book,ArrayList<AuditTrailModel> list){
		this.models = list;
		this.book = book;
	}
	
	public void initialize() {
		AuditLabel.setText("Audit Trail for " + book.getTitle().getValue());
	}
}

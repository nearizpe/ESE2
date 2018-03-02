package Controller;

import java.util.ArrayList;

import Book.Book;
import Model.AuditTrailModel;
import assignment1.UsefulFunctions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AuditTrailController extends ViewController {

	ArrayList<AuditTrailModel> models;
	
	private Book book;
	private ObservableList<AuditTrailModel> listItems = FXCollections.observableArrayList();
	
    @FXML
    private Label AuditLabel;

    @FXML
    private Button BackButton;
    

    @FXML
    private ListView<AuditTrailModel> ListView;

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
		listItems.setAll(models);
		ListView.setItems(listItems);
	}
}

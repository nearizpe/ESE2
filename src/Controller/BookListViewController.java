package Controller;

import java.util.ArrayList;

import Book.Book;
import Book.Publisher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import assignment1.UsefulFunctions;

public class BookListViewController extends ViewController {

	private ArrayList<Book> books;
	private ObservableList<Book> listItems = FXCollections.observableArrayList();	

	@FXML
	private Button SearchButton;

	@FXML
	private TextField searchField;

	@FXML
	private ListView<Book> ListView;

	@FXML
	void ListClick(MouseEvent event) {
		if(event.getClickCount() > 1) {
			Book nb = ListView.getSelectionModel().getSelectedItem();
			if (nb != null){
				UsefulFunctions functions = UsefulFunctions.getInstance();
				try {
					functions.SwitchView(functions.BookDetail, nb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	void SearchHandler(ActionEvent event) {

	}

	public BookListViewController(ArrayList<Book> books) {
		this.books = books;
	}

	public void initialize() {// URL location, ResourceBundle resources
		listItems.setAll(books);
		ListView.setItems(listItems);
	}
}

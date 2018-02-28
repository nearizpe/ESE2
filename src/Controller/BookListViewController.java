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
	private Book SelectedBook = null;

	@FXML
	private Button SearchButton;

	@FXML
	private TextField searchField;

	@FXML
	private ListView<Book> ListView;

	@FXML
	void ListClick(MouseEvent event) {
		
		
		if(event.getClickCount() == 1){
			SelectedBook = ListView.getSelectionModel().getSelectedItem();
			searchField.setText(SelectedBook.getTitle().getValue());
		}
		else if(event.getClickCount() > 1) {
			SelectedBook = ListView.getSelectionModel().getSelectedItem();
			if (SelectedBook != null){
				UsefulFunctions functions = UsefulFunctions.getInstance();
				try {
					functions.SwitchView(functions.BookDetail, SelectedBook);
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
	
	@FXML
	void DeleteHandler(ActionEvent event) {
		if (SelectedBook != null){
			try {
				SelectedBook.getGateway().deleteBook(SelectedBook);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public BookListViewController(ArrayList<Book> books) {
		this.books = books;
	}

	public void initialize() {// URL location, ResourceBundle resources
		listItems.setAll(books);
		ListView.setItems(listItems);
	}
}

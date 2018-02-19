package Controller;

import Book.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BookListViewController extends ViewController {

	public BookListViewController() {

	}

	public void initialize() {// URL location, ResourceBundle resources

	}

	@FXML
	private Button SearchButton;

	@FXML
	private TextField searchField;

	@FXML
	private ListView<Book> ListView;

	@FXML
	void ListClick(MouseEvent event) {

	}

	@FXML
	void SearchHandler(ActionEvent event) {

	}

}

package Controller;

import java.util.ArrayList;

import Book.Book;
import Book.Publisher;
import DataBase.BookTableGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import assignment1.UsefulFunctions;

public class BookListViewController extends ViewController {

	private BookTableGateway gway;
	private ArrayList<Book> books;
	private ObservableList<Book> listItems = FXCollections.observableArrayList();
	private Book SelectedBook = null;
	private int numOfPages = 0;
	private int lastPage = 0;

	@FXML
	private Button SearchButton;
	
	@FXML
	private TextField searchField;

	@FXML
	private ListView<Book> ListView;
	
	 @FXML
	 private Button FirstPageButton;

	 @FXML
	 private Button PrevPageButton;

     @FXML
     private Button NextPageButton;

    @FXML
    private Button LastPageButton;

    @FXML
    private Text RecordNumberDisplay;

	public BookListViewController(BookTableGateway gway) {
		this.gway = gway;
		//this.books = gway.getBooks();
		this.books = gway.getRangeBooks(0,0);
		this.numOfPages = gway.getNumRecords();
		this.lastPage = numOfPages/50;
	}
	
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
		if(searchField.getText() == ""){
			listItems.setAll(books);
			ListView.setItems(listItems);
		}
		else{
			ArrayList<Book> nbList;
			nbList = gway.getSpecificBooks(searchField.getText());
			
			listItems.setAll(nbList);
			ListView.setItems(listItems);
		}
	}
	
	@FXML
	void DeleteHandler(ActionEvent event) {
		if (SelectedBook != null){
			try {
				SelectedBook.getGateway().deleteBook(SelectedBook);
				UsefulFunctions functions = UsefulFunctions.getInstance();
				functions.SwitchView(functions.BookListView, null);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	void FirstPageHandler(ActionEvent event) {
		System.out.println("FP Clicked");
		ArrayList<Book> FPList;
		FPList = gway.getRangeBooks(0,0);
		
		listItems.setAll(FPList);
		ListView.setItems(listItems);
		ListView.refresh();
	}

	@FXML
	void LastPageHandler(ActionEvent event) {
		ArrayList<Book> LPList;
		System.out.println("LP Clicked");
		LPList = gway.getRangeBooks(lastPage,0);
		listItems.setAll(LPList);
		ListView.setItems(listItems);
		ListView.refresh();
	}
	
	@FXML
	void NextPageHandler(ActionEvent event) {

	}

	@FXML
	void PrevPageHandler(ActionEvent event) {

	}
	
	

	public void initialize() {// URL location, ResourceBundle resources
		listItems.setAll(books);
		ListView.setItems(listItems);
	}
}

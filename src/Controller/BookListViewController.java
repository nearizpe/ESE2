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
import authentication.User;
import core.clientTest;

public class BookListViewController extends ViewController {

	private BookTableGateway gway;
	private ArrayList<Book> books;
	private ObservableList<Book> listItems = FXCollections.observableArrayList();
	private Book SelectedBook = null;
	private int numOfPages = 0;
	private int lastPage = 0;
	private int currPage;
	private clientTest bean;
	User user;

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
		this.currPage = 0;
		this.books = gway.getRangeBooks(currPage);
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
		user = User.getInstance();
    	bean = bean.getInstance();
		if (SelectedBook != null && bean.callAccess(user.getSession(), "BookDelete")){
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
		if(currPage != 0){
		currPage = 0;
		books = gway.getRangeBooks(currPage);
		listItems.setAll(books);
		ListView.setItems(listItems);
		//ListView.refresh();
		RecordNumberDisplay.setText("Fetched books 1 to 50 of " + numOfPages);
		}else{
			System.out.println("Already on First Page");
		}
	}

	@FXML
	void LastPageHandler(ActionEvent event) {
		System.out.println("LP Clicked");
		if(currPage != lastPage){
		currPage = lastPage;
		books = gway.getRangeBooks(currPage);
		listItems.setAll(books);
		ListView.setItems(listItems);
		//ListView.refresh();
		RecordNumberDisplay.setText("Fetched books " +(currPage*50+1) + " to " + (currPage*50+50) + " of " + numOfPages);
		}else{
		System.out.println("Already on Last Page");
		}
	}
	
	@FXML
	void NextPageHandler(ActionEvent event) {
		System.out.println("Next Page Clicked");
		if(currPage != lastPage){
			currPage++;
			books = gway.getRangeBooks(currPage);
			listItems.setAll(books);
			ListView.setItems(listItems);
			//ListView.refresh();
			RecordNumberDisplay.setText("Fetched books " +(currPage*50+1) + " to " + (currPage*50+50) + " of " + numOfPages);
		}else{
			System.out.println("This is the last Page");
		}
	}

	@FXML
	void PrevPageHandler(ActionEvent event) {
		System.out.println("Prev Page Clicked");
		if(currPage != 0){
			currPage--;
			books = gway.getRangeBooks(currPage);
			listItems.setAll(books);
			ListView.setItems(listItems);
			//ListView.refresh();
			RecordNumberDisplay.setText("Fetched books " +(currPage*50+1) + " to " + (currPage*50+50) + " of " + numOfPages);
		}else{
			System.out.println("This is the first page");
		}
	}
	
	

	public void initialize() {// URL location, ResourceBundle resources
		listItems.setAll(books);
		ListView.setItems(listItems);
		this.RecordNumberDisplay.setText("Fetched books 1 to 50 of " + numOfPages);
	}
}

package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Book.Book;
import Book.Publisher;
import DataBase.AuthorTableGateway;
import DataBase.BookTableGateway;
import DataBase.PublisherTableGateway;
import Model.Author;
import assignment1.GenerateExcel;
import assignment1.UsefulFunctions;
import authentication.User;
import core.clientTest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;


public class MenuController extends ViewController {
	
	private Connection conn;
	private clientTest bean;
	User user;// = User.getInstance();
	private PublisherTableGateway pgtw;
	private BookTableGateway bgw;
	private ArrayList<Book> bookList;
	
	private static Logger logger = LogManager.getLogger();

	@FXML
	private MenuItem AddBookListMenuItem;

	@FXML
	private MenuItem AddBookMenuItem;

	@FXML
	private MenuItem AddAuthorMenuItem;
	
    @FXML
    private MenuItem AuthorListMenuItem;
    
    @FXML
    private MenuItem LoginMenuItem;

    @FXML
    private MenuItem logoutMenuItem;
    
    @FXML
    private MenuItem ReportMenuItem;

    @FXML
    private MenuItem closeMenuItem;
    
    @FXML
    private Pane centerPane;

    @FXML
    void MenuHandler(ActionEvent event) {
    	bean = bean.getInstance();
    	try {
    		AuthorTableGateway authorGateWay = new AuthorTableGateway(conn);
    		BookTableGateway bookGateway = new BookTableGateway(conn);
    		PublisherTableGateway publisherTableGateway = new PublisherTableGateway(conn);
    		
    		user = User.getInstance();
    		
    		
        	if(event.getSource() == closeMenuItem){
            	System.exit(0);
        	}else if(event.getSource() == LoginMenuItem) {
        		System.out.println("login was clicked");
        		loginHandler();
        	}else if(event.getSource() == logoutMenuItem) {
        		System.out.println("logout was clicked");
        		if(bean.callAccess(user.getSession(),"logout")) {
        			logoutMessanger(1);
        		    User.setUser(-1, -1);
        		}else{
        			logoutMessanger(0);
        		}
        	}else if(event.getSource() == AddAuthorMenuItem){
        		if(bean.callAccess(user.getSession(),"AddAuthor")) {
        		Author author = new Author(authorGateWay);
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		functions.SwitchView(functions.AuthorDetail,author);
        		}else {
        			unauthorizedMessage();
        		}
        	}
        	else if(event.getSource() == AuthorListMenuItem){
        		if(bean.callAccess(user.getSession(),"list")) {
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		functions.SwitchView(functions.AuthorList,authorGateWay.getAuthors());
        		}else {
        			unauthorizedMessage();
        		}
        	}
        	else if(event.getSource() == AddBookListMenuItem){//book list
        		if(bean.callAccess(user.getSession(),"list")) {
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		functions.SwitchView(functions.BookListView,null);
        		}else {
        			unauthorizedMessage();
        		}
        	}  	
        	else if(event.getSource() == AddBookMenuItem){ //Book detail
        		if(bean.callAccess(user.getSession(),"AddBook")) {
        		Book book = new Book(bookGateway);
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		functions.SwitchView(functions.BookDetail,book);
        		}else {
        			unauthorizedMessage();
        		}
        	}
        	else if(event.getSource() == ReportMenuItem){
        		logger.info("report button clicked");
        		if(bean.callAccess(user.getSession(), "Report")) {
        			reportManager();
        			//UsefulFunctions functions = UsefulFunctions.getInstance();
            		//functions.SwitchView(functions.ReportPage,null);
        		}
        	}
        	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("couldnt find files. MenuController");
			
		}

    }

    
    private void reportManager() {
    	GenerateExcel y = new GenerateExcel();
    	pgtw = new PublisherTableGateway(conn);
    	ObservableList<String> pubChoices =  FXCollections.observableArrayList();
    	ArrayList<Publisher> list = new ArrayList<Publisher>();
    	//ChoiceDialog<String> pubdialog;
    	TextField path = new TextField();
    	ChoiceBox<String> pcb = new ChoiceBox<String>();
    	DateTimeFormatter today = DateTimeFormatter.ofPattern("MM dd,yyyy HH:mm");
    	LocalDateTime now = LocalDateTime.now();
    	bookList = new ArrayList<>();
    	today.format(now);
    	bgw = new BookTableGateway(conn);
    	
    	
    	
    	list = pgtw.getPublishers();
    	Dialog<Pair<String,ChoiceBox<String>>> tdialog = new Dialog<>();
    	Button tb = new Button("Generate Process");
    	
    	ButtonType loginButtonType = new ButtonType("Generate Report", ButtonData.OK_DONE);
    	tdialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
    	
    	tdialog.setTitle("Report Genorator");
    	tdialog.setHeaderText("Which records would you like to produce?");
    	
    	
    	
    	for(Publisher temp: list) {
    	pubChoices.add(temp.getPublisherName().getValue());
    	}
    	
    	pcb.setItems((ObservableList<String>) pubChoices);
    	
    	
    	//pubdialog = new ChoiceDialog<>("Available Publishers",pubChoices);
    	
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));
    	
    	path.setPromptText("Path Name");
    	
    	grid.add(new Label("Path:"), 0, 0);
    	grid.add(path, 1, 0);
    	grid.add(new Label("Publisher"),0,1);
    	grid.add(pcb, 1, 1);
    	//grid.add(tb, 0, 2);
    	
    	tdialog.getDialogPane().setContent(grid);
    	Optional<Pair<String,ChoiceBox<String>>> result = tdialog.showAndWait();
    	
    	// Traditional way to get the response value.
    	//Optional<String> result = dialog.showAndWait();
    	String p = pcb.getValue();//result.get().getKey();
    	String l = path.getText();
    	Publisher temp = new Publisher(pgtw);
    	
    	if (result.isPresent() && p != null && !l.isEmpty()){
    		for(Publisher t: list) {
    			//System.out.println("1pub " + t.getPublisherName().getValue().toString());
    			if(t.getPublisherName().getValue().toString().equals(p)) {
    				temp.setId(t.getId());
    				temp.setPublisherName(t.getPublisherName().getValue().toString());
    				//System.out.println("1pub " + t.getPublisherName().getValue().toString());
    				break;
    			}
    		}
    		//System.out.println("pub " + temp.getPublisherName().getValue().toString());
    		//ArrayList<Book> eh = new ArrayList<>();
    		bookList = bgw.getBooksByPub(temp.getId());
    		//for(int i = 1; i<20;i++) {
    		//	eh.add(bookList.get(i));
    		//}
    		
    		//System.out.println("!!!! " + bookList.get(1).getTitle().toString());
    		y.generateExcel(l, p, today.format(now), bookList);
    	}
	}

	private void loginHandler() {
    	bean = bean.getInstance();
    	Dialog<Pair<String, String>> dialog = new Dialog<>();
    	dialog.setTitle("Login");
    	dialog.setHeaderText("Please enter your username and password");

    	// Set the icon (must be included in the project).
    	//dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

    	// Set the button types.
    	ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    	// Create the username and password labels and fields.
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField username = new TextField();
    	username.setPromptText("Username");
    	PasswordField password = new PasswordField();
    	password.setPromptText("Password");

    	grid.add(new Label("Username:"), 0, 0);
    	grid.add(username, 1, 0);
    	grid.add(new Label("Password:"), 0, 1);
    	grid.add(password, 1, 1);

    	// Enable/Disable login button depending on whether a username was entered.
    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    	loginButton.setDisable(true);

    	// Do some validation (using the Java 8 lambda syntax).
    	username.textProperty().addListener((observable, oldValue, newValue) -> {
    	    loginButton.setDisable(newValue.trim().isEmpty());
    	});

    	dialog.getDialogPane().setContent(grid);

    	// Request focus on the username field by default.
    	Platform.runLater(() -> username.requestFocus());

    	// Convert the result to a username-password-pair when the login button is clicked.
    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == loginButtonType) {
    	        return new Pair<>(username.getText(), password.getText());
    	    }
    	    return null;
    	});

    	Optional<Pair<String, String>> result = dialog.showAndWait();

    	result.ifPresent(usernamePassword -> {
    	    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
    	});
    	
    	int sessionId;
    	if((sessionId = bean.callLogin(username.getText(), password.getText())) != -1) {
    		System.out.println("Succesful login session Id = "+ sessionId);
    		user.setUser(sessionId, bean.callGetAccess(sessionId));
    		//System.out.println("!!!!!!!!!!! " + user.getSession() + " " + user.checkAccess());
    	}else {
    		loginErrorMessage();
    	}
    	
		
	}

	private void loginErrorMessage() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error!");
		alert.setHeaderText(null);
		alert.setContentText("Invalid username or password");
		alert.showAndWait();
	}
	
	private void unauthorizedMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error!");
		alert.setHeaderText(null);
		alert.setContentText("You do not have authorized permission for this feature!");
		alert.showAndWait();
	}

	private void logoutMessanger(int x) {
		if(x == 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success!");
			alert.setHeaderText(null);
			alert.setContentText("You have successfully logged out!");
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText("You are not logged in");
			alert.showAndWait();
		}
	}
	
	public void setConnection(Connection conn){
    	this.conn = conn;
    }
    
    public Connection getConnection(){
    	return conn;
    }
}








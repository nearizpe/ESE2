package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DataBase.AuthorTableGateway;
import DataBase.PublisherTableGateway;
import Model.Author;
import assignment1.UsefulFunctions;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;


public class MenuController extends ViewController {
	
	private Connection conn;
	
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
    private MenuItem closeMenuItem;
    
    @FXML
    private Pane centerPane;

    @FXML
    void MenuHandler(ActionEvent event) {
    	try {
    		AuthorTableGateway authorGateWay = new AuthorTableGateway(conn);
    		PublisherTableGateway publisherTableGateway = new PublisherTableGateway(conn);
    		
        	if(event.getSource() == closeMenuItem){
            	System.exit(0);
        	}
        	else if(event.getSource() == AddAuthorMenuItem){
        		Author author = new Author(authorGateWay);
        		AuthorDetailViewController detailViewController = new AuthorDetailViewController(author);
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		functions.SwitchView(this, detailViewController,"/View/AuthorDetailView.fxml");
        	}
        	else if(event.getSource() == AuthorListMenuItem){
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		AuthorListController controller = new AuthorListController(authorGateWay.getAuthors());
        		functions.SwitchView(this,controller,"/View/AuthorListView.fxml");
        	}
        	else if(event.getSource() == AddBookListMenuItem){
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		BookListViewController controller = new BookListViewController();
        		functions.SwitchView(this,controller,"/View/BookListView.fxml");
        	}  	
        	else if(event.getSource() == AddBookMenuItem){
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		BookDetailViewController controller = new BookDetailViewController(publisherTableGateway);
        		functions.SwitchView(this,controller,"/View/BookDetailView.fxml");
        	}
        	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("couldnt find files. MenuController");
		}

    }

    public void setConnection(Connection conn){
    	this.conn = conn;
    }
    
    public Connection getConnection(){
    	return conn;
    }
}








package assignment1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.text.View;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Book.Book;
import Controller.AuditTrailController;
import Controller.AuthorDetailViewController;
import Controller.AuthorListController;
import Controller.BookDetailViewController;
import Controller.BookListViewController;
import Controller.ViewController;
import DataBase.AuthorTableGateway;
import DataBase.BookTableGateway;
import DataBase.PublisherTableGateway;
import Model.Author;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class UsefulFunctions {
	private static UsefulFunctions single_instance = null;
	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static final int AuthorDetail = 1;
	public static final int AuthorList = 2;
	public static final int BookDetail = 3;
	public static final int BookListView = 4;
	public static final int AuditTrail = 5;

	
	private Connection conn;
	private BorderPane rootPane = null;

	public BorderPane getRootPane() {
		return rootPane;
	}

	public void setRootPane(BorderPane rootPane) {
		this.rootPane = rootPane;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	// private constructor restricted to this class itself
    private UsefulFunctions()
    {
    }
    
 // static method to create instance of Singleton class
    public static UsefulFunctions getInstance()
    {
        if (single_instance == null)
            single_instance = new UsefulFunctions();
 
        return single_instance;
    }
	
	public void SwitchView(int viewType,Object arg)throws Exception { //REFACTOR
		try {
			ViewController controller = null;
			URL fxmlFile = null;
			switch(viewType){
			case AuthorDetail:
				fxmlFile = this.getClass().getResource("/View/AuthorDetailView.fxml");
				controller = new  AuthorDetailViewController((Author)arg);
				break;
			case AuthorList:
				fxmlFile = this.getClass().getResource("/View/AuthorListView.fxml");
				controller = new AuthorListController(new AuthorTableGateway(conn).getAuthors());
				break;
			case BookDetail:
				fxmlFile = this.getClass().getResource("/View/BookDetailView.fxml");
				controller = new BookDetailViewController((Book) arg,new PublisherTableGateway(conn).getPublishers() );
				break;
			case BookListView:
				fxmlFile = this.getClass().getResource("/View/BookListView.fxml");
				controller = new BookListViewController(new BookTableGateway(conn));
				break;
			case AuditTrail:
				fxmlFile = this.getClass().getResource("/View/AuditTrailView.fxml");
				controller = new AuditTrailController((Book)arg,((Book) arg).getAuditTrail());
				break;
			}
			
			FXMLLoader loader = new FXMLLoader(fxmlFile);
			loader.setController(controller);
		
			Parent viewNode = loader.load();
			logger.info("Checking if null, "+ rootPane);
			rootPane.setCenter(viewNode);
		} catch (IOException e) {
			throw new Exception(e);
		}
			
		/*	toController.setRootNode(fromController.getRootNode());
			
			URL fxmlFile = this.getClass().getResource(resource);	
			FXMLLoader loader = new FXMLLoader(fxmlFile);
			
			loader.setController(toController);
			
			Parent contentView = loader.load();

			//get rid of reference to previous content view
			fromController.getRootNode().setCenter(null);
			logger.info("Swithching Views");

			
			fromController.getRootNode().setCenter(contentView);

		} catch (IOException e) {
			logger.error("error couldnt switch view");
			e.printStackTrace();
		}*/
	}
	
	


}

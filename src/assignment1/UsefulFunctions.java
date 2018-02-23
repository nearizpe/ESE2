package assignment1;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import javax.swing.text.View;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Controller.AuthorDetailViewController;
import Controller.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class UsefulFunctions {
	private static UsefulFunctions single_instance = null;
	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static final int AuthorDetail = 1;
	
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
	
	public void SwitchView(int viewType,Object arg){ //REFACTOR
		try {
			ViewController controller = null;
			URL fxmFile = null;
			switch(viewType){
			case AuthorDetail:
				"/View/AuthorDetailView.fxml"
				break;
			}
			
			FXMLLoader loader = new FXMLLoader(fxmlFile);
			loader.setController(controller);
		
			Parent viewNode = loader.load();
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

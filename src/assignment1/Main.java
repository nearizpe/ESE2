package assignment1;

import java.net.URL;
import java.sql.Connection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Controller.MenuController;
import DataBase.ConnectionFactory;

public class Main extends Application {
	
	private Connection conn;
	private static Logger logger = LogManager.getLogger(Main.class);
	
	@Override
	public void stop() throws Exception {
		logger.error("error Couldnt load window");
		super.stop();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		//load main view (the top-level container for View1 and View2)
				URL fxmlFile = this.getClass().getResource("/View/MainView.fxml");
				FXMLLoader loader = new FXMLLoader(fxmlFile);
				
				BorderPane rootNode = loader.load();
				Scene scene = new Scene(rootNode, 600, 400);
				stage.setTitle("Assignment 1");
				stage.setScene(scene);
				stage.show();		

				//now load View1
				MenuController controller = new MenuController();
				controller.setConnection(conn); //set connection
				controller.setRootNode((BorderPane) rootNode);

				fxmlFile = this.getClass().getResource("/View/Menu.fxml");
				loader = new FXMLLoader(fxmlFile);
			
				loader.setController(controller);
			
				Parent contentView = loader.load();
				
				rootNode.setTop(contentView);

				
	}

	public void init() throws Exception{
		super.init();
		
		logger.info("Creating connection");
		try{
			conn = ConnectionFactory.createConnection();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		//main thread blocks until launch returns (JAT thread terminates)
		launch(args);
	}
}
package assignment1;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Book.Book;
import Book.Publisher;
import Controller.MenuController;
import DataBase.BookTableGateway;
import DataBase.ConnectionFactory;
import DataBase.PublisherTableGateway;

public class Main extends Application {
	// CS 4743 Assignment 3 by Kevin Gonzales, Nicholas Arizpe
	private Connection conn;
	private static Logger logger = LogManager.getLogger(Main.class);

	@Override
	public void stop() throws Exception {
		logger.error("error Couldnt load window");
		super.stop();
		conn.close();
	}

	@Override
	public void start(Stage stage) throws Exception {

		// load main view (the top-level container for View1 and View2)

		UsefulFunctions func = UsefulFunctions.getInstance();
		func.setConn(conn);
		
		URL fxmlFile = this.getClass().getResource("/View/MainView.fxml");
		FXMLLoader loader = new FXMLLoader(fxmlFile);

		BorderPane rootNode = loader.load();
		Scene scene = new Scene(rootNode, 600, 400);
		stage.setTitle("Enterprise ");
		stage.setScene(scene);
		stage.show();

		// now load View1
		MenuController controller = new MenuController();
		controller.setConnection(conn); // set connection
		controller.setRootNode((BorderPane) rootNode);
		
		func.setRootPane((BorderPane) rootNode);

		fxmlFile = this.getClass().getResource("/View/Menu.fxml");
		loader = new FXMLLoader(fxmlFile);

		loader.setController(controller);

		Parent contentView = loader.load();

		rootNode.setTop(contentView);
		
		BookTableGateway bgw = new BookTableGateway(conn);
		RandomWords rw = new RandomWords();
		PublisherTableGateway pgw = new PublisherTableGateway(conn);
		List<Publisher> pubs = pgw.getPublishers();
		Random rand = new Random();
		int max = pubs.size();
		System.out.println("~~~~~~~~~~" + max);
		
		for(int i = 0; i< 10000; i++){
		Book temp = new Book(bgw);
		temp.setGateway(bgw);
		temp.setYearPublished(1994);
		temp.setSummary("this is a book");
		int randnum = rand.nextInt(max);
		temp.setPublisher(pubs.get(randnum));
		temp.setIsbn("isbn2134");
		temp.setTitle("Book"+String.valueOf(i));
		
		bgw.upDateBook(temp);
		
		}

	}

	public void init() throws Exception {
		super.init();

		logger.info("Creating connection");
		try {
			conn = ConnectionFactory.createConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// main thread blocks until launch returns (JAT thread terminates)
		launch(args);
		
	}
}
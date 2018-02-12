package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.ScrollPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Model.Author;
import assignment1.Main;
import assignment1.UsefulFunctions;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;

public class AuthorListController extends ViewController{
	private static Logger logger = LogManager.getLogger(Main.class);
	private ObservableList<Button> buttons = FXCollections.observableArrayList();
	private ArrayList<Author> authors;

	@FXML
    private ListView<Button> ListView;
	
	public AuthorListController(ArrayList<Author> authors){
		this.authors= authors;
	}
	
	
	@FXML
	void ListClick(MouseEvent event) {
		try {
			if (event.getClickCount() > 1) {
				logger.info("Double Clicked");
				UsefulFunctions functions = UsefulFunctions.getInstance();
				//Author temp = authors;
				AuthorDetailViewController detailViewController = new AuthorDetailViewController(authors.get(1));
				functions.SwitchView(this, detailViewController, "/View/fxml");
			}
		} catch (Exception e) {
			logger.error("couldnt find files");
		}
	}

	
	EventHandler<MouseEvent> buttonHandler = new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
	        //label.setText("Accepted");
			try {
				if (event.getClickCount() == 2) {
					Button btn = (Button) event.getSource();
					String id = btn.getId();
					logger.info("Double Clicked id is " + id);
					changeView(Integer.valueOf(id));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("couldnt find files");
			}
	    }
	};
	
	private void changeView(int id){
		AuthorDetailViewController detailViewController = new AuthorDetailViewController(authors.get(id));
		logger.info("sda");
		UsefulFunctions functions = UsefulFunctions.getInstance();
		functions.SwitchView(this, detailViewController,"/View/AuthorDetailView.fxml");
	}
	
	public void initialize(){//URL location, ResourceBundle resources
		for(int i = 0; i < authors.size();i++){
			Button newButton = new Button();
			newButton.setText(authors.get(i).getFirstName().getValue() + " " + authors.get(i).getLastName().getValue());
			newButton.setOnMouseClicked(buttonHandler);
			newButton.setId(Integer.toString(i));
			logger.info("id is " + newButton.getId());
			buttons.add(newButton);			
		}
		ListView.setItems(buttons);
	}

}

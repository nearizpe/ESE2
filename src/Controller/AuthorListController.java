package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

	@FXML
    void DoubleClickHandler(MouseEvent event) {
		try {
			if (event.getClickCount() == 2) {
				logger.info("Double Clicked");
				UsefulFunctions functions = UsefulFunctions.getInstance();
				AuthorDetailViewController detailViewController = new AuthorDetailViewController(authors.get(0));
				functions.SwitchView(this, detailViewController,"/View/fxml");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("couldnt find files");
		}
		
    }
	
	public void initialize(){//URL location, ResourceBundle resources
		for(int i = 0; i < authors.size();i++){
			Button newButton = new Button();
			newButton.setText(authors.get(i).getFirstName().getValue() + " " + authors.get(i).getLastName().getValue());
			
			buttons.add(newButton);			
		}
		ListView.setItems(buttons);
	}

}

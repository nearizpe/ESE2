package Controller;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

public class AuthorListController extends ViewController{
	private static Logger logger = LogManager.getLogger(Main.class);

	@FXML
	private Button Poe;

	@FXML
	private Button Akira;

	@FXML
	private Button George;

	@FXML
	private Button Hat;

	@FXML
	private Button Professor;

	public AuthorListController(ArrayList<Author> authors){
		for(int i = 0; i < authors.size();i++){
			System.out.println(authors.get(i).getId());
		}
	}
	
	
	@FXML
    void DoubleClickHandler(MouseEvent event) {
		try {
			if (event.getClickCount() == 2) {
				logger.info("Double Clicked");
				UsefulFunctions functions = UsefulFunctions.getInstance();
				AuthorDetailViewController detailViewController = new AuthorDetailViewController();
				functions.SwitchView(this, detailViewController,"/View/AuthorDetailView.fxml");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("couldnt find files");
		}
		
    }
	
	

}

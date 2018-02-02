package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Model.Author;
import Model.AuthorTableGateway;
import assignment1.UsefulFunctions;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;


public class MenuController extends ViewController {
	
	private static Logger logger = LogManager.getLogger();

	AuthorTableGateway authors = new AuthorTableGateway();
	
    @FXML
    private MenuItem AuthorListMenuItem;

    @FXML
    private MenuItem closeMenuItem;
    
    @FXML
    private Pane centerPane;

    @FXML
    void MenuHandler(ActionEvent event) {
    	try {
        	if(event.getSource() == closeMenuItem){
            	System.exit(0);
        	}
        	else{
        		UsefulFunctions functions = UsefulFunctions.getInstance();
        		//System.out.println(centerPane);
        		AuthorListController controller = new AuthorListController(authors.getAuthors());
        		functions.SwitchView(this,controller,"/View/AuthorListView.fxml");

        	}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("couldnt find files MenuController");
		}

    }

}








package assignment1;

import java.io.IOException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;


public class MenuController extends ViewController {
	
	private static Logger logger = LogManager.getLogger();


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
        		AuthorListController controller = new AuthorListController();
        		functions.SwitchView(this,controller,"/assignment1/AuthorListView.fxml");

        	}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("couldnt find files");
		}

    }

}








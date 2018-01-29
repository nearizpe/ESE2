package assignment1;

import java.io.IOException;
import java.net.URL;

import javax.swing.text.View;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Controller.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class UsefulFunctions {
	private static UsefulFunctions single_instance = null;
	private static Logger logger = LogManager.getLogger(Main.class);

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
	
	public void SwitchView(ViewController fromController,ViewController toController,String resource){
		try {
			toController.setRootNode(fromController.getRootNode());
			
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
		}
	}
}

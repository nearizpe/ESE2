package Controller;

import Book.Publisher;
import DataBase.PublisherTableGateway;
import assignment1.GenerateExcel;
import assignment1.UsefulFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ReportPageController extends ViewController{
	
	GenerateExcel ge;
	PublisherTableGateway pt;
	UsefulFunctions uf;
	
	@FXML
	private Button GenerateReportButton;

    @FXML
    private ComboBox<Publisher> PubList;

	@FXML
	private TextField PathText;

	@FXML
	void GenerateReportHandler(ActionEvent event) {
		
	}
	
	public ReportPageController() {
		
	}
	
	public void initialize() {
		uf = UsefulFunctions.getInstance();
		ge = new GenerateExcel();
		pt = new PublisherTableGateway(uf.getConn());
		//PubList.getItems().addAll(pt.getPublishers());
		
	}

}


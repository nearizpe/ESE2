package Controller;

import java.util.ArrayList;

import Model.AuditTrailModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AuditTrailController extends ViewController {

	ArrayList<AuditTrailModel> models;
	
    @FXML
    private Label AuditLabel;

    @FXML
    private Button BackButton;

    @FXML
    void BackHandler(ActionEvent event) {

    }
	
	public AuditTrailController(ArrayList<AuditTrailModel> list){
		this.models = list;
	}
}

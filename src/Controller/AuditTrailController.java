package Controller;

import java.util.ArrayList;

import Model.AuditTrailModel;

public class AuditTrailController extends ViewController {

	ArrayList<AuditTrailModel> models;
	
	public AuditTrailController(ArrayList<AuditTrailModel> list){
		this.models = list;
	}
}

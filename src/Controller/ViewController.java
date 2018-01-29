package Controller;

import javafx.scene.layout.BorderPane;

public class ViewController {
	
	private BorderPane rootNode;

	public BorderPane getRootNode(){
		return rootNode;
	}
	
	public void setRootNode(BorderPane rootNode) {
		this.rootNode = rootNode;
	}
}

package application;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

public class MenuScene extends AppScene {
	
	private VBox _vBox;
	private ObservableList<Node> _vBoxNodes;
	
	private Button _viewButton;
	private Button _createButton;
	private Button _quitButton;
	private Label _welcomeLabel;
	
	
	public MenuScene(App app) {
		super(app);
	}

	
	@Override
	public Scene setUpScene() {
		
		_vBox = new VBox();
		_vBoxNodes = _vBox.getChildren();
		
		
		_welcomeLabel = new Label("Welcome to VAR Encyclopedia");
		_viewButton = new Button("View Your Creations");
		_createButton = new Button("Create a new Creation");
		_quitButton = new Button("Quit");

		_viewButton.setOnAction(event -> { app.changeScene(4); });
		_createButton.setOnAction(event -> { app.changeScene(1); });
		_quitButton.setOnAction(event -> { System.exit(0); });

		
		_vBoxNodes.addAll(_welcomeLabel, _viewButton, _createButton, _quitButton);
		_vBox.setAlignment(Pos.CENTER);
		_vBox.setSpacing(20);

		return new Scene(_vBox, 600, 400);
	}
	
	
	@Override
	public void update() {
		//nothing to update
	}
}
package application;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class DisplayLinesScene extends AppScene{

	private VBox _vBox;
	private ObservableList<Node> _vBoxNodes;

	private ListView<String> _searchDisplay;
	private Label _selectionLabel;
	private Button _submitButton;
	private TextArea _linesTextArea;


	public DisplayLinesScene(App app) {
		super(app);
	}

	public Scene setUpScene() {

		_vBox = new VBox();
		_vBoxNodes = _vBox.getChildren();


		_searchDisplay = new ListView<String>();
		_selectionLabel = new Label("Please select the number of line to be read");
		_linesTextArea = new TextArea();
		_submitButton = new Button("Submit");


		_submitButton.setOnAction(event -> { 
			int numberOfLinesRequested;
			
			try {
				numberOfLinesRequested = Integer.parseInt(_linesTextArea.getText());
				
			} catch (Exception e) {
				
				Alert notAnInteger = new Alert(AlertType.INFORMATION);
				notAnInteger.setTitle("Invalid character");
				notAnInteger.setHeaderText(null);
				notAnInteger.setContentText("Please enter an Integer.");
				notAnInteger.showAndWait();
				
				return;
			}

			
			if (currentCreation.setNumberOfLinesToRead(numberOfLinesRequested)) {

				setGlobalCreation(currentCreation);
				app.changeScene(3);

			} else { 

				Alert noContentAlert = new Alert(AlertType.INFORMATION);
				noContentAlert.setTitle("Invalid Number Of Lines");
				noContentAlert.setHeaderText(null);
				noContentAlert.setContentText("Please enter a value from 1 to " + currentCreation.getMaxLines() + ".");
				noContentAlert.showAndWait();
			}		
		});


		_vBoxNodes.addAll(_searchDisplay, _selectionLabel, _linesTextArea, _submitButton);
		_vBox.setAlignment(Pos.CENTER);
		_vBox.setSpacing(20);

		return new Scene(_vBox, 600, 400);
	}

	
	public void update() {
		
		_linesTextArea.clear();
		
		
		ObservableList<String> searchDisplayItems = _searchDisplay.getItems();
		searchDisplayItems.clear();

		int count = 0;
		for (String line : currentCreation.getAllContent()) {
			searchDisplayItems.add(++count + ". " + line);
		}
	}
}

package application;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class GetCreationNameScene extends AppScene {
	
	private Media _media;
	private String _creationName;

	private VBox _vBox;
	private ObservableList<Node> _vBoxNodes;

	private Label _creationNameLabel;
	private TextArea _creationNameTextArea;
	private Button _submitNameButton;
	private ProgressBar _loadingBar;


	public GetCreationNameScene(App app) {
		super(app);
		
		_media = new Media();
	}

	
	@Override
	public Scene setUpScene() {

		_vBox = new VBox();
		_vBoxNodes = _vBox.getChildren();

		_creationNameLabel = new Label("What would you like to name your creation?");
		_creationNameTextArea = new TextArea();
		_submitNameButton = new Button("Create");
		_loadingBar = new ProgressBar();
		
		_loadingBar.setVisible(false);


		_submitNameButton.setOnAction(event -> { 

			_creationName = _creationNameTextArea.getText();
			currentCreation.setName(_creationName);
			

			if (_media.creationAlreadyExists(_creationName)) {

				Alert overrideAlert = new Alert(AlertType.CONFIRMATION, "Creation already exists. Would you like to override " + _creationName 
						+ "?", ButtonType.YES, ButtonType.CANCEL);
				overrideAlert.setHeaderText(null);
				overrideAlert.showAndWait();

				if (overrideAlert.getResult() == ButtonType.YES) {

					createMedia(_creationName);
				}

			} else {

				createMedia(_creationName);
			}
		});

		_vBoxNodes.addAll(_creationNameLabel, _creationNameTextArea, _submitNameButton, _loadingBar);
		_vBox.setAlignment(Pos.CENTER);
		_vBox.setSpacing(20);

		return new Scene(_vBox, 600, 400);

	}
	
	
	@Override
	public void update() {
		_creationNameTextArea.clear();
	}
	
	
	//Creates another thread which asks a Creation object to create the media associated with that object.
	public void createMedia(String creationName) {
		
		_loadingBar.setVisible(true);
		
		Thread thread = new Thread(new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				
				Platform.runLater(() -> {
					_loadingBar.progressProperty().bind(this.progressProperty());
				});
				
				currentCreation.createMedia();
				return null;
			}
			
			@Override
			protected void done() {
				
				Platform.runLater(() -> {
					
					_loadingBar.setVisible(false);
					
					Alert noContentAlert = new Alert(AlertType.INFORMATION);
					noContentAlert.setTitle("Creation created.");
					noContentAlert.setHeaderText(null);
					noContentAlert.setContentText("Your Creation " + _creationName + " has been created.");
					noContentAlert.show();

					setGlobalCreation(currentCreation);
					app.changeScene(0);
				});
			}
			
		});

		thread.start();
	}
}

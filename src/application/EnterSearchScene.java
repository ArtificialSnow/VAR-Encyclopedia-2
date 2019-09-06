package application;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class EnterSearchScene extends AppScene {

	Creation newCreation;
	String searchTerm;

	private VBox _vBox;
	private ObservableList<Node> _vBoxNodes;

	private Label _searchLabel;
	private TextArea _searchTextArea;
	private Button _searchButton;
	private Button _quitToMenuButton;
	private ProgressBar _loadingBar;


	public EnterSearchScene(App app) {
		super(app);
	}

	@Override
	public Scene setUpScene() {

		_vBox = new VBox();
		_vBoxNodes = _vBox.getChildren();

		_searchLabel = new Label("Please Enter your Wikipedia Search");
		_searchTextArea = new TextArea();
		_searchButton = new Button("Search");
		_quitToMenuButton = new Button("Quit back to menu");
		_loadingBar = new ProgressBar();
		_loadingBar.setVisible(false);

		_quitToMenuButton.setOnAction(event -> { app.changeScene(0); });
		_searchButton.setOnAction(event -> { 

			searchTerm = _searchTextArea.getText();
			_loadingBar.setVisible(true);


			Thread thread = new Thread(new Task<Void>() {

				@Override
				protected Void call() throws Exception {

					Platform.runLater(() -> {
						_loadingBar.progressProperty().bind(this.progressProperty());
					});

					newCreation = new Creation(searchTerm);
					return null;
				}

				@Override
				protected void done() {
					Platform.runLater(() -> {
						_loadingBar.setVisible(false);

						if (newCreation.contentDoesNotExist()) {
							Alert noContentAlert = new Alert(AlertType.INFORMATION);
							noContentAlert.setTitle("No Content Found");
							noContentAlert.setHeaderText(null);
							noContentAlert.setContentText("No Content Found on Wikipedia for search: " + searchTerm);
							noContentAlert.showAndWait();
						} else {
							setGlobalCreation(newCreation);
							app.changeScene(2);
						}
					});
				}	
			});	
			thread.start();
		});
		_vBoxNodes.addAll(_searchLabel, _searchTextArea, _searchButton, _quitToMenuButton, _loadingBar);
		_vBox.setAlignment(Pos.CENTER);
		_vBox.setSpacing(20);

		return new Scene(_vBox, 600, 400);
	}

	@Override
	public void update() {
		_searchTextArea.clear();
	}
}
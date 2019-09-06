package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class FileViewerScene extends AppScene {

	Media media;

	Button quitToMenubutton;
	Button deleteButton;
	Button playButton;
	ListView<String> listOfCreations;
	TextArea textArea;

	public FileViewerScene(App app) {
		super(app);

		media = new Media();
	}
	
	
	@Override
	public Scene setUpScene() {

		VBox vBox = new VBox();

		quitToMenubutton = new Button("Quit To Menu");
		quitToMenubutton.setOnAction(event -> { app.changeScene(0); });

		deleteButton = new Button("Delete");
		deleteButton.setOnAction(event -> { delete(); });

		playButton = new Button("Play");
		playButton.setOnAction(event -> { play(); });

		listOfCreations = new ListView<String>();

		vBox.getChildren().addAll(listOfCreations, deleteButton, playButton, quitToMenubutton);
		vBox.setAlignment(Pos.CENTER);

		return new Scene(vBox, 600, 400);
	}
	
	
	@Override
	public void update() {
		updateList();
	}

	
	//Updates the list of creations displayed in the GUI.
	public void updateList() {

		listOfCreations.getItems().clear();

		int count = 0;
		for (String creationName: media.listCreatedCreations()) {
			listOfCreations.getItems().add(++count + ". " + creationName.substring(0, creationName.length() -4));
		}
	}

	//Checks whether the user has selected a creation to play in the GUI and asks a Media object to play it.
	public void play() {
		String creationName = listOfCreations.getSelectionModel().getSelectedItem();

		if (creationName != null) {
			creationName = creationName.substring(3) + ".avi";

			media.play(creationName);

		} else {

			createNoOperandAlert();
		}

	}
	
	//Checks whether the user has selected a creation to delete in the GUI and asks a Media object to play it.
	public void delete() {
		String creationName = listOfCreations.getSelectionModel().getSelectedItem();

		if (creationName != null) {
			creationName = creationName.substring(3) + ".avi";

			Alert deletionConfirmation = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete " + creationName + "?", ButtonType.YES, ButtonType.CANCEL);
			deletionConfirmation.setHeaderText(null);
			deletionConfirmation.showAndWait();


			if (deletionConfirmation.getResult() == ButtonType.YES) {

				media.delete(creationName);
				updateList();
			}

		} else {

			createNoOperandAlert();
		}
	}

	//Creates an alert if the user has not selected a creation to play/delete.
	public void createNoOperandAlert() {

		Alert noContentAlert = new Alert(AlertType.INFORMATION);
		noContentAlert.setTitle("No Creation selected.");
		noContentAlert.setHeaderText(null);
		noContentAlert.setContentText("Please select a creation");
		noContentAlert.showAndWait();
	}
}

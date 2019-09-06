package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {	

	private Stage primaryStage;
	private ArrayList<AppScene> appSceneList; 


	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;

		setUp();
	}
	
	
	public void setUp() {
		setUpScenes();
		setUpFileDirectory();
		setUpPrimaryStage();
	}
	
	
	//Creates the required scenes and stores them in an ArrayList.
	public void setUpScenes() {
		
		appSceneList = new ArrayList<AppScene>();
		appSceneList.add(new MenuScene(this));
		appSceneList.add(new EnterSearchScene(this));
		appSceneList.add(new DisplayLinesScene(this));
		appSceneList.add(new GetCreationNameScene(this));
		appSceneList.add(new FileViewerScene(this));
	}

	
	//Sets the title and changes the scene to the main menu.
	public void setUpPrimaryStage() {

		primaryStage.setTitle("VAR Encyclopedia");
		changeScene(0);
	}
	
	
	//Creates a FileDirectory object to create the file directory.
	public void setUpFileDirectory() {
		
		FileDirectory fileDirectory = new FileDirectory();
		fileDirectory.create();
	}

	//Changes which scene is shown to the user.
	public void changeScene(int index) {
		AppScene nextAppScene = appSceneList.get(index);
		nextAppScene.update();
		
		primaryStage.setScene(nextAppScene.getScene());
		primaryStage.show();
	}
	
	//Sets the current Creation for all AppScene objects.
	public void setGlobalCreation(Creation creation) {	
		for (AppScene appScene : appSceneList) {
			appScene.updateCreation(creation);
		}
	}
}

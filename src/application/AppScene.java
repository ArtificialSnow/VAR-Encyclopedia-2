package application;

import javafx.scene.Scene;

public abstract class AppScene {
	
	protected App app;
	protected Scene scene;
	protected Creation currentCreation;
	
	public AppScene(App app) {
		this.app = app;
		
		scene = setUpScene();
	}
	
	
	//Creates the scene object associated with an AppScene, and all the object it contains.
	abstract Scene setUpScene();
	
	//Resets values of objects within scenes and other objects which are required to reset or update before a certain scene is displayed again.
	abstract void update();
	
	//Returns the scene for a particular AppScene class.
	public Scene getScene() {
		return scene;
	}
	
	//Updates the Creation object within an AppScene class.
	public void updateCreation(Creation creation) {
		currentCreation = creation;
	}
	
	//Call to the main controller to update the current Creation object within all AppScene classes.
	public void setGlobalCreation(Creation currentCreation) {
		app.setGlobalCreation(currentCreation);
	}
}

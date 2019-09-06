package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Media {

	File creationsFile;

	public Media() {
		creationsFile = new File("./Creations");
	}
	
	//Lists all files in the current directory that end in .avi, and sorts them in alphabetical order.
	public List<String> listCreatedCreations() {
		
		File[] listOfFiles = creationsFile.listFiles();
		ArrayList<String> arrayListOfFileNames = new ArrayList<String>();

		for (File creation: listOfFiles) {
			if (creation.getName().contains(".avi")) {
				arrayListOfFileNames.add(creation.getName());
			}
		}

		Collections.sort(arrayListOfFileNames, String.CASE_INSENSITIVE_ORDER);
		
		return arrayListOfFileNames;
	}
	
	//Checks whether there already exists a creation with a certain name.
	public boolean creationAlreadyExists(String creationName) {
		
		File[] listOfFiles = creationsFile.listFiles();

		for (File file : listOfFiles) {
			if (file.getName().equals(creationName + ".avi")) {
				return true;
			}
		}

		return false;
	}

	//Plays a creation.
	public void play(String creationName) {

		String[] playCommands = { "/bin/bash", "-c", "ffplay -i -autoexit ./Creations/" + creationName + " &> /dev/null"};
		ProcessBuilder playBuilder = new ProcessBuilder(playCommands);

		try {

			playBuilder.start();

		} catch (Exception e) {

			System.out.println("Error playing file.");
		}

	}
	
	//Deletes a creation.
	public void delete(String creationName) {

		String[] deleteCommands = { "/bin/bash", "-c", "rm -f ./Creations/" + creationName };
		ProcessBuilder deleteBuilder = new ProcessBuilder(deleteCommands);

		Process deleteProcess;

		try {

			deleteProcess = deleteBuilder.start();
			deleteProcess.waitFor();

		} catch (Exception e) {

			System.out.println("Error deleting file.");
		}


	}
}

package application;

public class FileDirectory {
	
	private final String pathToBash;
	private final String checkIfCreationsFolderExists;
	private final String makeCreationsFolder;
	private final String makeTextFilesFolder;
	private final String makeAudioFilesFolder;
	
	public FileDirectory() {
		
		pathToBash = "/bin/bash";
		checkIfCreationsFolderExists = "[ ! -d \"Creations\" ]";
		makeCreationsFolder = "mkdir Creations; ";
		makeTextFilesFolder = "mkdir -p Creations/.TextFiles; ";
		makeAudioFilesFolder = "mkdir -p Creations/.AudioFiles; ";
	}
	
	//Checks if the required files to store media exist in the current directory and creates them if they do not.
	public void create() { 

		String[] checkFilesCommands = { pathToBash, "-c", checkIfCreationsFolderExists };
		ProcessBuilder checkFilesBuilder = new ProcessBuilder(checkFilesCommands);

		try {

			Process checkFilesProcess = checkFilesBuilder.start();
			int exitStatus = checkFilesProcess.waitFor();


			if (exitStatus == 0) {

				String[] createFilesCommands = { pathToBash, "-c", makeCreationsFolder + makeTextFilesFolder + makeAudioFilesFolder };
				ProcessBuilder createFilesBuilder = new ProcessBuilder(createFilesCommands);

				Process createFilesProcess = createFilesBuilder.start();
				createFilesProcess.waitFor();
			}

		} catch (Exception e) {

			System.out.println("Error - Unable to ensure File Directory Exists");
		}


	}	
}
package application;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Creation {
	
	File _textFile;
	
	private String _creationName;
	private String _searchTerm;
	private Wikipedia _wikipedia;
	private int _numberOfLinesToRead;


	public Creation(String searchTerm) {
		_searchTerm = searchTerm;
		_wikipedia = new Wikipedia(searchTerm);	
	}

	
	//Creates the media associated with a creation.
	public void createMedia() {
		
		createTextFile();
		createAudioFile();
		createVideo();	
	}
	
	
	//Creates the text file associated with the creation.
	public void createTextFile() {
		
		File dir = new File("Creations/.TextFiles");
		dir.mkdir();
		_textFile = new File (dir,  _creationName + ".txt");

		
		try {
			_textFile.createNewFile();
		} catch (IOException e1) {
			System.out.println("Error creating text file.");
		}

		
		try {
			PrintWriter writer = new PrintWriter(_textFile, "UTF-8");
			
			for (int i = 0; i < _numberOfLinesToRead; i++) {
				writer.println(_wikipedia.getContent().get(i));
			}
			
			writer.close();
		} catch (Exception e) {
			System.out.println("Error in writing to text file");
		}
	}

	
	//Creates the audio file associated with the creation.
	public void createAudioFile() {
		
		String audioFile = "./Creations/.AudioFiles/" + _creationName + ".wav";
		String[] createAudioCommands = { "/bin/bash", "-c", "espeak -f " + _textFile + " -w " + audioFile };
		ProcessBuilder createAudioBuilder = new ProcessBuilder(createAudioCommands);
		Process createAudioProcess;

		try {
			createAudioProcess = createAudioBuilder.start();
			createAudioProcess.waitFor();
		} catch (Exception e) {
			System.out.print("Error Creating Audio File.");
		}
	}

	
	//Creates the video file associated with the creation.
	public void createVideo() {
		
		ProcessBuilder createVideoBuilder = new ProcessBuilder("sh", "-c", "./createVideo.sh " + _creationName + " " + _searchTerm);
		Process createVideoProcess;

		try {
			createVideoProcess = createVideoBuilder.start();
			createVideoProcess.waitFor();
		} catch (Exception e) {
			System.out.print("Error Creating Video File.");
		}
	}
	
	
	//Returns the copy of the ArrayList of the lines associated with a wikipedia search (which it gets from a Wikipedia object).
	public List<String> getAllContent() {
		return _wikipedia.getContent();
	}

	
	//Checks whether content exists for a particular search (calls Wikipedia object).
	public boolean contentDoesNotExist() {
		return _wikipedia.contentDoesNotExist();
	}	

	
	//Returns the number of lines of content associated with a particular wikipedia search.
	public int getMaxLines() {
		return getAllContent().size();
	}

	
	//Sets the name of the creation.
	public void setName(String name) {
		_creationName = name;
	}

	
	//Sets the number of lines to read in the media, only if it is valid.
	//Returns true if valid number of lines requested, false otherwise.
	public boolean setNumberOfLinesToRead(int numRequested) {

		if ((numRequested > 0) && (numRequested <= getMaxLines())){

			_numberOfLinesToRead = numRequested;

			return true;
		}

		return false;
	}
}

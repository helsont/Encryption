package encrypter;

import java.io.File;

public class IsNotFileException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IsNotFileException(File f) {
		super("File : \"" +f.getPath() + " \" is not a file.");
	}
}

package encrypter;

import java.io.File;

public class IsNotFolderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8549947994009925448L;
	
	public IsNotFolderException(File f) {
		super("File : \"" +f.getPath() + " \" is not a folder.");
	}
}

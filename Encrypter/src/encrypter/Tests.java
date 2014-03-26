package encrypter;

import java.io.File;

public class Tests {
	
	public static void main(String[] args) throws IsNotFileException {
		
	}
	
	public static void checkHideFile() throws IsNotFileException {
		String location = Constants.loc + File.separator + "Test Folder" + File.separator;
		String file_name = "mbdtf.png";
		File f = new File (location + file_name);
		FileUtils.makeHidden(f);
		System.out.println("Sucess!");
	}
	
	public static void checkHideFolder() {
		String location = Constants.loc + File.separator + "Test Folder" + File.separator;
		String file_name = "mbdtf.png";
		File f = new File (location + file_name);
		//FileUtils.
		System.out.println("Sucess!");
	}
	
	public static void checkCreateFolderAt() {
		String location = Constants.loc + File.separator + "Test Folder";
		File f = new File (location);
		FileUtils.createSubdirectoryInFolder(f, "the new new");
		System.out.println("Sucess!");
	}
	
	public static void makeFolderHidden() {
		String location = Constants.loc + File.separator + "Test Folder";
		File f = new File (location);
		FileUtils.makeFilesInDirectoryHidden(f);
		System.out.println("Sucess!");
	}
}

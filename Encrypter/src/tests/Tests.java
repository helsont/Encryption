package tests;

import java.io.File;

import tree.Branch;

import encrypter.Constants;
import encrypter.FileUtils;
import encrypter.IsNotFileException;

public class Tests {

	public static void main(String[] args) throws IsNotFileException,
			InterruptedException {

		populateTree();
	}

	public static void populateTree() {
		new Branch(Constants.loc + File.separator + "Documents");
	}

	public static void createFolder() {
		String[] args = { Constants.loc, "a35lk4jef" };
		FileUtils.createFolder(args);
	}

	public static void checkHideFile() throws IsNotFileException,
			InterruptedException {
		String location = Constants.loc + File.separator + "Test Folder"
				+ File.separator;
		String file_name = "mbdtf";
		File f = new File(location + file_name);
		FileUtils.hide(f);
		System.out.println("Sucess!");
	}

	public static void checkUnhideFile() throws IsNotFileException {
		String location = Constants.loc + File.separator + "Test Folder"
				+ File.separator;
		String file_name = ".mbdtf";
		File f = new File(location + file_name);
		FileUtils.unhide(f);
		System.out.println("Sucess!");
	}

	public static void checkHideFolder() throws InterruptedException {
		String location = Constants.loc + File.separator + "Test Folder";
		File f = new File(location);
		FileUtils.hide(f);
		System.out.println("Sucess!");
	}

	public static void checkUnhideFolder() {
		String location = Constants.loc + File.separator + ".Test Folder";
		File f = new File(location);
		FileUtils.unhide(f);
		System.out.println("Sucess!");
	}

	public static void checkCreateFolderAt() {
		String location = Constants.loc + File.separator + "Test Folder";
		File f = new File(location);
		FileUtils.createSubdirectoryInFolder(f, "the new new");
		System.out.println("Sucess!");
	}

	public static void makeFolderHidden() throws InterruptedException {
		String location = Constants.loc + File.separator + "Test Folder";
		File f = new File(location);
		FileUtils.hide(f);
		System.out.println("Sucess!");
	}
}

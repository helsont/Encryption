package password;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import encrypter.Constants;
import encrypter.Encrypter;
import encrypter.FileUtils;

public class PasswordCreator {

	private String user;
	private char[] password;
	private Encrypter en;
	private String subdir;

	public static final String PWD_SHORT = "Password must be atleast 6 characters long.",
			VALID = "Password valid.",
			USER_SHORT = "Usernames must be atleast 6 characters long.",
			WHITESPACE = "Remove all whitespace in the username.",
			ILLEGAL_CHARACTER = "Remove all non letter or number characters.";

	public PasswordCreator() {
		en = new Encrypter(Constants.loc);
	}

	public void setUserAndPassword(String user, char[] pass) {
		this.user = user;
		this.password = pass;
		createFolder();
	}

	private final String MAC_LOCATION = Constants.loc;
	private final String FILE_NAME = createRandomString();

	private void createFolder() {
		String folderName = createRandomString();
		File folder = FileUtils.createFolder(MAC_LOCATION, folderName);

		Encrypter e = new Encrypter(folder.getPath());

		// Encrypt username and password
		e.encryptString(folder.getPath() + File.separator + FILE_NAME, user
				+ "|" + String.copyValueOf(password));

		// Clear username and password information as soon as possible
		clearInfo();

		// Hide the folder
		try {
			FileUtils.hide(folder);
			Thread.sleep(3000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		saveDir(folderName, FILE_NAME);

		Encrypter d = new Encrypter(MAC_LOCATION + File.separator + "."
				+ folderName);
		try {
			d.decryptPassword(new File(MAC_LOCATION + File.separator + "." + folderName
					+ File.separator + "." + FILE_NAME));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void saveDir(String folder_name, String key_name) {
		File newfolder = new File(MAC_LOCATION, Constants.Clock);
		FileUtils.createFolder(newfolder);
		FileUtils.writeToFile(MAC_LOCATION + File.separator + Constants.Clock
				+ File.separator + Constants.Clock2, folder_name + "|"
				+ key_name);
		try {
			FileUtils.hide(newfolder);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String createRandomString() {
		String str = "";
		Random r = new Random();
		str += getCharForNumber(1 + r.nextInt(25));
		for (int i = 0; i < 19; i++)
			str += r.nextInt(10);
		return str;
	}

	public static String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char) (i + 64)) : null;
	}

	private void clearInfo() {
		user = null;
		for (int i = 0; i < password.length; i++) {
			password[i] = ' ';
		}
		password = new char[0];
	}

	public static String checkCredentials(String user, char[] pass) {
		char[] userchar = user.toCharArray();
		if (userchar.length == 0)
			return USER_SHORT;
		for (int i = 0; i < userchar.length; i++) {
			if (userchar[i] == ' ')
				return WHITESPACE;
			if (!Character.isLetterOrDigit(userchar[i]))
				return ILLEGAL_CHARACTER;
		}
		if (pass.length < 6)
			return PWD_SHORT;
		for (int i = 0; i < pass.length; i++) {
			if (!Character.isLetterOrDigit(pass[i]))
				return ILLEGAL_CHARACTER;
		}
		return VALID;
	}
}
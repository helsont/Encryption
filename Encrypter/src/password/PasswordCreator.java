package password;

import java.io.File;
import java.util.Random;

import encrypter.Constants;
import encrypter.Encrypter;
import encrypter.FileUtils;

public class PasswordCreator {

	private String user;
	private char[] password;
	private Encrypter en;
	private String subdir;

	public static final String SHORT = "Password must be atleast 6 characters long.",
			VALID = "Password valid.";

	public PasswordCreator() {
		en = new Encrypter(Constants.loc);
	}

	public void setUserAndPassword(String user, char[] pass) {
		this.user = user;
		this.password = pass;
		createFolder();
	}

	private void createFolder() {
		//String folder_name = create
		File f = new File(Constants.loc);
		FileUtils.createFolder(f);
		//FileUtils.makeHidden(f)
	}

	private String createRandomString() {
		String str = "";
		Random r = new Random();
		for (int i = 0; i < 20; i++) 
			str += r.nextInt();
		return str;
	}

	private void clearInfo() {
		user = null;
		for (int i = 0; i < password.length; i++) {
			password[i] = ' ';
		}
		password = new char[0];
	}

	public static String checkPassword(char[] pass) {
		if (pass.length < 6)
			return SHORT;
		return VALID;
	}
}
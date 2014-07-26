package password;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import encrypter.Constants;
import encrypter.Encrypter;
import encrypter.FileUtils;

public class PasswordChecker {

	public String username;
	public char[] password;

	public PasswordChecker(String username, char[] password) {
		this.username = username;
		this.password = password;
	}

	public boolean checkCredentials() {
		char[] txt = null;
		try {
			txt = FileUtils.getTextFromFile(Constants.loc + File.separator
					+ "." + Constants.Clock + File.separator + "."
					+ Constants.Clock2);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		char[] loc = Arrays.copyOfRange(txt, 21, 41);
		String str_loc = String.valueOf(loc);

		char[] loc2 = Arrays.copyOfRange(txt, 0, 20);
		String path = String.valueOf(loc2);

		char[] orig = null;
		try {
			orig = FileUtils.getTextFromFile(Constants.loc + File.separator
					+ "." + path + File.separator + "." + str_loc);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Encrypter d = new Encrypter(Constants.loc + File.separator + "." + path);
		try {
			d.decryptPassword(new File(Constants.loc + File.separator + "."
					+ path + File.separator + "." + str_loc));
		} catch (Error e1) {
			// Quiet
		} catch (IllegalBlockSizeException e1) {
			// Quiet
		} catch (BadPaddingException e1) {
			// Quiet
		} catch (IOException e1) {
			// Quiet
		}

		char[] filetxt = null;
		try {
			filetxt = FileUtils.getTextFromFile(Constants.loc + File.separator
					+ "." + path + File.separator + "." + str_loc);
		} catch (IOException e1) {
			// Quiet
		}
		// out.println(filetxt);
		char[] comp1 = null, comp2 = null;
		int stop = 0;

		for (int i = 0; i < filetxt.length; i++) {
			if (filetxt[i] == '|')
				stop = i;
		}
		comp1 = Arrays.copyOfRange(filetxt, 0, stop);
		comp2 = Arrays.copyOfRange(filetxt, stop + 1, filetxt.length);

		char[] tty = username.toCharArray();
		if (comp1.length != tty.length)
			return false;
		if (comp2.length != password.length)
			return false;
		for (int x = 0; x < comp1.length; x++)
			if (comp1[x] != tty[x])
				return false;
		for (int x = 0; x < comp2.length; x++)
			if (comp2[x] != password[x])
				return false;

		FileUtils.writeToFile(Constants.loc + File.separator + "." + path
				+ File.separator + "." + str_loc, String.copyValueOf(orig));

		for (int x = 0; x < comp1.length; x++)
			comp1[x] = ' ';
		for (int x = 0; x < comp2.length; x++)
			comp2[x] = ' ';
		username = null;
		for (int x = 0; x < password.length; x++)
			password[x] = ' ';
		return true;
	}
}

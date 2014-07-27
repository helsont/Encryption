package secure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import main.Constants;

import utils.OSValidator;


public class Key implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8782860930086862904L;
	private transient KeyGenerator keyGenerator;
	private SecretKey secretKey;
	private transient String location;

	public Key(String loc) {
		this.location = loc;

		try {
			keyGenerator = KeyGenerator.getInstance("DES");
			secretKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public SecretKey getKey() {
		return secretKey;
	}

	public void saveKey() {
		OutputStream file;
		OutputStream buffer;
		ObjectOutput output;
		String fullFileName = location + Constants.NAME + ".ser";
		try {
			File f = new File(location + Constants.NAME + ".ser");
			f.createNewFile();

			file = new FileOutputStream(location + Constants.NAME + ".ser");
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(this);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		makeKeyHidden(location + Constants.NAME + ".ser");
	}

	public static Key getKey(String loc) {
		InputStream file;
		InputStream buffer;
		ObjectInput input;
		try {
			int OS = OSValidator.getOS();
			if (OS == OSValidator.MAC)
				file = new FileInputStream(loc + "." + Constants.NAME + ".ser");
			else if (OS == OSValidator.WINDOWS)
				file = new FileInputStream(loc + Constants.NAME + ".ser");
			else
				file = new FileInputStream(loc + Constants.NAME + ".ser");
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			Key temp = (Key) input.readObject();
			file.close();
			buffer.close();
			input.close();
			return temp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new Error("ERROR CODE: " + Constants.ERROR_COULD_NOT_FIND_KEY);
	}

	private void makeKeyHidden(String loc) {
		try {
			int OS = OSValidator.getOS();
			if (OS == OSValidator.MAC) {
				File f = new File(loc);
				f.renameTo(new File(location + "." + Constants.NAME + ".ser"));
			} else if (OS == OSValidator.WINDOWS) {
				Runtime.getRuntime().exec(
						"attrib +H " + System.getProperty("user.home")
								+ File.separator + Constants.NAME + ".ser");
			} else
				System.out.println("Unable to be hidden.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

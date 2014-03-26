package encrypter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Encrypter {
	private Key key;
	private Cipher cipher;
	public static EncryptionType encryptionType;
	private byte[] data;
	private String loc = System.getProperty("user.home");
	private Logger logger = Logger.getLogger("Encrypter");

	public Encrypter() {
		try {
			if (encryptionType == null)
				encryptionType = new EncryptionType("DES");
			cipher = Cipher.getInstance(encryptionType.getType());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public Encrypter(String location) {
		this();
		this.loc = location;
	}

	public void createKey() {
		key = new Key(loc + File.separator);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key.getKey());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		System.out.println("Key created.");
	}

	/**
	 * Encrypt one file, no subdirectory creation. Overwrites existing files
	 * with encrypted data.
	 * 
	 * @param file
	 *            The file to encrypt.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void encryptOneFileNFC(File file) throws FileNotFoundException,
			IOException {
		createKey();
		key.saveKey();
		data = getBytes(file);
		byte[] encrypted;
		try {
			encrypted = cipher.doFinal(data);
			writeDataNF(file, PATH_TO_FILE, encrypted);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Encrypt folder, no subdirectory creation. Overrwrites existing files in
	 * director with encrypted data.
	 * 
	 * @param files
	 *            The files to encrypt.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public void encryptFolderNFC(ArrayList<File> files)
			throws FileNotFoundException, IOException,
			IllegalBlockSizeException, BadPaddingException {
		createKey();
		key.saveKey();
		// createNewFolder(files.get(0).getParent() + File.separator);
		for (int i = 0; i < files.size(); i++) {
			data = getBytes(files.get(i));
			byte[] encrypted;
			encrypted = cipher.doFinal(data);
			writeToFile(files.get(i), encrypted);
		}
	}

	private final String PATH_TO_FILE = "New Folder";
	private String path_to_encrypted_folder;

	public void createNewFolder(String path) {
		path_to_encrypted_folder = path + Constants.SEPERATOR + PATH_TO_FILE;
		System.out.println("Creating this:" + path_to_encrypted_folder);
		File folder = new File(path_to_encrypted_folder);
		int i = 0;
		while (folder.exists()) {
			i++;
			path_to_encrypted_folder = path + File.separator + PATH_TO_FILE + i;
			folder = new File(path + File.separator + PATH_TO_FILE + i);
		}
		folder.mkdir();
	}
	
	/**
	 * Writes data in the same folder as the original data.
	 * @param f The original file.
	 * @param b The data to write.
	 */
	private void overrwriteData(File f, byte[] b) {
		try {
			FileOutputStream fos = new FileOutputStream(f.getPath());
			fos.write(b);
			fos.close();
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		}
	}


	/**
	 * Write data to a new folder.
	 * @param f The file to write
	 * @param folder_name The name of the new folder
	 * @param b The data to write
	 */
	private void writeDataNF(File f, String folder_name, byte[] b) {
		try {
			FileUtils.createSubdirectoryInFolder(f, folder_name);
			FileOutputStream fos = new FileOutputStream(f.getParent()
					+ File.separator + folder_name + File.separator
					+ f.getName());
			fos.write(b);
			fos.close();
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		}
	}

	private void writeToFile(File f, byte[] b) {
		try {
			// File newFile = new File(path_to_encrypted_folder +
			// File.separator+ f.getName());
			// newFile.createNewFile();
			// FileOutputStream fos = new FileOutputStream(
			// path_to_encrypted_folder + File.separator + f.getName());
			FileOutputStream fos = new FileOutputStream(f.getParent()
					+ File.separator + f.getName());
			fos.write(b);
			fos.close();
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		}
	}

	private byte[] getBytes(File f) throws FileNotFoundException, IOException {
		byte b[] = new byte[(int) f.length()];
		FileInputStream fileInputStream = new FileInputStream(f.getPath());
		fileInputStream.read(b);
		return b;
	}

	public void decrypt(File f) throws FileNotFoundException, IOException,
			IllegalBlockSizeException, BadPaddingException {
		key = Key.getKey(loc + File.separator);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key.getKey());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		byte[] data = getBytes(f);
		byte[] decrypted;

		decrypted = cipher.doFinal(data);
		writeToFile(f, decrypted);

	}
}

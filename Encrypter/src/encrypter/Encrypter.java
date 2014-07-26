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

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Encrypter {
	private Key key;
	private Cipher cipher;
	public static EncryptionType encryptionType;
	private byte[] data;
	private String loc;
	private String fldr = "8CZv09bQ";

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

	public void setLoc() {
		loc = System.getProperty("user.home") + File.separator + "." + fldr;
		FileUtils.createFolder(loc);
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

	public void encryptString(String file_path, String str) {
		createKey();
		key.saveKey();
		byte[] encrypted;
		byte[] text = str.getBytes();
		try {
			encrypted = cipher.doFinal(text);
			writeToFile(file_path, encrypted);
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

		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).isDirectory()) {
				encryptFolderNoKeyCreation(files.get(i).listFiles());
				continue;
			}

			data = getBytes(files.get(i));
			byte[] encrypted;
			encrypted = cipher.doFinal(data);
			overrwriteData(files.get(i), encrypted);
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
	public void encryptFolderNoKeyCreation(File[] files)
			throws FileNotFoundException, IOException,
			IllegalBlockSizeException, BadPaddingException {
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				if (files[i].isDirectory()) {
					encryptFolderNoKeyCreation(files[i].listFiles());
					continue;
				}
			}
			data = getBytes(files[i]);
			byte[] encrypted;
			encrypted = cipher.doFinal(data);
			overrwriteData(files[i], encrypted);
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
	 * 
	 * @param f
	 *            The original file.
	 * @param b
	 *            The data to write.
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
	 * 
	 * @param f
	 *            The file to write
	 * @param folder_name
	 *            The name of the new folder
	 * @param b
	 *            The data to write
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

	private void writeToFile(String file_path, byte[] encrypted) {
		try {
			FileOutputStream fos = new FileOutputStream(file_path);
			fos.write(encrypted);
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

	public void decryptPassword(File f) throws FileNotFoundException,
			IOException, IllegalBlockSizeException, BadPaddingException {
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

	public void decrypt(File f) throws FileNotFoundException, IOException,
			IllegalBlockSizeException, BadPaddingException {
		String file_name = f.getParent() + File.separator + "output";

		ZipUtils.extract(f);
		f.delete();
		File output = new File(file_name);
		key = Key.getKey(loc + File.separator);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key.getKey());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		if (f.isDirectory())
			return;
		FileInputStream fileInput = new FileInputStream(output);
		FileOutputStream fileOutput = null;
		fileOutput = new FileOutputStream(f.getParent() + File.separator
				+ "file");
		try {
			int data;
			byte[] arr = new byte[1024];
			// For each byte read it in from the input file
			// and write it to the output file
			while ((data = fileInput.read(arr)) != -1) {
				int cut = find(arr);

			}

		} catch (IOException e) {
			// Catch the IO error and print out the message
			System.out.println("Error message: " + e.getMessage());
		} finally {
			// Must remember to close streams
			// Check to see if they are null in case there was an
			// IO error and they are never initialized
			if (fileInput != null) {
				fileInput.close();
			}
		}
		File[] list = output.listFiles();
		for (int i = 0; i < list.length; i++) {
			byte[] data = getBytes(f);
			byte[] decrypted;

			decrypted = cipher.doFinal(data);
			writeToFile(f, decrypted);
		}
	}

	public int find(byte[] data) {
		byte[] token = eof.getBytes();
		int start = -1;
		int TRUE = 1, INVALID = -1;
		for (int idx = 0; idx < data.length; idx++) {
			int res = compare(data, token, idx, token.length);
			if (res == TRUE && start == INVALID)
				start = idx;
			if (res == TRUE)
				c++;
			else {
				c = 0;
				start = INVALID;
			}
		}
		return start;

	}

	public int compare(byte[] a, byte[] b, int off, int len) {
		for (int i = 0; i < len; i++)
			if (a[off + i] != b[i])
				return 1;
		return -1;
	}

	private String eof = "waC@a8uqe*ruswah";
	private int c;

	private byte[] getArray(ArrayList<Byte> bytesList) {
		byte[] bytes = new byte[bytesList.size()];
		for (int i = 0; i < bytesList.size(); i++) {
			bytes[i] = bytesList.get(i);
		}

		return bytes;
	}

	public void addBytes(ArrayList<Byte> list, byte[] arr) {
		for (int i = 0; i < arr.length; i++)
			list.add(arr[i]);

	}

	private void append(ArrayList<Byte> list) {
		byte[] b = eof.getBytes();
		addBytes(list, b);
	}

	public void encryptFolder(ArrayList<File> f) throws FileNotFoundException,
			IllegalBlockSizeException, BadPaddingException, IOException {
		ZipUtils.zip(new File(f.get(0).getParent()), new File(f.get(0)
				.getParent() + File.separator + "output.zip"));

	}
}

package encrypter;

import java.awt.Cursor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import frames.MainFrame;

import net.lingala.zip4j.exception.ZipException;

import secure.Key;
import tree.Branch;
import tree.Leaf;
import tree.Node;
import utils.FileUtils;
import utils.ZipUtils;

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

	public void initEncryptKey() {
		key = new Key(loc + File.separator);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key.getKey());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		key.saveKey();
		System.out.println("Key created.");
	}

	public void initDecryptKey() {
		key = Key.getKey(loc + File.separator);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key.getKey());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		System.out.println("Key created.");
	}

	public void encryptString(String file_path, String str) {
		initEncryptKey();
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

	public void setWaitCursor() {

		MainFrame.frame.setCursor(Cursor
				.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void setNormCursor() {

		MainFrame.frame.setCursor(Cursor
				.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	public void encryptNode(Node selected_file) throws FileNotFoundException,
			IOException, ZipException {
		setWaitCursor();
		File encrypted = ZipUtils.zipNode(selected_file);
		File selected = selected_file.getFile();
		FileUtils.emptyDirectory(selected);
		selected.delete();
		FileUtils.removeFileType(encrypted);
		setNormCursor();
	}

	public void decryptNode(Node selected_file) throws FileNotFoundException,
			IOException, ZipException {
		setWaitCursor();
		File zip = FileUtils.appendToDirectory(selected_file.getFile(), ".zip");
		File unzipped = ZipUtils.unzipNode(new Node(zip));
		zip.delete();
		setNormCursor();
	}
}

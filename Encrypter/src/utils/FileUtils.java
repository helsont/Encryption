package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import main.Constants;

public class FileUtils {
	public static boolean createFolder(File dir) {
		return dir.mkdir();
	}

	public static boolean emptyDirectory(File folder) {
		File[] list = folder.listFiles();
		boolean success = false;
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				emptyDirectory(list[i]);
			}
			success = list[i].delete();
		}
		return success;
	}

	public static boolean createSubdirectoryInFolder(File folder,
			String folderName) {
		File newDir = new File(folder.getPath() + File.separator + folderName);
		return createFolder(newDir);
	}

	/**
	 * Removes the text after the first period it finds, reading right to left.
	 * 
	 * @param f
	 */
	public static void removeFileType(File f) {
		char[] name = f.getAbsolutePath().toCharArray();
		int mark = -1;

		for (int idx = name.length - 1; idx >= 0; idx--)
			if (name[idx] == '.') {
				mark = idx;
				break;
			}
		name[mark] = '1';

		f.renameTo(new File(String.copyValueOf(name, 0, name.length
				- (name.length - mark))));
	}

	/**
	 * Removes the text after the first period it finds, reading right to left.
	 * 
	 * @param f
	 */
	public static String removeFileType(String f) {
		char[] name = f.toCharArray();
		int mark = -1;

		for (int idx = name.length - 1; idx >= 0; idx--)
			if (name[idx] == '.') {
				mark = idx;
				break;
			}
		return String.copyValueOf(name, 0, name.length - (name.length - mark));
	}

	public synchronized static void hide(File f) throws InterruptedException {
		if (f.isDirectory())
			hideAll(f);
		if (f.isHidden())
			return;
		String parent_dir = f.getParent();
		if (Constants.OS == OSValidator.MAC) {
			String new_name = "." + f.getName();
			f.renameTo(new File(parent_dir + File.separator + new_name));
		} else if (Constants.OS == OSValidator.WINDOWS) {
			// @TODO: Implementation.
		} else if (Constants.OS == OSValidator.UNIX) {
			String new_name = "." + f.getName();
			f.renameTo(new File(parent_dir + new_name));
		}
	}

	public static void unhide(File f) {
		if (f.isDirectory())
			unhideAll(f);

		String parent_dir = f.getParent();
		if (Constants.OS == OSValidator.MAC) {
			if (!f.isHidden()) {
				System.out.println("This is not hidden:" + f.getAbsolutePath());
				return;
			}
			char[] name = f.getName().toCharArray();
			char[] new_name = null;
			if (name[0] == '.')
				new_name = Arrays.copyOfRange(name, 1, name.length);

			String str_new_name = String.copyValueOf(new_name);
			f.renameTo(new File(parent_dir + File.separator + str_new_name));
		} else if (Constants.OS == OSValidator.WINDOWS) {
			// @TODO: Implementation.
		} else if (Constants.OS == OSValidator.UNIX) {
			// @TODO: Implementation.
			// String new_name = "." + f.getName();
			// f.renameTo(new File(parent_dir + new_name));
		}
	}

	public static void unhideAll(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files)
				unhide(file);
		}
	}

	public static void hideAll(File dir) throws InterruptedException {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File f : files) {
				hide(f);
			}
		}
	}

	public static File createFolder(String... args) {
		String dir = args[0];
		for (int i = 1; i < args.length; i++)
			dir += File.separator + args[i];
		File f = new File(dir);
		if (f.exists())
			return f;
		if (!f.mkdir())
			throw new Error("Unable to create folder:" + f.getPath());
		return f;
	}

	public static void writeToFile(String file_path, String text) {
		try {
			FileOutputStream fos = new FileOutputStream(file_path);
			fos.write(text.getBytes());
			fos.close();
		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException : " + ex);
		} catch (IOException ioe) {
			System.out.println("IOException : " + ioe);
		}
	}

	public static File appendToDirectory(File f, String s) {
		if (f.renameTo(new File(f.getAbsoluteFile() + s)))
			return new File(f.getAbsolutePath() + s);
		else
			throw new Error("Could not rename " + f.getAbsolutePath());
	}

	public static File getParentDirectory(File f) {
		return new File(f.getParent());
	}

	public static char[] getTextFromFile(String file_path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_path));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			String everything = sb.toString();
			return everything.toCharArray();
		} finally {
			br.close();
		}
	}

	/**
	 * Writes data in the same folder as the original data.
	 * 
	 * @param f
	 *            The original file.
	 * @param b
	 *            The data to write.
	 */
	public static void overrwriteData(File f, byte[] b)
			throws FileNotFoundException, IOException {

		FileOutputStream fos = new FileOutputStream(f.getPath());
		fos.write(b);
		fos.close();
	}
}

package encrypter;

import java.io.File;

public class FileUtils {
	public static boolean createFolder(File dir) {
		return dir.mkdir();
	}

	public static boolean createSubdirectoryInFolder(File folder,
			String folderName) {
		File newDir = new File(folder.getPath() + File.separator + folderName);
		return createFolder(newDir);
	}

	public static void makeHidden(File f) {
		if(f.isDirectory())
			makeFilesInDirectoryHidden(f);
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

	public static void makeFilesInDirectoryHidden(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for(File f: files) {
				makeHidden(f);
			}
		}
	}
}

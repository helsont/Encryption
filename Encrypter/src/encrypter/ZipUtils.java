package encrypter;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipUtils {

	public static void extract(File source) {
		try {
			// Initiate ZipFile object with the path/name of the zip file.

			ZipFile zipFile = new ZipFile(source);

			// Check to see if the zip file is password protected
			if (zipFile.isEncrypted()) {
				// if yes, then set the password for the zip file
				zipFile.setPassword(new char[] { 'b', 'i', 'g', 'b', 'o', 'o',
						't', 'y' });
			}

			// Specify the file name which has to be extracted and the path to
			// which this file has to be extracted
			zipFile.extractFile("output", source.getParent() + File.separator);
		} catch (ZipException e) {
			e.printStackTrace();
		}

	}

	public static void zip(File source_file, File dest_file) {

		try {
			// Initiate ZipFile object with the path/name of the zip file.
			ZipFile zipFile = new ZipFile(dest_file.getAbsoluteFile());

			// Initiate Zip Parameters which define various properties such
			// as compression method, etc.
			ZipParameters parameters = new ZipParameters();

			// set compression method to store compression
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

			// Set the compression level. This value has to be in between 0 to 9
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setIncludeRootFolder(true);
			parameters.setPassword(new char[] { 'b', 'i', 'g', 'b', 'o', 'o',
					't', 'y' });
			zipFile.createZipFile(asArrayList(source_file.listFiles()),
					parameters);
			// zipFile.createZipFile(source_file, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}

	private static <T> ArrayList<T> asArrayList(T[] arr) {
		ArrayList<T> list = new ArrayList<T>(arr.length);
		for (int idx = 0; idx < arr.length; idx++)
			list.add(idx, arr[idx]);
		return list;
	}
}

package utils;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.progress.ProgressMonitor;
import net.lingala.zip4j.util.Zip4jConstants;
import tree.Node;

public class ZipUtils {

	public static File unzip(final File source) throws ZipException {

		// Initiate ZipFile object with the path/name of the zip file.

		final ZipFile zipFile = new ZipFile(source);
		// zipFile.setRunInThread(true);
		// Check to see if the zip file is password protected
		if (zipFile.isEncrypted()) {
			// if yes, then set the password for the zip file
			zipFile.setPassword(new char[] { 'b', 'i', 'g', 'b', 'o', 'o', 't',
					'y' });
		}

		// Get progress monitor from ZipFile
		// ProgressMonitor progressMonitor = zipFile.getProgressMonitor();
		//
		// Progress p = new Progress(progressMonitor, new Callback() {
		//
		// @Override
		// public void run() {
		// try {
		zipFile.extractAll(FileUtils.removeFileType(source.getAbsolutePath()));
		// } catch (ZipException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		// });
		//
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Specify the file name which has to be extracted and the path to
		// which this file has to be extracted
		return new File(FileUtils.removeFileType(source.getAbsolutePath()));

	}

	public static File unzipNode(Node node) throws ZipException {
		return unzip(node.getFile());
	}

	public static File zipNode(Node node) throws ZipException {
		return zip(node.getFile(), new File(node.getFile() + ".zip"));
	}

	public static File zip(File source_file, File dest_file)
			throws ZipException {

		// Initiate ZipFile object with the path/name of the zip file.
		ZipFile zipFile = new ZipFile(dest_file.getAbsoluteFile());
		// zipFile.setRunInThread(true);
		// Initiate Zip Parameters which define various properties such
		// as compression method, etc.
		ZipParameters parameters = new ZipParameters();

		// set compression method to store compression
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

		// This value has to be in between 0 to 9
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

		parameters.setEncryptFiles(true);
		parameters.setReadHiddenFiles(true);

		// Set the encryption method to AES Zip Encryption
		parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

		// Set AES Key strength. Key strengths available for AES encryption
		// are:
		// AES_STRENGTH_128 - For both encryption and decryption
		// AES_STRENGTH_192 - For decryption only
		// AES_STRENGTH_256 - For both encryption and decryption
		// Key strength 192 cannot be used for encryption. But if a zip file
		// already has a
		// file encrypted with key strength of 192, then Zip4j can decrypt
		// this file
		parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

		parameters.setIncludeRootFolder(true);
		parameters.setPassword(new char[] { 'b', 'i', 'g', 'b', 'o', 'o', 't',
				'y' });

		zipFile.createZipFile(asArrayList(source_file.listFiles()), parameters);
		completionCheck(zipFile, parameters, source_file);
		return dest_file;
	}

	private static void completionCheck(ZipFile f, ZipParameters zp, File src)
			throws ZipException {
		File[] list = src.listFiles();
		for (int idx = 0; idx < list.length; idx++)
			if (list[idx].isDirectory()) {
				f.addFolder(list[idx], zp);
			} else if (list[idx].isFile()) {
				f.addFile(list[idx], zp);
			}
	}

	private static <T> ArrayList<T> asArrayList(T[] arr) {
		ArrayList<T> list = new ArrayList<T>(arr.length);
		for (int idx = 0; idx < arr.length; idx++)
			list.add(idx, arr[idx]);
		return list;
	}
}

package encrypter;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tree.Node;

import net.lingala.zip4j.exception.ZipException;

public class EncryptPerformed {

	public Node selected_file;
	public boolean processing;
	public int encrypt_count;
	public JFrame frame;

	public EncryptPerformed(JFrame frame, Node selected_file,
			boolean processing, int encrypt_count) {
		this.selected_file = selected_file;
		this.frame = frame;
		this.processing = processing;
		this.encrypt_count = encrypt_count;

	}

	public void actionPerformed(ActionEvent evt) {
		if (selected_file == null)
			return;
		if (processing)
			JOptionPane.showMessageDialog(frame,
					"Please wait, encryption in process.");
		if (encrypt_count > 0) {
			int result = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to encrypt? "
							+ "Do not reencrypt data. "
							+ "This will result in permanent data loss.",
					"Reencrypt Data?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION)
				return;
			if (result == JOptionPane.NO_OPTION)
				return;
		}
		Encrypter en = new Encrypter();
		en.setLoc();
		processing = true;

		if (selected_file != null)
			try {
				en.encryptNode(selected_file);
				// en.encryptFolderNFC(_list);
				JOptionPane.showMessageDialog(frame, "Successfully encrypted.");
				processing = false;
				encrypt_count++;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Encryption unsuccesful.");
				e.printStackTrace();
				processing = false;
				return;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Encryption unsuccesful.");
				e.printStackTrace();
				processing = false;
				return;
			} catch (ZipException e) {
				JOptionPane.showMessageDialog(frame, "Encryption unsuccesful.");
				e.printStackTrace();
				processing = false;
				return;
			}
	}
}

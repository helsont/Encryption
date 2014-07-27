package encrypter;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.lingala.zip4j.exception.ZipException;
import tree.Node;

public class DecryptPerformed {

	public Node selected_file;
	public boolean processing;
	public int encrypt_count;
	public JFrame frame;

	public DecryptPerformed(JFrame frame, Node selected_file,
			boolean processing, int encrypt_count) {
		this.selected_file = selected_file;
		this.frame = frame;
		this.processing = processing;
		this.encrypt_count = encrypt_count;

	}

	public void actionPerformed(ActionEvent event) {
		if (selected_file == null)
			return;
		if (processing)
			JOptionPane.showMessageDialog(frame,
					"Please wait, decryption in process.");
		encrypt_count = 0;
		Encrypter de = new Encrypter();
		de.setLoc();
		processing = true;

		if (selected_file != null) {
			try {
				de.decryptNode(selected_file);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(frame, "Decryption unsuccesful.");
				e.printStackTrace();
				processing = false;
				return;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(frame, "Decryption unsuccesful.");
				e.printStackTrace();
				processing = false;
				return;
			} catch (ZipException e) {
				JOptionPane.showMessageDialog(frame, "Decryption unsuccesful.");
				e.printStackTrace();
				processing = false;
				return;
			}
		}

		JOptionPane.showMessageDialog(frame, "Successfully decrypted.");
		processing = false;
	}
}

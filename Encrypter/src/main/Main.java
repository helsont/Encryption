package main;

import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import frames.Login;
import frames.Register;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		if(userExists()) {
			new Login();
			Login.main(args);
		} else {
			new Register();
			Register.main(args);
		}
	}
	
	private static boolean userExists() {
		File f = new File(Constants.loc + File.separator+ "." + Constants.Clock + File.separator + "." + Constants.Clock2);
		if(f.exists())
			return true;
		return false;
	}
}

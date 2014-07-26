package encrypter;

import java.io.File;

public class Main {
	public static void main(String[] args) {
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

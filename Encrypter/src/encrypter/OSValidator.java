package encrypter;
public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();
	public static final int WINDOWS = 1, MAC = 2, UNIX = 3, SOLARIS = 4,
			UNSUPPORTED = 5;

	public static int getOS() {

		if (isWindows()) {
			// System.out.println("This is Windows");
			return WINDOWS;
		} else if (isMac()) {
			// System.out.println("This is Mac");
			return MAC;
		} else if (isUnix()) {
			// System.out.println("This is Unix or Linux");
			return UNIX;
		} else if (isSolaris()) {
			// System.out.println("This is Solaris");
			return SOLARIS;
		} else {
			// System.out.println("Your OS is not supported.");
			return UNSUPPORTED;
		}
	}

	private static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	private static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	private static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS
				.indexOf("aix") > 0);
	}

	private static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}

}

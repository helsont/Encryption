package encrypter;
import java.io.File;


public class Constants {
	public static int ERROR_COULD_NOT_FIND_KEY = 1;
	public static String NAME = "f8", SEPERATOR = File.pathSeparator;
	public static final int OS = OSValidator.getOS();
	public static final String loc = System.getProperty("user.home");
}

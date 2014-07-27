package main;
import java.io.File;

import utils.OSValidator;


public class Constants {
	public static int ERROR_COULD_NOT_FIND_KEY = 1;
	public static String NAME = "f8", SEPERATOR = File.separator;
	public static final int OS = OSValidator.getOS();
	public static final String loc = System.getProperty("user.home");
	public static final String Clock = "wienc", Clock2 = "clock2";
}

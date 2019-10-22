package fb.pricingAnalytics.utils;


import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @Fishbowl
 * @author abhay
 */

public class FBUtils {

	private static Logger logger = Logger.getLogger(FBUtils.class);
	
	public static boolean isAdmin(List<Integer> userRoles) {
		boolean isAdmin = false;
		for(Integer userAuthRole : userRoles) {
			if(userAuthRole == 1001){
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}

	public static String getFileExtension(String fileName) {
		logger.info("fileName : " + fileName);
		String extension = "";

		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

		if (i > p) {
			extension = fileName.substring(i + 1);
		}
		logger.info("extension : " + extension);
		return extension;
	}

	public static String getRandomString(int len) {

		final String source = "0123456789";
		SecureRandom rand = new SecureRandom();

		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(source.charAt(rand.nextInt(source.length())));
		return sb.toString();
	}
    
	public static boolean serachTextPattern(String freeText){
		Pattern pattern = Pattern.compile("[^A-Za-z0-9\\s]");
	    Matcher matcher = pattern.matcher(freeText);
	    return (matcher.find());
	}
}
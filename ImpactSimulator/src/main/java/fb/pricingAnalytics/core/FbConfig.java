package fb.pricingAnalytics.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.el.PropertyNotFoundException;

import org.apache.log4j.Logger;

/**
*@Fishbowl
*@author abhay
*/

public class FbConfig {
	
	private static FbConfig fbConfig;
	private static final Integer ALOCK = new Integer(0);
	private Properties properties = new Properties();
	private static Logger logger = Logger.getLogger(FbConfig.class);
        
	private FbConfig() {

		String propertyFileLocation =System.getProperty("fishbowlConfigFile");
             propertyFileLocation = "C:\\Pradeep\\Code\\SouceCode\\FbAppWs\\FBAppWS\\FBAppWS\\conf\\fishbowlmasterConfig.properties";
		logger.info("propertyFileLocation : " + propertyFileLocation);
		InputStream configStream;
		try {
			configStream = new FileInputStream(propertyFileLocation);
			//configStream = getClass().getClassLoader().getResourceAsStream("fbconfig.properties");			
			setConfig(configStream);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
	
	public static FbConfig getConfig() {
		if (fbConfig == null) {
			synchronized (ALOCK) {
				if (fbConfig == null) {
					fbConfig = new FbConfig();
				}
			}
		}
		return fbConfig;
	}	

	private void setConfig(InputStream configStream) {
		if (configStream != null) {
			try {
				properties.load(configStream);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException(new FileNotFoundException(
					"Fishbowl Configuration file not found"));
		}
	}
	
	public String getProperty(String key) {
		String value = properties.getProperty(key);
		if (value == null || value.isEmpty()) {
			throw new PropertyNotFoundException(key);
		}
		return value;
	}
}

package fb.pricingAnalytics.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *@Fishbowl
 *@author abhay
 */

public class AuthUtil {

	private static Logger logger = Logger.getLogger(AuthUtil.class);
	
	
	
	/*
	 * public String getDefaultFromAddress(HttpServletRequest request){ String
	 * fromAddress ; if(request.getServerName().contains("qa")){ fromAddress =
	 * fbProperties.getQaFromAddress(); }else
	 * if(request.getServerName().contains("stg") ||
	 * request.getServerName().contains("staging")){ fromAddress =
	 * fbProperties.getStgFromAddress(); }else{ fromAddress =
	 * fbProperties.getPrdFromAddress(); } return fromAddress; }
	 */
	
	public String getApplicationURL(HttpServletRequest request,int reportId){
		String appURL = "https://" + request.getServerName() + "/#/reports/details/"+reportId;
		logger.info("Application URL : "+appURL);
		return appURL;
	}
	
}



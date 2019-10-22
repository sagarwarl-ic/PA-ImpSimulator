package fb.pricingAnalytics.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;



public class MergeCodeUtil {
	
	
	private final static Logger logger = LoggerFactory.getLogger(MergeCodeUtil.class);
	
	public String MERGE_CODE_SEPERATOR = ".";
	public String MERGE_CODE_SEPERATOR_REGEX = "\\.";
	public String MERGE_CODE_USER_NAME = "##USERNAME##";
	
	public static String resolveMergeCode(String message,String mergeCode,String mergeCodeValue) {
	  
	    try{
			if(!Strings.isNullOrEmpty(message)){
				message = message.replaceAll(mergeCode, mergeCodeValue);
			}
		} catch(Exception ex){
			logger.error(ExceptionUtils.getStackTrace(ex));
			
		}
		logger.info("resolveMergeCode - final message created is : "+message);
		return message;
	}
	
	
	
	

	public static String resolveMergeCodes(String message,Map<String,String> mergeCodesMap) {
		
		 String[] mergeCodesArray = mergeCodesMap.keySet().toArray(new String [mergeCodesMap.size()]);
		  
	    try{
	    	for(int i=0;i<mergeCodesArray.length;i++){
	    		if(mergeCodesArray[i].equalsIgnoreCase("##FISHBOWLTEAM##")){
	    			message=message.replace(mergeCodesArray[i],"Fishbowl Team");
	    		}else{
	    			message=message.replace(mergeCodesArray[i], mergeCodesMap.get(mergeCodesArray[i]) == null ? "" : mergeCodesMap.get(mergeCodesArray[i]));
	    		}
	    	  }
		} catch(Exception ex){
			logger.error(ExceptionUtils.getStackTrace(ex));
			
		}
		logger.info("Message after MergeCodes Resolved : "+message);
		return message;
	}
	
	
}

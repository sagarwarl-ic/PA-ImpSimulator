package fb.pricingAnalytics.core;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class FbProperties {
	
	private String qaFromAddress;
	private String stgFromAddress;
	private String prdFromAddress;
	private long fileUploadSizeLimit;
	private String sendGripApiKey;
	
	 
    @Autowired
    public FbProperties(@Value("${default.qa.from.address}") String qaFromAddress, @Value("${default.stg.from.address}") String stgFromAddress,@Value("${default.prd.from.address}") String prdFromAddress,
    					@Value("${fileUploadSizeLimit}") long fileUploadSizeLimit,@Value("${default.sendgridapikey}") String sendGripApiKey) {
    	this.qaFromAddress = qaFromAddress;
        this.stgFromAddress = stgFromAddress;
    	this.prdFromAddress = prdFromAddress;
        this.fileUploadSizeLimit = fileUploadSizeLimit;
        this.sendGripApiKey = sendGripApiKey;
 
    }

    public String getDefaultFromAddress(HttpServletRequest request){
		String fromAddress ;
		if(request.getServerName().contains("qa")){
			fromAddress = this.getQaFromAddress();
		}else if(request.getServerName().contains("stg") || request.getServerName().contains("staging")){
			fromAddress = this.getStgFromAddress();
		}else{
			fromAddress = this.getPrdFromAddress();
		}
		return fromAddress;
	}
    
	public String getQaFromAddress() {
		return qaFromAddress;
	}

	public String getStgFromAddress() {
		return stgFromAddress;
	}

	public String getPrdFromAddress() {
		return prdFromAddress;
	}

	public long getFileUploadSizeLimit() {
		return fileUploadSizeLimit;
	}

	public String getSendGripApiKey() {
		return sendGripApiKey;
	}
	
	

}

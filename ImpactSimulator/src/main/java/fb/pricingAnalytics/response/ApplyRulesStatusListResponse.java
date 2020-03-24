package fb.pricingAnalytics.response;

import java.util.ArrayList;
import java.util.List;

public class ApplyRulesStatusListResponse {
	
	private boolean successFlag;
	private String message;
	List<ApplyRulesStatusResponse> applyRulesStatusResponse = new ArrayList<ApplyRulesStatusResponse>();
	
	public boolean isSuccessFlag() {
		return successFlag;
	}
	public void setSuccessFlag(boolean successFlag) {
		this.successFlag = successFlag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ApplyRulesStatusResponse> getApplyRulesStatusResponse() {
		return applyRulesStatusResponse;
	}
	public void setApplyRulesStatusResponse(
			List<ApplyRulesStatusResponse> applyRulesStatusResponse) {
		this.applyRulesStatusResponse = applyRulesStatusResponse;
	}
	
}

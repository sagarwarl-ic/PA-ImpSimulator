package fb.pricingAnalytics.response;

import java.math.BigInteger;

public class ApplyRulesStatusResponse {
	
	private BigInteger ruleId;
	private boolean ruleApplied;
	private String message;
	
	
	

	public ApplyRulesStatusResponse(BigInteger ruleId, boolean ruleApplied,
			String message) {
		super();
		this.ruleId = ruleId;
		this.ruleApplied = ruleApplied;
		this.message = message;
	}
	public BigInteger getRuleId() {
		return ruleId;
	}
	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}
	public boolean isRuleApplied() {
		return ruleApplied;
	}
	public void setRuleApplied(boolean ruleApplied) {
		this.ruleApplied = ruleApplied;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

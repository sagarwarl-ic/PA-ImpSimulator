package fb.pricingAnalytics.response;

import java.math.BigInteger;

import fb.pricingAnalytics.utils.FBRestResponse;

public class RuleCreationResponse  extends FBRestResponse{
	
	private BigInteger ruleId;

	public BigInteger getRuleId() {
		return ruleId;
	}

	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}
	
	

}

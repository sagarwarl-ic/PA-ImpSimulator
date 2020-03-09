package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class ApplyRuleRequest {
	
	private BigInteger ruleId;
	private BigInteger scenarioId;
	private boolean isApplied;
	private BigInteger projectId;
	
	
	public BigInteger getRuleId() {
		return ruleId;
	}
	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}
	public BigInteger getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}
	public boolean isApplied() {
		return isApplied;
	}
	public void setApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}
	public BigInteger getProjectId() {
		return projectId;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	
	

}

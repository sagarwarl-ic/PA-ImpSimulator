package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class ApplyRuleRequest {
	
	private boolean isApplied;
	private boolean isDeleted;
	private BigInteger projectId;
	private BigInteger ruleId;
	private BigInteger scenarioId;
	
	
	public BigInteger getProjectId() {
		return projectId;
	}
	public BigInteger getRuleId() {
		return ruleId;
	}
	public BigInteger getScenarioId() {
		return scenarioId;
	}
	public boolean isApplied() {
		return isApplied;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}
	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}

	@Override
	public String toString() {
		return "ApplyRuleRequest [ruleId=" + ruleId + ", scenarioId=" + scenarioId + ", isApplied=" + isApplied
				+ ", projectId=" + projectId + ", isDeleted=" + isDeleted + "]";
	}
	
	

}

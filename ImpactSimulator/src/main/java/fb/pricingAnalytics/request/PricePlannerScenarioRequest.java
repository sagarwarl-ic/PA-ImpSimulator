package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class PricePlannerScenarioRequest {
	
	private BigInteger scenarioId;
	private BigInteger projectId;
	private BigInteger dataEntryId;
	private String scenarioName;
	private BigInteger businessRuleScenarioId;
	
	
	public BigInteger getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}
	
	public BigInteger getProjectId() {
		return projectId;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	
	public String getScenarioName() {
		return scenarioName;
	}
	public BigInteger getDataEntryId() {
		return dataEntryId;
	}
	public void setDataEntryId(BigInteger dataEntryId) {
		this.dataEntryId = dataEntryId;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public BigInteger getBusinessRuleScenarioId() {
		return businessRuleScenarioId;
	}
	public void setBusinessRuleScenarioId(BigInteger businessRuleScenarioId) {
		this.businessRuleScenarioId = businessRuleScenarioId;
	}

}

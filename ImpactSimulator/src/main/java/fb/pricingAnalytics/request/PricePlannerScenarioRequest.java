package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class PricePlannerScenarioRequest {
	
	private BigInteger scenarioId;
	private BigInteger projectId;
	private String scenarioName;
	
	
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
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

}

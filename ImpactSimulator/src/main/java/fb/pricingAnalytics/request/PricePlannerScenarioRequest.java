package fb.pricingAnalytics.request;

public class PricePlannerScenarioRequest {
	
	private Integer scenarioId;
	private Integer projectId;
	private String scenarioName;
	
	public Integer getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(Integer scenarioId) {
		this.scenarioId = scenarioId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

}

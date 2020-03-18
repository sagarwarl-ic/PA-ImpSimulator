package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class UpdateStoreInfoRequest {
	
	private Integer storeCode;
	private String proposedTier;
	private BigInteger project_Id;
	private BigInteger scenario_Id;
	private Integer brandId;
	private boolean changed;
	
	
	public boolean getChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	public Integer getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(Integer storeCode) {
		this.storeCode = storeCode;
	}
	public String getProposedTier() {
		return proposedTier;
	}
	public void setProposedTier(String proposedTier) {
		this.proposedTier = proposedTier;
	}
	
	public BigInteger getProject_Id() {
		return project_Id;
	}
	public void setProject_Id(BigInteger project_Id) {
		this.project_Id = project_Id;
	}
	public BigInteger getScenario_Id() {
		return scenario_Id;
	}
	public void setScenario_Id(BigInteger scenario_Id) {
		this.scenario_Id = scenario_Id;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	

}

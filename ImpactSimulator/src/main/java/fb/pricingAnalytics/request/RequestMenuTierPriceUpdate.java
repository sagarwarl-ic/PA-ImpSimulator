package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class RequestMenuTierPriceUpdate {

	private Integer brandId;
	private boolean changed;
	private BigInteger dataEntry_Id;
	private Double price;
	private String productId;
	private BigInteger project_Id;
	private BigInteger scenario_Id;
	private String tier;
	private Integer changeType;
	private BigInteger associateRuleId;

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}



	public BigInteger getAssociateRuleId() {
		return associateRuleId;
	}

	public void setAssociateRuleId(BigInteger associateRuleId) {
		this.associateRuleId = associateRuleId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public boolean getChanged() {
		return changed;
	}

	public BigInteger getDataEntry_Id() {
		return dataEntry_Id;
	}

	public Double getPrice() {
		return price;
	}

	public String getProductId() {
		return productId;
	}

	public BigInteger getProject_Id() {
		return project_Id;
	}

	public BigInteger getScenario_Id() {
		return scenario_Id;
	}

	public String getTier() {
		return tier;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;

	}

	public void setDataEntry_Id(BigInteger dataEntry_Id) {
		this.dataEntry_Id = dataEntry_Id;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProject_Id(BigInteger project_Id) {
		this.project_Id = project_Id;
	}

	public void setScenario_Id(BigInteger scenario_Id) {
		this.scenario_Id = scenario_Id;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	@Override
	public String toString() {
		return "RequestMenuTierPriceUpdate [brandId=" + brandId + ", changed=" + changed + ", dataEntry_Id="
				+ dataEntry_Id + ", price=" + price + ", productId=" + productId + ", project_Id=" + project_Id
				+ ", scenario_Id=" + scenario_Id + ", tier=" + tier + ", changeType=" + changeType
				+ ", associateRuleId=" + associateRuleId + "]";
	}

	

}

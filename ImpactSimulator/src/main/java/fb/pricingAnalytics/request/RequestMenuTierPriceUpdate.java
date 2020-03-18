package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class RequestMenuTierPriceUpdate {


	private String productId;
	private String tier;
	private Double price;	
	private BigInteger project_Id;
	private BigInteger scenario_Id;
	private Integer brandId;
	private boolean changed;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
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

	public boolean getChanged() {
		return changed;
	}
	public void setChanged(boolean changed) {
		this.changed = changed;

	}
}

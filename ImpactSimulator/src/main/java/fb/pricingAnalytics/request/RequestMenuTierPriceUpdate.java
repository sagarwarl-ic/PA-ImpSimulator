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
		return "RequestMenuTierPriceUpdate [productId=" + productId + ", tier=" + tier + ", price=" + price
				+ ", project_Id=" + project_Id + ", dataEntry_Id=" + dataEntry_Id + ", scenario_Id=" + scenario_Id
				+ ", brandId=" + brandId + ", changed=" + changed + "]";
	}

}

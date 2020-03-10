package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;
import java.util.Date;

public class PricingRuleVo {
	
	private BigInteger ruleId;
	private BigInteger scenarioId;
	private int brandId;
	private boolean isApplied;
	private boolean isDeleted;
	private Date createdOn;
	private String createdBy;
	private String ruleType;
	private String tierUpdate;
	private Float priceChange;
	private PricingRuleData ruleData;
	private String ruleName;
	
	public PricingRuleVo(BigInteger ruleId,String ruleName,BigInteger scenarioId, int brandId,
			boolean isApplied, boolean isDeleted, Date createdOn,
			String createdBy, String ruleType, String tierUpdate,
			Float priceChange, PricingRuleData ruleData) {
		super();
		this.ruleId = ruleId;
		this.scenarioId = scenarioId;
		this.brandId = brandId;
		this.isApplied = isApplied;
		this.isDeleted = isDeleted;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.ruleType = ruleType;
		this.tierUpdate = tierUpdate;
		this.priceChange = priceChange;
		this.ruleData = ruleData;
		this.ruleName = ruleName;
	}

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

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public boolean isApplied() {
		return isApplied;
	}

	public void setApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getTierUpdate() {
		return tierUpdate;
	}

	public void setTierUpdate(String tierUpdate) {
		this.tierUpdate = tierUpdate;
	}

	public Float getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(Float priceChange) {
		this.priceChange = priceChange;
	}

	public PricingRuleData getRuleData() {
		return ruleData;
	}

	public void setRuleData(PricingRuleData ruleData) {
		this.ruleData = ruleData;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	

}

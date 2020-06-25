package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;
import java.util.Date;

public class PricingRuleVo {
	
	private int brandId;
	private String createdBy;
	private Date createdOn;
	private boolean isApplied;
	private boolean isDeleted;
	private Double priceChange;
	private boolean priceChangeByPercentage;
	private PricingRuleData ruleData;
	private BigInteger ruleId;
	private String ruleName;
	private String ruleType;
	private BigInteger scenarioId;
	private String tierUpdate;
	private int priceBarrierOption;
	private int priceChangeBy;
	
	public PricingRuleVo() {
		super();
	}

	public PricingRuleVo(BigInteger ruleId,String ruleName,BigInteger scenarioId, int brandId,
			boolean isApplied, boolean isDeleted, Date createdOn,
			String createdBy, String ruleType, String tierUpdate,
			Double priceChange, PricingRuleData ruleData, boolean priceChangeByPercentage, int priceBarrierOption,int priceChangeBy) {
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
		this.priceChangeByPercentage = priceChangeByPercentage;
		this.priceBarrierOption=priceBarrierOption;
		this.priceChangeBy=priceChangeBy;
	}

	public int getBrandId() {
		return brandId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Double getPriceChange() {
		return priceChange;
	}

	public PricingRuleData getRuleData() {
		return ruleData;
	}

	public BigInteger getRuleId() {
		return ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public BigInteger getScenarioId() {
		return scenarioId;
	}

	public String getTierUpdate() {
		return tierUpdate;
	}

	public boolean isApplied() {
		return isApplied;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public boolean isPriceChangeByPercentage() {
		return priceChangeByPercentage;
	}

	public void setApplied(boolean isApplied) {
		this.isApplied = isApplied;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setPriceChange(Double priceChange) {
		this.priceChange = priceChange;
	}

	public void setPriceChangeByPercentage(boolean priceChangeByPercentage) {
		this.priceChangeByPercentage = priceChangeByPercentage;
	}

	public void setRuleData(PricingRuleData ruleData) {
		this.ruleData = ruleData;
	}

	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}

	public void setTierUpdate(String tierUpdate) {
		this.tierUpdate = tierUpdate;
	}

	public int getPriceBarrierOption() {
		return priceBarrierOption;
	}

	public void setPriceBarrierOption(int priceBarrierOption) {
		this.priceBarrierOption = priceBarrierOption;
	}

	public int getPriceChangeBy() {
		return priceChangeBy;
	}

	public void setPriceChangeBy(int priceChangeBy) {
		this.priceChangeBy = priceChangeBy;
	}
	

}

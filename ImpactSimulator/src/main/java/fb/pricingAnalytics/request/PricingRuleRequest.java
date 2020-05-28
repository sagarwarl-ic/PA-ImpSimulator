package fb.pricingAnalytics.request;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PricingRuleRequest {
	
	private MenuItem menuItem;
	@JsonIgnore
	private Double priceChange;
	@JsonIgnore
	private boolean priceChangeByPercentage;
	@JsonIgnore
	private BigInteger ruleId;
	
	@JsonIgnore
	private String ruleName;
	
	@JsonIgnore
	private BigInteger scenarioId;
	
	private StoreTier storeTier;
	
	@JsonIgnore
	private String tierUpdate;
	
	@JsonIgnore
	private String type;
	
	@JsonIgnore
	private int priceBarrierOption;
	
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public Double getPriceChange() {
		return priceChange;
	}
	
	public boolean getPriceChangeByPercentage() {
		return priceChangeByPercentage;
	}
	public BigInteger getRuleId() {
		return ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public BigInteger getScenarioId() {
		return scenarioId;
	}
	public StoreTier getStoreTier() {
		return storeTier;
	}
	public String getTierUpdate() {
		return tierUpdate;
	}
	public String getType() {
		return type;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public void setPriceChange(Double priceChange) {
		this.priceChange = priceChange;
	}
	public void setPriceChangeByPercentage(boolean priceChangeByPercentage) {
		this.priceChangeByPercentage = priceChangeByPercentage;
	}
	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}
	public void setStoreTier(StoreTier storeTier) {
		this.storeTier = storeTier;
	}
	public void setTierUpdate(String tierUpdate) {
		this.tierUpdate = tierUpdate;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPriceBarrierOption() {
		return priceBarrierOption;
	}
	public void setPriceBarrierOption(int priceBarrierOption) {
		this.priceBarrierOption = priceBarrierOption;
	}
	
	

}

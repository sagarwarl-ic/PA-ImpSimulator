package fb.pricingAnalytics.request;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PricingRuleRequest {
	
	@JsonIgnore
	private BigInteger scenarioId;
	@JsonIgnore
	private String type;
	@JsonIgnore
	private Double priceChange;
	@JsonIgnore
	private String tierUpdate;
	
	private MenuItem menuItem;
	
	private StoreTier storeTier;
	
	@JsonIgnore
	private String ruleName;
	
	@JsonIgnore
	private BigInteger ruleId;
	
	public BigInteger getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getPriceChange() {
		return priceChange;
	}
	public void setPriceChange(Double priceChange) {
		this.priceChange = priceChange;
	}
	public String getTierUpdate() {
		return tierUpdate;
	}
	public void setTierUpdate(String tierUpdate) {
		this.tierUpdate = tierUpdate;
	}
	public MenuItem getMenuItem() {
		return menuItem;
	}
	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}
	public StoreTier getStoreTier() {
		return storeTier;
	}
	public void setStoreTier(StoreTier storeTier) {
		this.storeTier = storeTier;
	}
	public BigInteger getRuleId() {
		return ruleId;
	}
	public void setRuleId(BigInteger ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	

}

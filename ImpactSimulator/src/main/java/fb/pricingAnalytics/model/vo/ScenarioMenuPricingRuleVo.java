package fb.pricingAnalytics.model.vo;

import java.math.BigInteger;
import java.util.Date;

import fb.pricingAnalytics.request.MenuItem;

public class ScenarioMenuPricingRuleVo {

	private int brandId;

	private String createdBy;

	private Date createdOn;

	private MenuItem decisiveMenuRuleData;

	private MenuItem dependentMenuRuleData;

	private boolean isApplied;

	private boolean isDeleted;

	private int operator;

	private float priceChange;

	private BigInteger ruleId;

	private String ruleName;

	private BigInteger scenarioId;

	private String updatedBy;

	private Date updatedOn;

	public ScenarioMenuPricingRuleVo(int brandId, String createdBy, Date createdOn, MenuItem decisiveMenuRuleData,
			MenuItem dependentMenuRuleData, boolean isApplied, boolean isDeleted, int operator, float priceChange,
			BigInteger ruleId, String ruleName, BigInteger scenarioId, String updatedBy, Date updatedOn) {
		super();
		this.brandId = brandId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.decisiveMenuRuleData = decisiveMenuRuleData;
		this.dependentMenuRuleData = dependentMenuRuleData;
		this.isApplied = isApplied;
		this.isDeleted = isDeleted;
		this.operator = operator;
		this.priceChange = priceChange;
		this.ruleId = ruleId;
		this.ruleName = ruleName;
		this.scenarioId = scenarioId;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
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

	public MenuItem getDecisiveMenuRuleData() {
		return decisiveMenuRuleData;
	}

	public MenuItem getDependentMenuRuleData() {
		return dependentMenuRuleData;
	}

	public int getOperator() {
		return operator;
	}

	public float getPriceChange() {
		return priceChange;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public boolean isApplied() {
		return isApplied;
	}

	public boolean isDeleted() {
		return isDeleted;
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

	public void setDecisiveMenuRuleData(MenuItem decisiveMenuRuleData) {
		this.decisiveMenuRuleData = decisiveMenuRuleData;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setDependentMenuRuleData(MenuItem dependentMenuRuleData) {
		this.dependentMenuRuleData = dependentMenuRuleData;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
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

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}




}

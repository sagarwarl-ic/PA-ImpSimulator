package fb.pricingAnalytics.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ScenarioPricingRule",schema="ImpactSimulator.dbo")
public class ScenarioPricingRule {
	
	@Column(name="BrandId")
	private int brandId;
	
	@Column(name="CreatedBy")
	private String createdBy;
		
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Column(name="IsApplied")
	private boolean isApplied;
	
	@Column(name="IsDeleted")
	private boolean isDeleted;
	
	
	@Column(name="PriceChange")
	private Double priceChange;
		
	@Column(name="RuleData")
	private String ruleData;
	
	@Id
	@GeneratedValue
	@Column(name="RuleId")
	private BigInteger ruleId;
	
	@Column(name="RuleName")
	private String ruleName;
	
	@Column(name="RuleType")
	private String ruleType;
	
	@Column(name="ScenarioId")
	private BigInteger scenarioId;
	
	@Column(name="TierUpdate")
	private String tierUpdate;
	
	@Column(name="PriceBarrierOption")
	private int priceBarrierOption;
	
	@Column(name="PriceChangeBy")
	private int priceChangeBy;

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

	public String getRuleData() {
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

	

	public void setRuleData(String ruleData) {
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

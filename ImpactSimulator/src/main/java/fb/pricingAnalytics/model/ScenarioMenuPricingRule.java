package fb.pricingAnalytics.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ScenarioMenuPricingRule", schema = "ImpactSimulator.dbo")
@NamedQueries({
                        @NamedQuery(name = "UpdateDeleteQuery", query = "UPDATE ScenarioMenuPricingRule SET IsDeleted=:is_Deleted where BrandId=:brand_Id and RuleId=:rule_Id and ScenarioId=:scenario_Id"),
		@NamedQuery(name = "SelectAllMenuQuery", query = "Select r from ScenarioMenuPricingRule r where brandId=:brand_Id and isDeleted=:is_Deleted and scenarioId=:scenario_Id")
})

public class ScenarioMenuPricingRule {

	@Column(name = "BrandId")
	private int brandId;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "DecisiveMenuRuleData")
	private String decisiveMenuRuleData;

	@Column(name = "DependentMenuRuleData")
	private String dependentMenuRuleData;

	@Column(name = "IsApplied")
	private boolean isApplied;

	@Column(name = "IsDeleted")
	private boolean isDeleted;

	@Column(name = "Operator")
	private int operator;

	@Column(name = "PriceChange")
	private float priceChange;

	@Id
	@GeneratedValue
	@Column(name = "RuleId")
	private BigInteger ruleId;

	@Column(name = "RuleName")
	private String ruleName;

	@Column(name = "ScenarioId")
	private BigInteger scenarioId;

	@Column(name="UpdatedBy")
	private String updatedBy;

	@Column(name="UpdatedOn")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	public ScenarioMenuPricingRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScenarioMenuPricingRule(int brandId, String createdBy, Date createdOn, String decisiveMenuRuleData,
			String dependentMenuRuleData, boolean isApplied, boolean isDeleted, int operator, float priceChange,
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

	public String getDecisiveMenuRuleData() {
		return decisiveMenuRuleData;
	}

	public String getDependentMenuRuleData() {
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

	public void setDecisiveMenuRuleData(String decisiveMenuRuleData) {
		this.decisiveMenuRuleData = decisiveMenuRuleData;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setDependentMenuRuleData(String dependentMenuRuleData) {
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

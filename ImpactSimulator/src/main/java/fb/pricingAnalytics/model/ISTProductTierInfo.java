package fb.pricingAnalytics.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="IST_Product_Tier_Info",schema="ImpactSimulator.dbo")
@NamedQueries({
@NamedQuery(name = "GetRecordBYProductIdTier", query = "Select r from ISTProductTierInfo r where brandId=:brand_Id and scenarioId=:scenario_Id and productId=:productId and tier=:tier")
})
public class ISTProductTierInfo implements Serializable {

	@Column(name="BrandId")
	private int brandId;

	@Column(name = "AssociateRuleId")
	private BigInteger associateRuleId;

	@Column(name = "ChangeType")
	private int changeType;

	@Column(name = "Comment")
	private String comment;

	@Column(name = "CreatedBy")
	private String createdBy;

	@Column(name = "CreatedOn")
	private Date createdOn;

	@Column(name="Current_Price")
	private float currentPrice;

	@Column(name="DataEntryId")
	private BigInteger dataEntryId;
	
	@Column(name="isChanged")
	private boolean isChanged;
	
	@Column(name="IsEditable")
	private boolean isEditable;
	
	@Id
	@GeneratedValue
	@Column(name="IstPrdTierId")
	private BigInteger istPrdTierId;
	
	@Column(name="Price")
	private double price;
	
	@Id
	@Column(name="Product_ID")
	private String productId;
	
	@Id
	@Column(name="Project_Id")
	private BigInteger projectId;
	
	@Id
	@Column(name="Scenario_Id")
	private BigInteger scenarioId;
	
	@Id
	@Column(name="Tier")
	private String tier;
	
	@Column(name="UpdatedBy")
	private String updatedBy;
	
	@Column(name="UpdatedOn")
	private Date updatedOn;
	
	public int getBrandId() {
		return brandId;
	}
	
	public int getChangeType() {
		return changeType;
	}
	
	public String getComment() {
		return comment;
	}
	
	
	public String getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public float getCurrentPrice() {
		return currentPrice;
	}

	public BigInteger getDataEntryId() {
		return dataEntryId;
	}

	public BigInteger getIstPrdTierId() {
		return istPrdTierId;
	}

	public double getPrice() {
		return price;
	}

	public String getProductId() {
		return productId;
	}

	public BigInteger getProjectId() {
		return projectId;
	}

	public BigInteger getScenarioId() {
		return scenarioId;
	}

	public String getTier() {
		return tier;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public boolean isChanged() {
		return isChanged;
	}

	public boolean isEditable() {
		return isEditable;
	}

	
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public void setCurrentPrice(float currentPrice) {
		this.currentPrice = currentPrice;
	}

	public void setDataEntryId(BigInteger dataEntryId) {
		this.dataEntryId = dataEntryId;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public void setIstPrdTierId(BigInteger istPrdTierId) {
		this.istPrdTierId = istPrdTierId;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigInteger getAssociateRuleId() {
		return associateRuleId;
	}

	public void setAssociateRuleId(BigInteger associateRuleId) {
		this.associateRuleId = associateRuleId;
	}

	@Override
	public String toString() {
		return "ISTProductTierInfo [brandId=" + brandId + ", associateRuleId=" + associateRuleId + ", changeType="
				+ changeType + ", comment=" + comment + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", currentPrice=" + currentPrice + ", dataEntryId=" + dataEntryId + ", isChanged=" + isChanged
				+ ", isEditable=" + isEditable + ", istPrdTierId=" + istPrdTierId + ", price=" + price + ", productId="
				+ productId + ", projectId=" + projectId + ", scenarioId=" + scenarioId + ", tier=" + tier
				+ ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + "]";
	}
	

	


	
	
}

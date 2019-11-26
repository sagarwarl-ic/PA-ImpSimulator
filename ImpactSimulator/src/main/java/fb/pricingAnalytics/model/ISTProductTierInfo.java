package fb.pricingAnalytics.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IST_Product_Tier_Info",schema="ImpactSimulator.dbo")
public class ISTProductTierInfo implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="IstPrdTierId")
	private BigInteger istPrdTierId;

	@Column(name="BrandId")
	private int brandId;
	
	@Id
	@Column(name="Product_ID")
	private String productId;
	
	@Id
	@Column(name="Tier")
	private String tier;
	
	@Column(name="Price")
	private double price;
	
	@Id
	@Column(name="Project_Id")
	private BigInteger projectId;
	
	@Id
	@Column(name="Scenario_Id")
	private BigInteger scenarioId;
	
	@Column(name="Comment")
	private String comment;
	
	@Column(name="isChanged")
	private boolean isChanged;
	
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Column(name="CreatedBy")
	private String createdBy;
	
	@Column(name="UpdatedOn")
	private Date updatedOn;
	
	@Column(name="UpdatedBy")
	private String updatedBy;
	
	
	public BigInteger getIstPrdTierId() {
		return istPrdTierId;
	}

	public void setIstPrdTierId(BigInteger istPrdTierId) {
		this.istPrdTierId = istPrdTierId;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	

	public BigInteger getProjectId() {
		return projectId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "ISTProductTierInfo [productId=" + productId + ", tier=" + tier + ", price=" + price + ", scenarioId="
				+ scenarioId + ", comment=" + comment + ", isChanged=" + isChanged + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}
	
	
}

package fb.pricingAnalytics.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="IST_Product_Tier_Info",schema="Simulator.dbo")
public class ISTProductTierInfo implements Serializable {


	@Id
	@Column(name="Product_ID")
	private String productId;
	
	@Id
	@Column(name="Tier")
	private String tier;
	
	@Column(name="Price")
	private double price;
	
	@Id
	@Column(name="Scenario_ID")
	private String scenarioId;
	
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

	public String getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(String scenarioId) {
		this.scenarioId = scenarioId;
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

	@Override
	public String toString() {
		return "ISTProductTierInfo [productId=" + productId + ", tier=" + tier + ", price=" + price + ", scenarioId="
				+ scenarioId + ", comment=" + comment + ", isChanged=" + isChanged + ", createdOn=" + createdOn
				+ ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}
	
	
}

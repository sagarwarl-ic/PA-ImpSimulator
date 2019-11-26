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
@Table(name="IST_Store_Info",schema="ImpactSimulator.dbo")

public class IST_Store_Info implements Serializable {
	
	
	@Id
	@GeneratedValue
	@Column(name="IstStoreId")
	private BigInteger istStoreId;
	
	@Column(name="BrandId")
	private int brandId;
	
	@Id
	@Column(name="Store_Code")
	private Integer storeCode;
	
	@Column(name="Proposed_Tier")
	private String proposedTier;

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
	
	
	public BigInteger getIstStoreId() {
		return istStoreId;
	}

	public void setIstStoreId(BigInteger istStoreId) {
		this.istStoreId = istStoreId;
	}

	public Integer getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(Integer storeCode) {
		this.storeCode = storeCode;
	}

	

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getProposedTier() {
		return proposedTier;
	}

	public void setProposedTier(String proposedTier) {
		this.proposedTier = proposedTier;
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

	public BigInteger getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}
	
	

}

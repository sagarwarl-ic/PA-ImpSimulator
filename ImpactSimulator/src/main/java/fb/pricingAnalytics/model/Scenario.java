package fb.pricingAnalytics.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Scenario",schema="ImpactSimulator.dbo")
public class Scenario {

	@Id
	@GeneratedValue
	@Column(name="ScenarioId")
	private BigInteger scenarioId;
	
	@Column(name="BrandId")
	private int brandId;
	
	@Column(name="Name")
	private String scenarioName;
	
	@Column(name="Project_Id")
	private BigInteger projectId;	
	
	@Column(name="Comment")
	private String comment;
	
	@Column(name="IsLocked")
	private boolean isLocked;
	
	@Column(name="IsLockedBy")
	private String isLockedBy;
	
	@Column(name="Deleted")
	private boolean deleted;
	
	@Column(name="CreatedOn")
	private Date createdOn;
	
	@Column(name="CreatedBy")
	private String createdBy;
	
	@Column(name="UpdatedOn")
	private Date updatedOn;
	
	@Column(name="UpdatedBy")
	private String updatedBy;

	

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getScenarioName() {
		return scenarioName;
	}

	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public BigInteger getProjectId() {
		return projectId;
	}

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
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

	
	public BigInteger getScenarioId() {
		return scenarioId;
	}

	public void setScenarioId(BigInteger scenarioId) {
		this.scenarioId = scenarioId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getIsLockedBy() {
		return isLockedBy;
	}

	public void setIsLockedBy(String isLockedBy) {
		this.isLockedBy = isLockedBy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
	@Override
	public String toString() {
		return "Scenario [ScenarioId=" + scenarioId + ", brandId=" + brandId + ", scenarioName=" + scenarioName + ", projectId=" + projectId
				+ ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn=" + updatedOn + ", updatedBy="
				+ updatedBy + "]";
	}


}

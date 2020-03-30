package fb.pricingAnalytics.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Project",schema="ImpactSimulator.dbo")
public class Project {

	@Id
	@GeneratedValue
	@Column(name="ProjectId")
	private BigInteger projectId;
	
	@Column(name="BrandId")
	private int brandId;
	
	@Column(name="Name")
	private String projectName;
	
	@Column(name="Status")
	private int status;
	
	@Column(name="Comment")
	private String comment;
	
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
	
	@Column(name="DataEntryId")
	private BigInteger dataEntryId;

	public BigInteger getProjectId() {
		return projectId;
	}

	/*public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}*/

	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/*public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}*/

	
	public Date getCreatedOn() {
		return createdOn;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public BigInteger getDataEntryId() {
		return dataEntryId;
	}

	public void setDataEntryId(BigInteger dataEntryId) {
		this.dataEntryId = dataEntryId;
	}

	@Override
	public String toString() {
		return "Project [ brandId=" + brandId + ", projectName=" + projectName + ", status=" + status
				+ ", deleted=" + deleted + ", createdOn=" + createdOn + ", createdBy=" + createdBy + ", updatedOn="
				+ updatedOn + ", updatedBy=" + updatedBy + "]";
	}
	
	
}

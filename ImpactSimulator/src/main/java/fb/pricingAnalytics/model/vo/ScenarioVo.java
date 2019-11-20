package fb.pricingAnalytics.model.vo;

import java.util.Date;

public class ScenarioVo {
	
	private Integer id;
	private Integer brandId;
	private String sceanrioName;
	private Integer projectId;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getSceanrioName() {
		return sceanrioName;
	}
	public void setSceanrioName(String sceanrioName) {
		this.sceanrioName = sceanrioName;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
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


}

package fb.pricingAnalytics.request;

import java.math.BigInteger;

public class PricePlannerProjectRequest {
	
	private BigInteger projectId;
	private BigInteger dataEntryId;
	private String projectName;
	private Integer statusId;
	private boolean deleted;
	
	
	public BigInteger getProjectId() {
		return projectId;
	}
	public void setProjectId(BigInteger projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}
	public BigInteger getDataEntryId() {
		return dataEntryId;
	}
	public void setDataEntryId(BigInteger dataEntryId) {
		this.dataEntryId = dataEntryId;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	

}
